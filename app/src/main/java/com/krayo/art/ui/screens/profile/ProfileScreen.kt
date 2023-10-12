package com.krayo.art.ui.screens.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.krayo.art.R

@Composable
fun ProfileScreen(navController: NavController, paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(Color.Gray)
    ) {
        // Display the image
        Image(
            painter = painterResource(id = R.drawable.csm_header),
            contentDescription = "Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                top = 136.dp,
                end = 8.dp,
                bottom = 16.dp
            )
    ) {
        // Profile Picture
        Image(
            painter = painterResource(id = R.drawable.tanjiro_ending_gyutaro),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        // Spacer for some spacing between profile picture and name
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            // Artist name
            Text("Kurto", fontWeight = FontWeight.Bold, color = Color.White)
            // Spacer for some spacing between artist name and their @
            Spacer(modifier = Modifier.height(1.dp))
            // Artist username
            Text("@kurtoart", fontWeight = FontWeight.Light, fontSize = 10.sp, color = Color.Gray)
            // Spacer for some spacing below the artists name and followers
            Spacer(modifier = Modifier.height(4.dp))
            Column {
                Row(
                    modifier = Modifier,
                ) {
                    // Number of followers
                    Text("78 following", fontWeight = FontWeight.Medium, fontSize = 12.sp, color = Color.White)
                    // Spacer for some spacing between 'followers' and 'following'
                    Spacer(modifier = Modifier.width(16.dp))
                    // Number of followers
                    Text("400 followers", fontWeight = FontWeight.Medium, fontSize = 12.sp, color = Color.White)
                }
            }
        }
    }
    Column {
        // Profile headers horizontally arranged
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    top = 224.dp,
                    end = 8.dp,
                    bottom = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Posts", fontWeight = FontWeight.Bold, color = Color.White)
            Text("My Collections", fontWeight = FontWeight.Light, color = Color.Gray)
            Text("Likes", fontWeight = FontWeight.Light, color = Color.Gray)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 8.dp,
                top = 360.dp,
                end = 8.dp,
                bottom = 16.dp
            ),
        // 'camera' image horizontally aligned to be in the center
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_camera),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)  // Size of the 'camera' image
                .padding(8.dp) // Padding for space between image and content below it
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Uh oh", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
            Text("Looks like you have no", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
            Text("artwork posts yet", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
        }
    }
}