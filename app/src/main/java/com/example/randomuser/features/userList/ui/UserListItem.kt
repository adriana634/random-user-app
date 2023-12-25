package com.example.randomuser.features.userList.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.randomuser.R
import com.example.randomuser.features.userList.model.User
import com.example.randomuser.ui.theme.RandomUserTheme

/**
 * Composable function representing an individual user item in the list.
 *
 * @param user The user data to be displayed.
 */
@Composable
fun UserListItem(user: User, onItemClick: ((User) -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick?.invoke(user) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.picture,
            contentDescription = stringResource(R.string.user_list_item_image_content_description),
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = user.name, fontWeight = FontWeight.Bold)
            Text(text = user.email, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListItemPreview() {
    RandomUserTheme {
        val sampleUser = getSampleUser()
        UserListItem(user = sampleUser, onItemClick = null)
    }
}

private fun getSampleUser(): User {
    return User("John Doe", "john.doe@example.com", "https://placehold.co/52x52/png")
}