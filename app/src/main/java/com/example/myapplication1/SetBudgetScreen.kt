package com.example.myapplication1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SetBudgetScreen(
    currentBudget: Double = 100000.0,
    onSaveBudget: (Double) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    var budgetInput by remember { mutableStateOf(if (currentBudget > 0) currentBudget.toInt().toString() else "") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
            .statusBarsPadding()
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
                    tint = Color(0xFF1E293B)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Set Monthly Budget",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFF1E293B)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Input Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Enter Your Budget Limit (MK)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = budgetInput,
                    onValueChange = { input ->
                        budgetInput = input
                        isError = false
                    },
                    label = { Text("Monthly Budget Amount", fontFamily = FontFamily.SansSerif) },
                    placeholder = { Text("e.g. 100000", fontFamily = FontFamily.SansSerif) },
                    singleLine = true,
                    isError = isError,
                    textStyle = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 14.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                if (isError) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Please enter a valid amount greater than 0",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val parsedBudget = budgetInput.toDoubleOrNull()
                        if (parsedBudget != null && parsedBudget > 0) {
                            onSaveBudget(parsedBudget)
                            onNavigateBack()
                        } else {
                            isError = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                ) {
                    Text(
                        text = "Save Budget",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.White
                    )
                }
            }
        }
    }
}