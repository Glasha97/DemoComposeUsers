package com.example.demo.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun UserPage(vm: UserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val user = vm.state.collectAsState().value.user
        AsyncImage(
            model = user?.avatar,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(CenterHorizontally)
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.size(16.dp))

        UserInfoRow("Email", user?.email ?: "")
        UserInfoRow("First Name", user?.firstName ?: "")
        UserInfoRow("Last Name", user?.lastName ?: "")

    }
}

@Composable
private fun UserInfoRow(placeHolder: String, data: String) {
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 4.dp))
            .background(color =  MaterialTheme.colors.primaryVariant)
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        Text(text = placeHolder, color = MaterialTheme.colors.onPrimary)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = data, color = MaterialTheme.colors.onPrimary)
    }

    Spacer(modifier = Modifier.size(16.dp))
}