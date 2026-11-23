package com.example.myapplication1.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val category: String,
    val amount: Double,
    val date: String,
    val isIncome: Boolean
)
