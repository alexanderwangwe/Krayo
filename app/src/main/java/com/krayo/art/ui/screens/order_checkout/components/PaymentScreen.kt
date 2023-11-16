package com.krayo.art.ui.screens.order_checkout.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PaymentScreen() {
    Column {
        PaymentsCard()
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
    }
}

@Composable
fun PaymentsCard() {
    var listOfPayments = listOf("M-Pesa on Delivery", "Credit Card", "M-pesa")
    var selectedPayment by remember { mutableStateOf(listOfPayments[0]) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {

        for (payment in listOfPayments) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedPayment = payment
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = payment,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                )
                RadioButton(
                    selected = payment == selectedPayment,
                    onClick = {
                        selectedPayment = payment
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
            if (payment != listOfPayments.last())
                Divider(
                    color = MaterialTheme.colorScheme.onBackground
                )
        }
    }
}
