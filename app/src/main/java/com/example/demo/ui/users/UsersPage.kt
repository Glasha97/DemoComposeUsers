package com.example.demo.ui.users

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.example.data.models.user.ui.User
import com.example.demo.ui.users.Contract.Event.OnUpdateFavouriteClicked

@Composable
fun UsersPage(viewModel: UsersPageViewModel, onUserClick: (User) -> Unit) {
    val state = viewModel.state.collectAsState()
    val users = state.value.pagingFlow.collectAsLazyPagingItems()
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Users", color = MaterialTheme.colors.secondary, fontWeight = W700, fontSize = 24.sp)
            Row() {
                Button(onClick = { viewModel.onEvent(Contract.Event.FetchUsers) }) {
                    Text(
                        text = "Refresh",
                        color = MaterialTheme.colors.primaryVariant
                    )
                }
                Spacer(modifier = Modifier.size(12.dp))
                Button(onClick = { viewModel.onEvent(Contract.Event.OnFavouriteClicked) }) {
                    Text(text = "Favourite", color = MaterialTheme.colors.primaryVariant)
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(users, key = { _, item -> item.id }) { _, item ->
                item?.let {
                    if (!state.value.showOnlyFavourite || (state.value.showOnlyFavourite && it.isFavourite)) {
                        UserItem(
                            it,
                            onUserClick = { onUserClick(item) },
                            onFavouriteClick = {
                                viewModel.onEvent(OnUpdateFavouriteClicked(!it.isFavourite, it.id))
                            }
                        )
                    }
                }
            }

        }

        when (val pagingState = users.loadState.refresh) {
            is LoadState.Error -> Toast.makeText(
                LocalContext.current,
                pagingState.error.message,
                Toast.LENGTH_SHORT
            ).show()
            LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {}
        }
    }
}

@Composable
fun UserItem(user: User, onUserClick: () -> Unit, onFavouriteClick: () -> Unit) {
    Spacer(modifier = Modifier.size(8.dp))
    Card(
        modifier = Modifier
            .background(MaterialTheme.colors.primaryVariant, RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .clickable { onUserClick() },
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant
    ) {
        
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = user.avatar,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.size(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "${user.firstName} ${user.lastName}", color = MaterialTheme.colors.onPrimary)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = user.email, color = MaterialTheme.colors.onPrimary)
            }
            Spacer(modifier = Modifier.size(12.dp))

            IconButton(onClick = { onFavouriteClick() }) {
                Icon(
                    painter = painterResource(id = if (user.isFavourite) com.example.design_system.R.drawable.ic_star_filled else com.example.design_system.R.drawable.ic_star_outline),
                    contentDescription = null
                )
            }
        }
    }
}
