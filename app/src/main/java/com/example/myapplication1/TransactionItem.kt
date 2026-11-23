package com.example.myapplication1

import java.util.UUID

data class TransactionItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val category: String,
    val amount: Double,
    val date: String,
    val isIncome: Boolean = false
)
