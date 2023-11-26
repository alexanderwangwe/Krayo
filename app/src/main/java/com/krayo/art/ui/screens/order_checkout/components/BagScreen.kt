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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.krayo.art.R


@Composable
fun BagScreen() {
    Column {
        DeliveryCard()
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        OrderSummaryCard()
    }
}

@Composable
fun DeliveryCard(
    canChangeDelivery: Boolean = true
) {
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
        Column(
            Modifier
                .padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Outlined.LocationOn, contentDescription = null)
                    Text(text = "Delivery Address", modifier = Modifier.padding(start = 8.dp))
                }
                if (canChangeDelivery)
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground,
                        ), onClick = { /*TODO*/ }) {
                        Text(
                            "Change",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
            }
            Divider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Some random address near our school or something like that, Nairobi, Kenya",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}

@Composable
fun OrderSummaryCard() {
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
            .height(250.dp)
    ) {
        Column(
            Modifier
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(text = "Order Summary", style = MaterialTheme.typography.bodyLarge)
            }
            Divider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Row {
                Surface(
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    Image(contentScale = ContentScale.Crop,modifier = Modifier.fillMaxSize(),painter = painterResource(id = R.drawable.content), contentDescription = null)
                }
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .weight(1.5f)
                        .fillMaxSize()
                ){
                    Column{
                        Text("Spider Man")
                        Text(maxLines = 2,overflow = TextOverflow.Ellipsis, text = "A piece on spider man flying over manhattan with MJ on his back", style = MaterialTheme.typography.bodyMedium)
                    }
                    Text(text = "Ksh 5000", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}
