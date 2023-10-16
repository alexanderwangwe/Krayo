package com.krayo.art.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.krayo.art.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Poppins")

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.Normal)
)

val fontFamilyBold = FontFamily(
    Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.Bold)
)

val fontFamilyLight = FontFamily(
    Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.Light)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamilyLight,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamilyLight,
        fontWeight = FontWeight.Light,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 48.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = fontFamilyLight,
        fontWeight = FontWeight.Light,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    )
)