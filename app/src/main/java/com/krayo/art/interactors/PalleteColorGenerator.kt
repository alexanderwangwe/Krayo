package com.krayo.art.interactors

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.palette.graphics.Palette

// DO NOT TOUCH. STILL WORKING ON THIS
object PalleteColorGenerator {

//    suspend fun convertImageUrlToBitmap(
//        imageUrl: String,
//        context: Context
//    ): Bitmap? {
//        val loader = ImageLoader(context = context)
//        val request = ImageRequest.Builder(context = context)
//            .data(imageUrl)
//            .allowHardware(false)
//            .build()
//        val imageResult = loader.execute(request = request)
//        return if (imageResult is SuccessResult) {
//            (imageResult.drawable as BitmapDrawable).bitmap
//        } else {
//            null
//        }
//    }

    suspend fun convertImageToBitmap(
        image: Drawable,
        context: Context
    ): Bitmap? {
        // TODO: Convert an image drawable passed down to a bitmap

        return (image as BitmapDrawable).bitmap
    }

    fun extractColorsFromBitmap(bitmap: Bitmap): Map<String, String> {
        return mapOf(
            "vibrant" to parseColorSwatch(
                color = Palette.from(bitmap).generate().vibrantSwatch
            ),
            "darkVibrant" to parseColorSwatch(
                color = Palette.from(bitmap).generate().darkVibrantSwatch
            ),
            "onDarkVibrant" to parseBodyColor(
                color = Palette.from(bitmap).generate().darkVibrantSwatch?.bodyTextColor
            )
        )
    }

    private fun parseColorSwatch(color: Palette.Swatch?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color.rgb)
            return "#$parsedColor"
        } else {
            "#000000"
        }
    }

    private fun parseBodyColor(color: Int?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color)
            "#$parsedColor"
        } else {
            "#FFFFFF"
        }
    }

}