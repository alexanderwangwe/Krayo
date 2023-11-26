package com.krayo.art.ui.screens.payment_methods

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.krayo.art.R

@Composable
fun CreditOrDebitScreen(
    innerPadding: PaddingValues,
    navController: NavHostController,
    function: () -> Unit
) {
    var cardNumberInput by remember { mutableStateOf("") }
    var cvvInput by remember { mutableStateOf("") }
    var expirationDateInput by remember { mutableStateOf("") }
    var cardHolderNameInput by remember { mutableStateOf("") }
    Column (
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 50.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Add a Credit or Debit card",
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(Alignment.Start),

        )
        PaymentInputFields(
            label = R.string.card_number,
            value = cardNumberInput,
            onValueChange = { cardNumberInput = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(bottom = 32.dp)


        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, top = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            BoxWithConstraints(
                modifier = Modifier
                    .width(150.dp) // Set the width of the BoxWithConstraints
                    .padding(end = 10.dp)
            ) {
                PaymentInputFields(
                    label = R.string.cvv,
                    value = cvvInput,
                    onValueChange = { cvvInput = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }

            BoxWithConstraints(
                modifier = Modifier
                    .width(200.dp) // Set the width of the BoxWithConstraints
                    .padding(start = 10.dp)
            ) {
                PaymentInputFields(
                    label = R.string.expiration_date,
                    value = expirationDateInput,
                    onValueChange = { expirationDateInput = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }
        }

        PaymentInputFields(
            label = R.string.card_holder_name,
            value = cardHolderNameInput,
            onValueChange = { cardHolderNameInput = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(bottom = 32.dp)


        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentInputFields(
    @StringRes label: Int,
    value: String, // Change this to String
    onValueChange : (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    width: Dp = Dp.Unspecified, // Add width parameter
    padding: PaddingValues = PaddingValues(0.dp) // Add padding parameter
) {
    TextField(
        label = { Text(text = stringResource(id = label))},
        singleLine = true,
        keyboardOptions = keyboardOptions,
        value = value, // This is now a String
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .width(width) // Set the width of the TextField composable
            .padding(padding), // Set the padding of the TextField composable
        colors = TextFieldDefaults.textFieldColors(textColor = Color.Black)
    )
}

