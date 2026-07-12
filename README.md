# PayHajj & Telipesin - Android Architecture & UI Showcase

A curated collection of core components demonstrating modern Android development practices, clean architecture patterns, and custom UI design using **Kotlin** and **Jetpack Compose**. 

These files demonstrate code patterns implemented for two real-world client contract projects: **PayHajj** (Fintech) and **Telipesin** (Telehealth/Counseling).

---

## 📁 Repository Structure

### 1. `FintechDepositField.kt` (UI Layer - PayHajj)
*   **Focus:** State management, input filtering, and interactive Fintech UI controls.
*   **Demonstrated Skills:** 
    *   Dynamic formatting of raw numerical strings into local currency format (Naira `₦`).
    *   Interactive flow-grid selectors (Quick deposit buttons) connected to the primary text field state.
    *   Defensive text manipulation preventing character overflows and non-digit entries.

### 2. `SlidingPlanSelector.kt` (UI Layer - PayHajj)
*   **Focus:** Advanced Jetpack Compose layout synchronization and view paging.
*   **Demonstrated Skills:**
    *   Horizontal pager synchronization with sliding `TabRow` headers using `rememberPagerState`.
    *   Layout styling adapted dynamically to support native dark/light mode configurations.
    *   Decoupled, stateless Composable design facilitating easy previews and modifications.

### 3. `HomeViewModel.kt` (Architecture Layer - PayHajj)
*   **Focus:** Unidirectional Data Flow (UDF), asynchronous threading, and structured concurrency.
*   **Demonstrated Skills:**
    *   Encapsulated MVI/Event-driven updates using a single `onEvent(UiEvent)` dispatcher.
    *   Exposing state reactively using read-only `StateFlow` and `asStateFlow()` to prevent outer mutations.
    *   Caching-first loading routines retrieving user details offline from local preferences before triggering APIs.
    *   Reactive connectivity listening via Kotlin Flows to trigger data refreshes when internet is restored.

### 4. `TransactionDao.kt` (Data Layer - PayHajj)
*   **Focus:** Database persistence and memory-efficient list loading.
*   **Demonstrated Skills:**
    *   Integrated Jetpack **Paging 3** (`PagingSource`) to allow Room Database to load transaction items on-demand as the user scrolls.
    *   Thread-safe write operations (inserts and clears) utilizing Kotlin Coroutines (`suspend`).
    *   Cache conflict resolution handling using `OnConflictStrategy.REPLACE` for reliable syncing.

---

## 🛠️ Technology Stack & Libraries Used
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose (Material Design 3)
*   **Architecture:** Clean Architecture, Model-View-ViewModel (MVVM) / MVI patterns
*   **Async/Reactive Programming:** Kotlin Coroutines, Flow, StateFlow, LiveData
*   **Database:** Room Database (SQLite)
*   **Dependency Injection:** Dagger Hilt
*   **Paging:** Jetpack Paging 3
