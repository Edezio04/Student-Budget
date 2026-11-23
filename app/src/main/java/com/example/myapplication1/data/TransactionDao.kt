package com.example.myapplication1.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
@JvmSuppressWildcards
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY id DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransaction(id: String): Int

    @Query("DELETE FROM transactions")
    suspend fun clearAll(): Int
}
