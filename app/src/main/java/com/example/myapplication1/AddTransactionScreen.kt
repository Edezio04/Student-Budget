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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onAddTransaction: (TransactionItem) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    var title by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Food") }
    var isIncome by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var expandedCategoryDropdown by remember { mutableStateOf(false) }

    val categories = listOf("Food", "Rent", "Books & Stationery", "Transport", "Entertainment", "Allowance", "Other")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        // Header Row
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
            Text(
                text = "Add Transaction",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFF0F172A)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Type Selector (Expense vs Income)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = !isIncome,
                        onClick = { isIncome = false },
                        label = {
                            Text(
                                text = "Expense",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.SansSerif
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = isIncome,
                        onClick = { isIncome = true },
                        label = {
                            Text(
                                text = "Income",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.SansSerif
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Title Input
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        isError = false
                    },
                    label = { Text("Description / Title", fontFamily = FontFamily.SansSerif, fontSize = 15.sp) },
                    placeholder = { Text("e.g. Lunch at Cafeteria", fontFamily = FontFamily.SansSerif, fontSize = 15.sp) },
                    singleLine = true,
                    textStyle = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 16.sp, color = Color(0xFF0F172A)),
                    isError = isError && title.isBlank(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Amount Input
                OutlinedTextField(
                    value = amountText,
                    onValueChange = {
                        amountText = it
                        isError = false
                    },
                    label = { Text("Amount (MK)", fontFamily = FontFamily.SansSerif, fontSize = 15.sp) },
                    placeholder = { Text("e.g. 2500", fontFamily = FontFamily.SansSerif, fontSize = 15.sp) },
                    singleLine = true,
                    textStyle = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 16.sp, color = Color(0xFF0F172A)),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = isError && amountText.toDoubleOrNull() == null,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Category Dropdown
                ExposedDropdownMenuBox(
                    expanded = expandedCategoryDropdown,
                    onExpandedChange = { expandedCategoryDropdown = !expandedCategoryDropdown }
                ) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category", fontFamily = FontFamily.SansSerif, fontSize = 15.sp) },
                        textStyle = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 16.sp, color = Color(0xFF0F172A)),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategoryDropdown) },
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCategoryDropdown,
                        onDismissRequest = { expandedCategoryDropdown = false }
                    ) {
                        categories.forEach { selection ->
                            DropdownMenuItem(
                                text = { Text(selection, fontFamily = FontFamily.SansSerif, fontSize = 16.sp) },
                                onClick = {
                                    category = selection
                                    expandedCategoryDropdown = false
                                }
                            )
                        }
                    }
                }

                if (isError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Please enter a valid title and numeric amount.",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.SansSerif
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Save Button
                Button(
                    onClick = {
                        val parsedAmount = amountText.toDoubleOrNull()
                        if (title.isNotBlank() && parsedAmount != null && parsedAmount > 0) {
                            val currentDate = SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date())
                            val newItem = TransactionItem(
                                title = title,
                                category = category,
                                amount = parsedAmount,
                                date = currentDate,
                                isIncome = isIncome
                            )
                            onAddTransaction(newItem)
                        } else {
                            isError = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isIncome) Color(0xFF16A34A) else Color(0xFF2563EB)
                    )
                ) {
                    Text(
                        text = if (isIncome) "Save Income" else "Save Expense",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.White
                    )
                }
            }
        }
    }
}
