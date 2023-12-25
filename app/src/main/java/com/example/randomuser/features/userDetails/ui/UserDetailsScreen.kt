package com.example.randomuser.features.userDetails.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.randomuser.R
import com.example.randomuser.features.map.ui.MapView
import com.example.randomuser.model.User

@Composable
fun UserDetailsScreen(user: User) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_details_screen_background_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .alpha(0.8f)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(145.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                AsyncImage(
                    model = user.pictureLarge,
                    contentDescription = stringResource(R.string.user_list_item_image_content_description),
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color.White, CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 260.dp)
        ) {
            UserInfoFieldsContainer(user)
            user.location?.let { MapView(it) }
        }
    }
}