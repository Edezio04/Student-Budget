package com.example.myapplication1

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TransactionsScreen(
    transactions: List<TransactionItem> = emptyList(),
    onDeleteTransaction: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onNavigateToAddTransaction: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf("All") }

    val categoriesFilter = listOf(
        "All", "Food", "Transport", "Rent",
        "Books & Stationery", "Entertainment", "Allowance", "Other"
    )

    val filteredTransactions = transactions.filter { item ->
        val matchesSearch = item.title.contains(searchQuery, ignoreCase = true) ||
                item.category.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategoryFilter == "All" || item.category == selectedCategoryFilter
        matchesSearch && matchesCategory
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAddTransaction() },
                containerColor = Color(0xFF2563EB)
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
                .padding(16.dp)
        ) {
            // Header Row with Back Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF0F172A),
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Transaction History",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Manage and filter your records",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xFF475569)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = "Search description or category...",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 16.sp,
                        color = Color(0xFF64748B)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF475569),
                        modifier = Modifier.size(22.dp)
                    )
                },
                textStyle = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    color = Color(0xFF0F172A)
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Category Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categoriesFilter.forEach { cat ->
                    FilterChip(
                        selected = selectedCategoryFilter == cat,
                        onClick = { selectedCategoryFilter = cat },
                        label = {
                            Text(
                                text = cat,
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 15.sp,
                                fontWeight = if (selectedCategoryFilter == cat) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            if (filteredTransactions.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No transactions found",
                        color = Color(0xFF64748B),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(filteredTransactions, key = { it.id }) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        color = Color(0xFF0F172A)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${item.category} • ${item.date}",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = FontFamily.SansSerif,
                                        color = Color(0xFF475569)
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = (if (item.isIncome) "+ MK " else "- MK ") + String.format("%,.2f", item.amount),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = if (item.isIncome) Color(0xFF16A34A) else Color(0xFFDC2626)
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                IconButton(
                                    onClick = { onDeleteTransaction(item.id) },
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Transaction",
                                        tint = Color(0xFFDC2626)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransactionsScreenPreview() {
    val sampleList = listOf(
        TransactionItem(title = "Cafeteria Lunch", category = "Food", amount = 2500.0, date = "04 Oct", isIncome = false),
        TransactionItem(title = "Weekly Allowance", category = "Allowance", amount = 20000.0, date = "03 Oct", isIncome = true),
        TransactionItem(title = "Bus Fare", category = "Transport", amount = 1200.0, date = "02 Oct", isIncome = false)
    )
    MaterialTheme {
        TransactionsScreen(transactions = sampleList)
    }
}
