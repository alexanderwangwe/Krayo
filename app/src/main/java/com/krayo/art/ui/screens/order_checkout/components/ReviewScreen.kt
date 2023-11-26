package com.krayo.art.ui.screens.order_checkout.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.krayo.art.R

@Composable
fun ReviewScreen(){
    DeliveryCard()
    Spacer(modifier = Modifier.padding(vertical = 10.dp))
    OrderSummaryCard()
    Spacer(modifier = Modifier.padding(vertical = 10.dp))
    AmountPayableCard()
    Spacer(modifier = Modifier.padding(vertical = 10.dp))
    PaymentMethodCard()
}

@Composable
fun AmountPayableCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "Sub-total", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "KES 5,000",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Divider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Delivery Fee",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "KES 500",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Divider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "KES 5,500",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun PaymentMethodCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(text = "Payment Method", style = MaterialTheme.typography.bodyLarge)
            }
            Divider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Row {
                Text(
                    text = "M-Pesa on Delivery",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
