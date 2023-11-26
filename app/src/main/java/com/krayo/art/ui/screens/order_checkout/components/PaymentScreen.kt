package com.krayo.art.ui.screens.order_checkout.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.krayo.art.data.DataSource
import com.krayo.art.data.models.PaymentMethod

@Composable
fun PaymentScreen() {
    Column {
        PaymentMethodList(
            paymentMethodList = DataSource().loadPaymentMethods()
        )
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
    }
}

@Composable
fun PaymentCard(paymentMethod: PaymentMethod, modifier: Modifier = Modifier){

    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        Row(Modifier.padding(15.dp)){
            Icon(
                painter = painterResource(id = paymentMethod.paymentIcon),
                contentDescription = paymentMethod.paymentName,
                modifier = Modifier
                    .padding(10.dp)
                    .size(24.dp)
            )
            Text(
                text = paymentMethod.paymentName,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
            )
        }
    }
}

@Composable
fun PaymentMethodList(paymentMethodList: List<PaymentMethod>, modifier: Modifier = Modifier) {
    var selectedPayment by remember { mutableStateOf(paymentMethodList[0]) }
    Column(modifier = modifier) {
        for (paymentMethod in paymentMethodList) {
            PaymentCard(
                paymentMethod = paymentMethod,
                modifier = Modifier.clickable { selectedPayment = paymentMethod }
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
        }
    }

}
