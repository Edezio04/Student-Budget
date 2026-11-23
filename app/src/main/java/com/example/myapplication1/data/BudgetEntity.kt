package com.example.myapplication1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class BudgetEntity(
    @PrimaryKey
    val id: Int = 1,
    val monthlyBudget: Double
)
