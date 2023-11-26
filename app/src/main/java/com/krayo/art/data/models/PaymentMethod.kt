package com.krayo.art.data.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PaymentMethod(
    val paymentName: String,
    @DrawableRes val paymentIcon: Int,
)
