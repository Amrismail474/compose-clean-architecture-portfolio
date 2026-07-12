package com.example.uisamples.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FintechDepositField(
    onContinueClick: (String) -> Unit = {}
) {
    var rawAmount by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Helper: Formats string digits to currency format (e.g. 10000 -> 10,000)
    val formattedAmount = remember(rawAmount) {
        if (rawAmount.isEmpty()) "0"
        else {
            try {
                val number = rawAmount.toLong()
                NumberFormat.getNumberInstance(Locale.US).format(number)
            } catch (e: Exception) {
                rawAmount
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Enter Deposit Amount",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )

        // 1. Text Field with automated Naira symbol prefix and number filtering
        OutlinedTextField(
            value = "₦$formattedAmount",
            onValueChange = { amountInput ->
                // Filter to digits only
                val digitsOnly = amountInput.filter { it.isDigit() }
                if (digitsOnly.length <= 12) {
                    rawAmount = digitsOnly
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0F172A)
            ),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2563EB),
                unfocusedBorderColor = Color(0xFFCBD5E1),
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        // 2. Quick Select FlowRow (Spaced out grid of values)
        val quickAmounts = listOf("10,000", "20,000", "50,000", "100,000", "300,000", "500,000")
        
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            quickAmounts.forEach { amountStr ->
                Box(
                    modifier = Modifier
                        .width(98.dp)
                        .height(40.dp)
                        .background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp))
                        .clickable {
                            // Extract digits and assign
                            rawAmount = amountStr.filter { it.isDigit() }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "₦$amountStr",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF475569)
                    )
                }
            }
        }

        // 3. Action Button
        Button(
            onClick = { onContinueClick(rawAmount) },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
            enabled = rawAmount.isNotEmpty()
        ) {
            Text(text = "Continue", color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFintechDepositField() {
    FintechDepositField()
}
