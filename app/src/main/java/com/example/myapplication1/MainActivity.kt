package com.example.myapplication1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Clear, Large, High-Legibility Typography Definition
val CustomTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 0.15.sp
    ),
    displayLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.25.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.4.sp
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                typography = CustomTypography,
                colorScheme = lightColorScheme(
                    primary = Color(0xFF3B82F6),
                    secondary = Color(0xFF1E293B),
                    background = Color(0xFFF4F6F8),
                    surface = Color.White
                )
            ) {
                val navController = rememberNavController()
                val budgetViewModel: BudgetViewModel = viewModel()

                // Real-time Room database flow states
                val monthlyBudget by budgetViewModel.monthlyBudget.collectAsState()
                val transactionsList by budgetViewModel.transactions.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = "dashboard"
                ) {
                    composable(route = "dashboard") {
                        DashboardScreen(
                            transactions = transactionsList,
                            monthlyBudget = monthlyBudget,
                            onNavigateToSetBudget = { navController.navigate("set_budget") },
                            onNavigateToAddTransaction = { navController.navigate("add_transaction") },
                            onNavigateToTransactions = { navController.navigate("transactions_list") }
                        )
                    }

                    composable(route = "set_budget") {
                        SetBudgetScreen(
                            currentBudget = monthlyBudget,
                            onSaveBudget = { newAmount ->
                                budgetViewModel.updateBudget(newAmount)
                            },
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }

                    composable(route = "add_transaction") {
                        AddTransactionScreen(
                            onAddTransaction = { newItem ->
                                budgetViewModel.addTransaction(newItem)
                                navController.popBackStack()
                            },
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }

                    composable(route = "transactions_list") {
                        TransactionsScreen(
                            transactions = transactionsList,
                            onDeleteTransaction = { idToDelete ->
                                budgetViewModel.deleteTransaction(idToDelete)
                            },
                            onNavigateBack = { navController.popBackStack() },
                            onNavigateToAddTransaction = { navController.navigate("add_transaction") }
                        )
                    }
                }
            }
        }
    }
}
