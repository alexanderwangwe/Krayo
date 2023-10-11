package com.krayo.art.ui.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.krayo.art.constants.Destinations

//@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun OnboardingScreen(navController: NavHostController, innerPadding: PaddingValues) {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome to Krayo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 153.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )

            Text(
                text = "What are you trying to achieve?",
                modifier = Modifier
                    .width(302.dp)

                    .padding(top = 104.dp),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight(700),

            )

            Button(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color(0xFFD9D9D9),
                        shape = RoundedCornerShape(size = 15.dp)
                    )
                    .height(50.dp)
                    .width(320.dp),
                    //.padding(top = 90.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                enabled = true,
                onClick = { /*TODO*/ },

            ) {

                Text(text = "Post and sell your creations")
            }

            Button(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color(0xFFD9D9D9),
                        shape = RoundedCornerShape(size = 15.dp)
                    )
                    .height(50.dp)
                    .width(320.dp),
                    //.padding(top = 108.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                enabled = true,
                onClick = {
                    navController.navigate(OnboardingProcess.FirstOnboardingScreen.route)
                },

                ) {
                Text(text = "Simply just post your artwork")
                //Icon(painter = painterResource(id = R.drawable.carbon_next_outline),
                //contentDescription = null)
            }
        }


    }
}

@Composable
fun OnboardingProcess(){

}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@Composable
fun FirstOnboardingScreen(
    navController: NavHostController
) {
    val pages = listOf(
        OnBoardingPages.First,
        OnBoardingPages.Second,
        OnBoardingPages.Third,
        OnBoardingPages.Fourth,
        OnBoardingPages.Fifth,
        OnBoardingPages.Sixth,
        OnBoardingPages.Final
    )
    val pagerState = rememberPagerState(pageCount = {7})

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            PagerScreen(onBoardingPages = pages[position])
        }

        FinishButton(
            modifier = Modifier.weight(1f),
            pagerState = pagerState
        ) {
            /* welcomeViewModel.saveOnBoardingState(completed = true)
            navController.popBackStack()
            navController.navigate(Destinations.CONTENT_CREATION.name) */
        }
    }
}

@Composable
fun PagerScreen(onBoardingPages: OnBoardingPages) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        /* Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.7f),
            painter = painterResource(id = onBoardingPages.image),
            contentDescription = "Pager Image"
        ) */
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onBoardingPages.title,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp),
            text = onBoardingPages.description,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 6
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                Text(text = "Start Selling")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FirstOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPages = OnBoardingPages.First)
    }
}

@Composable
@Preview(showBackground = true)
fun SecondOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPages = OnBoardingPages.Second)
    }
}

@Composable
@Preview(showBackground = true)
fun ThirdOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPages = OnBoardingPages.Third)
    }
}

@Composable
@Preview(showBackground = true)
fun FourthOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPages = OnBoardingPages.Fourth)
    }
}

@Composable
@Preview(showBackground = true)
fun FifthOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPages = OnBoardingPages.Fifth)
    }
}

@Composable
@Preview(showBackground = true)
fun SixthOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPages = OnBoardingPages.Sixth)
    }
}

@Composable
@Preview(showBackground = true)
fun FinalOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPages = OnBoardingPages.Final)
    }
}
