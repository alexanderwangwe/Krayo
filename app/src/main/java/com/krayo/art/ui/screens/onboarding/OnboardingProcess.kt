package com.krayo.art.ui.screens.onboarding

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.krayo.art.MainActivity
import com.krayo.art.R
import com.krayo.art.constants.Destinations

@OptIn( ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun FirstOnboardingScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    context: MainActivity,
    updateNavState: (Boolean) -> Unit
) {
    updateNavState(true)
    val pages = listOf(
        OnBoardingPages.First,
        OnBoardingPages.Second,
        OnBoardingPages.Third,
        OnBoardingPages.Fourth,
        OnBoardingPages.Fifth,
        OnBoardingPages.Sixth,
        OnBoardingPages.Seventh,
        OnBoardingPages.Final
    )

    var hideContent by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(bottom = paddingValues.calculateBottomPadding()),
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        val pagerState = rememberPagerState(
            pageCount =  pages.size
        )

       /*
           Box( modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(id = R.drawable.content),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
        */

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
            .padding(padding)) {

            PageIndicator(
                pageCount = pages.size,
                currentPage = pagerState.currentPage,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 15.dp),
                navController = navController
            )

            HorizontalPager(
                modifier = Modifier.weight(10f),
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { currentPage ->
                PagerScreen(onBoardingPages = pages[currentPage])
            }

            ButtonSection(
                pagerState = pagerState,
                navController = navController,
                context = context,
                padding = padding
            )
        }
    }

}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ButtonSection(
    pagerState: PagerState,
    padding: PaddingValues,
    navController: NavController,
    context: MainActivity
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            enabled = true,
            onClick = {
                onBoardingIsCompleted(context = context)
                navController.navigate(Destinations.ONBOARDING.name)
            }) {
            Text(
                text = "Start Selling",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
    }
}

@Composable
private fun PageIndicator(navController: NavController, pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        modifier = modifier.height(47.5.dp)
    ){
        Row (
            modifier = modifier.weight(1f),
            horizontalArrangement =
            Arrangement.spacedBy(0.dp,
                Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top,
        ) {
            repeat(pageCount){
                IndicatorSingleDash(isSelected = it == currentPage )
            }
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
            if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground,
            shape = RoundedCornerShape(size = 2.dp)
        )
    )
}

@Composable
fun PagerScreen(onBoardingPages: OnBoardingPages) {
//     Box( modifier = Modifier.fillMaxSize()) {
//         Image(painter = painterResource(id = onBoardingPages.image),
//             contentDescription = "Background Image",
//             contentScale = ContentScale.FillBounds,
//             modifier = Modifier.fillMaxSize()
//         )
//     }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onBoardingPages.number,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onBoardingPages.type,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onBoardingPages.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
        )

        Text(
            text = onBoardingPages.description,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight(400),
            textAlign = TextAlign.Left,
        )

    }

}

private fun onBoardingIsCompleted(context : MainActivity) {
    val sharedPreferences = context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
    val  editor = sharedPreferences.edit()
    editor.putBoolean("isComplete", true)
    editor.apply()
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
fun SeventhOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPages = OnBoardingPages.Seventh)
    }
}

@Composable
@Preview(showBackground = true)
fun FinalOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPages = OnBoardingPages.Final)
    }
}