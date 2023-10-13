package com.krayo.art.ui.screens.onboarding

import androidx.annotation.DrawableRes
import com.krayo.art.R

sealed class OnBoardingPages(
    @DrawableRes
    val image: Int,
    val number: String,
    val type: String,
    val title: String,
    val description: String
) {
    object First : OnBoardingPages(
        image = R.drawable.content,
        number = "",
        type = "",
        title = "Earn more from your craft",
        description = "Sell any crafts you own with lower fees than traditional auction houses, retail store and dealers, you take home more of the final sale price."
    )

    object Second : OnBoardingPages(
        image = R.drawable.content,
        number = "",
        type = "",
        title = "Reach a global network",
        description = "With a large network of collectors and enthusiasts, trade your work with the most interested buyers from all over the world."
    )

    object Third : OnBoardingPages(
        image = R.drawable.content,
        number = "",
        type = "",
        title = "Connect with Fellow Artists",
        description = "With communities, you can also interact with those who you share interests in the same field. Digital, Murals, Oil Painting, anything. Engage and help each other grow to full potential."
    )

    object Fourth : OnBoardingPages(
        image = R.drawable.content,
        number = "1",
        type = "How it Works",
        title = "Submit Your artwork",
        description = "Enter the Artist's name, \n\nUpload the images \n\nFill in the Artwork Details"
    )

    object Fifth : OnBoardingPages(
        image = R.drawable.content,
        number = "2",
        type = "How it Works",
        title = "Determine Your Sales Option",
        description = "Review your sales strategy and price estimate \n\nSelect the best way to seell your work. Either at auction, through private sale or via direct listing."
    )

    object Sixth : OnBoardingPages(
        image = R.drawable.content,
        number = "3",
        type = "How it Works",
        title = "Sell Your Artwork",
        description = "Get into contact with prospective buyers, talk, negotiate and seal the deal."
    )

    object Final : OnBoardingPages(
        image = R.drawable.content,
        number = "4",
        type = "How it Works",
        title = "Review Your Insights",
        description = "See how your work has performed, how many people are interested and (something we'll figure out later)"
    )
}
