package com.example.myapplication1.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
@JvmSuppressWildcards
interface BudgetDao {
    @Query("SELECT * FROM budget WHERE id = 1")
    fun getBudgetList(): Flow<List<BudgetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setBudget(budget: BudgetEntity): Long
}
