package com.example.myapplication1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(
    transactions: List<TransactionItem> = emptyList(),
    monthlyBudget: Double = 100000.0,
    onNavigateToSetBudget: () -> Unit = {},
    onNavigateToAddTransaction: () -> Unit = {},
    onNavigateToTransactions: () -> Unit = {}
) {
    val totalIncome = transactions.filter { it.isIncome }.sumOf { it.amount }
    val totalExpenses = transactions.filter { !it.isIncome }.sumOf { it.amount }

    // Remaining balance formula: (Budget + Income) - Expenses
    val remainingBalance = (monthlyBudget + totalIncome) - totalExpenses

    // High-precision budget progress calculation
    val budgetProgress = if (monthlyBudget > 0) (totalExpenses / monthlyBudget).coerceIn(0.0, 1.0) else 0.0

    // Expense breakdown grouped by category
    val categoryExpenses = transactions
        .filter { !it.isIncome }
        .groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { it.amount } }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAddTransaction() },
                containerColor = Color(0xFF3B82F6)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Transaction",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F6F8))
                .statusBarsPadding()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header Row (Fixed to prevent "Set Budget" vertical squishing)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Student Budget Manager",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF1E293B)
                    )
                    Text(
                        text = "Welcome back, Student!",
                        fontSize = 13.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(
                    onClick = { onNavigateToSetBudget() },
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Set Budget",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF3B82F6),
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Navigation Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { onNavigateToAddTransaction() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                ) {
                    Text(
                        text = "+ Add Item",
                        fontFamily = FontFamily.SansSerif
                    )
                }

                OutlinedButton(
                    onClick = { onNavigateToTransactions() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "View History",
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Budget Warning Alert Banner
            if (budgetProgress >= 0.8) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Alert",
                            tint = Color(0xFFC62828)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (budgetProgress >= 1.0)
                                "⚠️ You have exceeded your set monthly budget limit!"
                            else
                                "⚠️ Warning: You have used ${(budgetProgress * 100).toInt()}% of your budget limit!",
                            color = Color(0xFFC62828),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }
            }

            // Remaining Balance Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Remaining Balance",
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "MK " + String.format("%,.2f", remainingBalance),
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Income & Expenses Summary Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    title = "Total Income",
                    amount = "MK " + String.format("%,.2f", totalIncome),
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = "Total Expenses",
                    amount = "MK " + String.format("%,.2f", totalExpenses),
                    color = Color(0xFFC62828),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Budget Progress Bar Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Monthly Budget Progress",
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.SansSerif,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = "${(budgetProgress * 100).toInt()}% Used",
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            color = if (budgetProgress > 0.8) Color(0xFFC62828) else Color(0xFF2E7D32)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { budgetProgress.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = if (budgetProgress > 0.8) Color(0xFFC62828) else Color(0xFF2E7D32),
                        trackColor = Color(0xFFE2E8F0)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Spent MK " + String.format("%,.2f", totalExpenses) + " of MK " + String.format("%,.2f", monthlyBudget),
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Category Breakdown Section
            Text(
                text = "Expense Summary by Category",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (categoryExpenses.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No expenses recorded yet",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    categoryExpenses.forEach { (catName, catAmount) ->
                        val catFraction = if (totalExpenses > 0) (catAmount / totalExpenses).toFloat() else 0f
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = catName,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                    Text(
                                        text = "MK " + String.format("%,.2f", catAmount),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                LinearProgressIndicator(
                                    progress = { catFraction },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp),
                                    color = Color(0xFF3B82F6),
                                    trackColor = Color(0xFFE2E8F0)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryCard(title: String, amount: String, color: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 12.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = amount,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = color
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    MaterialTheme {
        DashboardScreen(
            transactions = listOf(
                TransactionItem(title = "Cafeteria Lunch", category = "Food", amount = 2500.0, date = "04 Oct", isIncome = false),
                TransactionItem(title = "Allowance", category = "Allowance", amount = 100000.0, date = "01 Oct", isIncome = true)
            )
        )
    }
}