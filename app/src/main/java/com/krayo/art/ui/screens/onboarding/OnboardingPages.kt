package com.krayo.art.ui.screens.onboarding

import androidx.annotation.DrawableRes

sealed class OnBoardingPages(
    @DrawableRes
    val image: Int,
    val number: String,
    val type: String,
    val title: String,
    val description: String
) {
    object First : OnBoardingPages(
        image = 0,
        number = "",
        type = "",
        title = "Earn more from your craft",
        description = "Sell any crafts you own with lower fees than traditional auction houses, retail store and dealers. You take home more of the final sale price."
    )

    object Second : OnBoardingPages(
        image = 0,
        number = "",
        type = "",
        title = "Reach a global network",
        description = "With a large network of collectors and enthusiasts, trade your work with the most interested buyers from all over the world."
    )

    object Third : OnBoardingPages(
        image = 0,
        number = "",
        type = "",
        title = "Connect with fellow artists",
        description = "With communities, you can interact with those who you share interests in the same field. Digital, Murals, Oil Painting, anything. You can also create your own community. Engage and help each other grow to full potential."
    )

    object Fourth : OnBoardingPages(
        image = 0,
        number = "1.",
        type = "How it works",
        title = "Submit your crafts",
        description = "• Enter the name of the piece \n• Upload the images \n• Fill in the Artwork Details \n• Set your price"
    )

    object Fifth : OnBoardingPages(
        image = 0,
        number = "2.",
        type = "How it works",
        title = "Hand over your craft",
        description = "We will take care of shipping and insurance on your behalf if its available in your region. We come to you to collect the artwork. Incase you live in an area where we can't collect, shipping will be done on your side"
    )

    object Sixth : OnBoardingPages(
        image = 0,
        number = "3.",
        type = "How it works",
        title = "Sell your crafts",
        description = "Get into contact with prospective buyers and seal the deal. Payment is held in Escrow until the buyer receives the artwork."
    )
    // , talk, negotiate

    // We will add this back when we have multiple ways of selling
//    object Sixth : OnBoardingPages(
//        image = 0,
//        number = "2",
//        type = "How it Works",
//        title = "Determine Your Sales Option",
//        description = "Review your sales strategy and price estimate \n\nSelect the best way to sell your work. Either at auction, through private sale or via direct listing."
//    )

    object Seventh : OnBoardingPages(
        image = 0,
        number = "4.",
        type = "How it Works",
        title = "Get Paid",
        description = "Once the sale is complete, payment is transferred to your account."
    )


    object Final : OnBoardingPages(
        image = 0,
        number = "5.",
        type = "How it Works",
        title = "Review Your Insights",
        description = "See how your work has performed, get insights and analytics on your sales and audience."
    )
}
