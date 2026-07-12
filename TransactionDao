package com.example.uisamples.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Entity
import androidx.room.PrimaryKey


@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<TransactionEntity>)

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<TransactionEntity>

    @Query("DELETE FROM transactions")
    suspend fun clearTransactions()

    @Query("SELECT * FROM transactions ORDER BY id DESC")
    fun pagingSource(): PagingSource<Int, TransactionEntity>
}





@Entity(tableName = "transactions")
data class TransactionEntity(
    val amount: String,
    val created_at: String,
    @PrimaryKey val id: Int,
    val savings_id: Int,
    val status: String,
    val type: String,
    val user_id: Int,
    val paymentMethod: String?,
    val updated_at: String
)


fun Transactions.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        amount = amount,
        created_at = created_at,
        id = id,
        savings_id = savings_id,
        status = status,
        type = type,
        user_id = user_id,
        paymentMethod = paymentMethod,
        updated_at = updated_at
    )
}

fun TransactionEntity.toTransaction(): Transactions {
    return Transactions(
        amount = amount,
        created_at = created_at,
        id = id,
        savings_id = savings_id,
        status = status,
        type = type,
        user_id = user_id,
        paymentMethod = paymentMethod ?: "",
        updated_at = updated_at
    )
}
