package com.example.randomuser.features.userDetails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomuser.model.User
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun UserInfoFieldsContainer(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        UserInfoField(
            icon = Icons.Default.Person,
            fieldName = "User",
            fieldValue = user.name
        )

        UserInfoField(
            icon = Icons.Default.Email,
            fieldName = "Email",
            fieldValue = user.email
        )

        UserInfoField(
            icon = Icons.Default.Face,
            fieldName = "Gender",
            fieldValue = user.gender
        )

        val formattedRegisteredDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(user.registeredDate)
        UserInfoField(
            icon = Icons.Default.DateRange,
            fieldName = "Registered date",
            fieldValue = formattedRegisteredDate
        )

        UserInfoField(
            icon = Icons.Default.Phone,
            fieldName = "Cell",
            fieldValue = user.cell
        )
    }
}