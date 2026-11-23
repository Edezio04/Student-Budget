package com.example.myapplication1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.data.AppDatabase
import com.example.myapplication1.data.BudgetEntity
import com.example.myapplication1.data.TransactionEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val transactionDao = db.transactionDao()
    private val budgetDao = db.budgetDao()

    // Persistent flow of transactions from Room Database
    val transactions: StateFlow<List<TransactionItem>> = transactionDao.getAllTransactions()
        .map { entities ->
            entities.map { entity ->
                TransactionItem(
                    id = entity.id,
                    title = entity.title,
                    category = entity.category,
                    amount = entity.amount,
                    date = entity.date,
                    isIncome = entity.isIncome
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Persistent flow of monthly budget from Room Database
    val monthlyBudget: StateFlow<Double> = budgetDao.getBudgetList()
        .map { list -> list.firstOrNull()?.monthlyBudget ?: 100000.0 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 100000.0
        )

    fun addTransaction(item: TransactionItem) {
        viewModelScope.launch {
            val entity = TransactionEntity(
                id = item.id,
                title = item.title,
                category = item.category,
                amount = item.amount,
                date = item.date,
                isIncome = item.isIncome
            )
            transactionDao.insertTransaction(entity)
        }
    }

    fun deleteTransaction(id: String) {
        viewModelScope.launch {
            transactionDao.deleteTransaction(id)
        }
    }

    fun updateBudget(newBudget: Double) {
        viewModelScope.launch {
            val entity = BudgetEntity(id = 1, monthlyBudget = newBudget)
            budgetDao.setBudget(entity)
        }
    }
}
