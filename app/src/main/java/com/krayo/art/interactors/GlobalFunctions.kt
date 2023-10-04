package com.krayo.art.interactors

import android.content.Context
import android.content.Intent

class GlobalFunctions {
    fun ShareSheetMessage(message: String): Intent {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
        return Intent.createChooser(sendIntent, null)
    }
}