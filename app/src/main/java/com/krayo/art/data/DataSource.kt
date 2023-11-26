package com.krayo.art.data

import com.krayo.art.R
import com.krayo.art.data.models.PaymentMethod

class DataSource {
    fun loadPaymentMethods(): List<PaymentMethod> {
        return listOf(
            PaymentMethod(
                paymentName = "M-Pesa",
                paymentIcon = R.drawable.icons8_mpesa
            ),
            PaymentMethod(
                paymentName = "Debit or Credit Card",
                paymentIcon = R.drawable.bank_card_svgrepo_com
            ),
            PaymentMethod(
                paymentName = "Paypal",
                paymentIcon = R.drawable.paypal_svgrepo_com
            ),
            PaymentMethod(
                paymentName = "Pay with cash at a location near you",
                paymentIcon = R.drawable.cash_svgrepo_com
            )
        )
    }
}