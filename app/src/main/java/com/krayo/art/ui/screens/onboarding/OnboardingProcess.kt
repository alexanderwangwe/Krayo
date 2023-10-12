package com.krayo.art.ui.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.krayo.art.ui.screens.home.TopBar

@OptIn( ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun FirstOnboardingScreen(
    navController: NavHostController,
    paddingValues: PaddingValues
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

    val navController = rememberNavController()

    var hideContent by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(bottom = paddingValues.calculateBottomPadding()),

    ) { padding ->
        val pagerState = rememberPagerState(
            pageCount =  pages.size
        )

        Column(modifier = Modifier.fillMaxSize()) {

            PageIndicator(
                pageCount = pages.size,
                currentPage = pagerState.currentPage,
                modifier = Modifier
                    .padding(top = 60.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )

            HorizontalPager(
                modifier = Modifier.weight(10f),
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { currentPage ->
                PagerScreen(onBoardingPages = pages[currentPage])
            }


            /*FinishButton(
                modifier = Modifier.weight(1f),
                pagerState = pagerState
            ) {
                /* welcomeViewModel.saveOnBoardingState(completed = true)
                navController.popBackStack()
                navController.navigate(Destinations.CONTENT_CREATION.name) */
            }*/
        }
    }

}

@Composable
private fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row (
        horizontalArrangement =
            Arrangement.spacedBy(0.dp,
            Alignment.CenterHorizontally),
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) {
        repeat(pageCount){
            IndicatorSingleDash(isSelected = it == currentPage )
        }
    }
}

@Composable
private fun IndicatorSingleDash(isSelected: Boolean) {

    Box(modifier = Modifier
        .padding(2.dp)
        .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
        .width(30.dp)
        .height(4.dp)
        .clip(CircleShape)
        .background(
            if (isSelected) Color(0xFF30D69A) else Color(0xFFD9D9D9),
            shape = RoundedCornerShape(size = 2.dp)
        )
    )
}

@Composable
private fun PagerScreen(onBoardingPages: OnBoardingPages) {
    /* Box( modifier = Modifier.fillMaxSize()) {
         Image(painter = painterResource(id = onBoardingPages.image),
             contentDescription = "Background Image",
             contentScale = ContentScale.FillBounds,
             modifier = Modifier.matchParentSize()
         )
     } */

    Column(
        modifier = Modifier
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 46.dp)
                .padding(top = 36.dp),
            text = onBoardingPages.type,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary

        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 46.dp)
                .padding(top = 120.dp),
            text = onBoardingPages.title,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            color = MaterialTheme.colorScheme.onPrimary

        )
        Text(
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .padding(top = 46.dp)
                .width(302.dp),
            text = onBoardingPages.description,
            //fontSize = 16.sp,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight(400),
            textAlign = TextAlign.Left,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@ExperimentalAnimationApi
@Composable
private fun FinishButton(
    modifier: Modifier,
    pagerState: com.google.accompanist.pager.PagerState,
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