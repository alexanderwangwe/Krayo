package com.krayo.art

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.krayo.art.data.models.BottomMenuContent
import com.krayo.art.ui.theme.KrayoTheme
import com.krayo.art.ui.theme.fontFamily
import com.krayo.art.ui.theme.fontName

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KrayoTheme{
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                    ){
                        BottomMenu(items = listOf(
                            BottomMenuContent("Home", R.drawable.outline_home_24),
                            BottomMenuContent("Discover", R.drawable.outline_remove_red_eye_24),
                            BottomMenuContent("Content creation", R.drawable.baseline_add_24),
                            BottomMenuContent("Chat", R.drawable.baseline_chat_bubble_outline_24),
                            BottomMenuContent("Profile", R.drawable.outline_person_24),
                        ))
                    }
                }
            }
        }
    }
}

@Composable
fun BottomMenu(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighLightColor: Color = MaterialTheme.colorScheme.secondary,
    activeTextColor: Color = MaterialTheme.colorScheme.secondary,
    inactiveTextColor: Color = MaterialTheme.colorScheme.primary,
    intialSelectedItemIndex: Int = 0,
){
    var seletedItemIndex by remember { mutableIntStateOf(intialSelectedItemIndex) }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        items.forEachIndexed { index, item ->
            BottomMenuItem(
                item = item,
                modifier = modifier,
                activeHighLightColor = activeHighLightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor,
                isSelected = index == seletedItemIndex,
                onClick = { seletedItemIndex = index }
            )
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    modifier: Modifier = Modifier,
    activeHighLightColor: Color = MaterialTheme.colorScheme.secondary,
    activeTextColor: Color = MaterialTheme.colorScheme.secondary,
    inactiveTextColor: Color = Color.White,
    isSelected: Boolean = false,
    onClick: () -> Unit
){
    val iconTint = if(isSelected) activeTextColor else Color.White
    val backgroundColor = if(isSelected) activeHighLightColor else Color.Transparent
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(10.dp)
            .clickable { onClick() }
    ){
        Icon(
            painter = painterResource(id = item.iconId),
            contentDescription = item.title,
            tint = iconTint
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        fontWeight = FontWeight.Light,
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KrayoTheme {
        Greeting("Android")
    }
}