package com.example.uisamples.architecture

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: PayHajjRepository,
    private val userPreferences: UserPreferences,
    private val networkUtil: NetworkUtil,
    private val savingRepo: SavingRepository,
    private val transactionRepo: TransactionRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomePageUiState())
    val homeState: StateFlow<HomePageUiState> = _homeState.asStateFlow()

    init {
        loadDataFromLocalCache()
        observeInternetConnectivity()
    }

    fun onEvent(event: HomePageUiEvent) {
        when (event) {
            is HomePageUiEvent.OnCustomTargetAmount -> {
                _homeState.update { it.copy(customTargetAmount = event.amount) }
            }
            is HomePageUiEvent.OnSavingDuration -> {
                _homeState.update { it.copy(savingDuration = event.duration) }
            }
            is HomePageUiEvent.OnSavingSchedule -> {
                _homeState.update { it.copy(savingSchedule = event.schedule) }
            }
            is HomePageUiEvent.OnPlanName -> {
                _homeState.update { it.copy(planName = event.planName) }
            }
            is HomePageUiEvent.OnCreateNewPlan -> {
                createNewSavingsPlan()
            }
            is HomePageUiEvent.FundSavings -> {
                fundActiveSavings()
            }
            is HomePageUiEvent.OnRefresh -> {
                refreshAllData(forceRefresh = true)
            }
            HomePageUiEvent.OnCloseSheet -> {
                _homeState.update { it.copy(currentSheet = HomeSheet.None) }
            }
            is HomePageUiEvent.OnOpenSheet -> {
                _homeState.update { it.copy(currentSheet = event.sheet) }
            }

            HomePageUiEvent.OnDismissClicked -> {
                Log.d("HomeViewModel", "OnDismissClicked trigger handled")
            }
        }
    }

    private fun loadDataFromLocalCache() {
        viewModelScope.launch {
        
            val cachedUser = userPreferences.getUserProfile()
            _homeState.update { 
                it.copy(
                    userName = cachedUser?.name ?: "User",
                    isLoading = false
                )
            }
            

            if (networkUtil.isInternetAvailable()) {
                refreshAllData(forceRefresh = false)
            }
        }
    }

    private fun observeInternetConnectivity() {
        viewModelScope.launch {
            networkUtil.isInternetAvailableFlow
                .distinctUntilChanged()
                .collect { isConnected ->
                    if (isConnected) {
                        refreshAllData(forceRefresh = true)
                    }
                    _homeState.update { it.copy(showOfflineBanner = !isConnected) }
                }
        }
    }

    private fun refreshAllData(forceRefresh: Boolean) {
        viewModelScope.launch {
            _homeState.update { it.copy(isLoading = true) }
            try {
                val savings = savingRepo.getOngoingSavings(forceRefresh)
                val total = savingRepo.getTotalSavings(forceRefresh)
                
                _homeState.update { 
                    it.copy(
                        onGoingList = savings,
                        totalSavings = total.toString(),
                        isLoading = false
                    ) 
                }
            } catch (e: Exception) {
                _homeState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    private fun createNewSavingsPlan() {
        viewModelScope.launch {
            _homeState.update { it.copy(isLoading = true) }
            val request = CreateSavingRequest(
                name = _homeState.value.planName,
                goal_amount = _homeState.value.customTargetAmount.toIntOrNull() ?: 0
            )

            when (val result = repo.createSaving(request)) {
                is Resource.Success -> {
                    _homeState.update { 
                        it.copy(
                            isLoading = false,
                            isSavingCreated = true,
                            id = result.data?.id ?: 0
                        ) 
                    }
                }
                is Resource.Error -> {
                    _homeState.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
            }
        }
    }

    private fun fundActiveSavings() {
        viewModelScope.launch {
            _homeState.update { it.copy(isLoading = true) }
            val request = FundSavingRequest(
                amount = _homeState.value.fundsAmount.toIntOrNull() ?: 0,
                savings_id = _homeState.value.savingId.toIntOrNull() ?: 0
            )

            when (val result = repo.fundSaving(request)) {
                is Resource.Success -> {
                    _homeState.update { 
                        it.copy(
                            isLoading = false, 
                            isFundingSuccessful = true
                        ) 
                    }
                }
                is Resource.Error -> {
                    _homeState.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
            }
        }
    }
}




//Ui States

data class HomePageUiState(
    val userName: String = "",
    val customTargetAmount: String = "",
    val savingDuration: String = "",
    val savingSchedule: String = "",
    val planName: String = "",
    val fundsAmount: String = "",
    val savingId: String = "",
    val id: Int = 0,
    val showOfflineBanner: Boolean = false,
    val currentSheet: HomeSheet = HomeSheet.None,
    val onGoingList: List<Any> = emptyList(),
    val totalSavings: String = "0",
    val isSavingCreated: Boolean = false,
    val isFundingSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


// Ui Events

sealed interface HomePageUiEvent {
    data class OnCustomTargetAmount(val amount: String) : HomePageUiEvent
    data class OnSavingDuration(val duration: String) : HomePageUiEvent
    data class OnSavingSchedule(val schedule: String) : HomePageUiEvent
    data class OnPlanName(val planName: String) : HomePageUiEvent
    data class OnOpenSheet(val sheet: HomeSheet) : HomePageUiEvent
    object OnCloseSheet : HomePageUiEvent
    object OnCreateNewPlan : HomePageUiEvent
    object FundSavings : HomePageUiEvent
    object OnRefresh : HomePageUiEvent
    object OnDismissClicked : HomePageUiEvent
}

sealed interface HomeSheet {
    object None : HomeSheet
    object FundPlanSavingListBottomSheet : HomeSheet
    object FundAmountBottomSheet : HomeSheet
}

interface PayHajjRepository {
    suspend fun createSaving(request: CreateSavingRequest): Resource<SavingResponse>
    suspend fun fundSaving(request: FundSavingRequest): Resource<FundingResponse>
}

interface SavingRepository {
    suspend fun getOngoingSavings(forceRefresh: Boolean): List<Any>
    suspend fun getTotalSavings(forceRefresh: Boolean): Double
}

interface TransactionRepository

interface UserPreferences {
    suspend fun getUserProfile(): UserProfile?
}

interface NetworkUtil {
    fun isInternetAvailable(): Boolean
    val isInternetAvailableFlow: Flow<Boolean>
}

data class UserProfile(val name: String)
data class CreateSavingRequest(val name: String, val goal_amount: Int)
data class SavingResponse(val id: Int)
data class FundSavingRequest(val amount: Int, val savings_id: Int)
data class FundingResponse(val transaction_id: String)

sealed interface Resource<out T> {
    data class Success<out T>(val data: T?) : Resource<T>
    data class Error(val message: String?) : Resource<Nothing>
}
