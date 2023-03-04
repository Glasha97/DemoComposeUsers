package com.example.demo.ui.users

import androidx.paging.PagingData
import com.example.core.UiEffect
import com.example.core.UiEvent
import com.example.core.UiState
import com.example.data.models.user.ui.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface Contract {

    data class State(
        // for usual flow we can keep here loading state etc, but here we have It in paging as default
        val pagingFlow: Flow<PagingData<User>> = emptyFlow(),
        val favouriteList: List<User> = emptyList(),
        val showOnlyFavourite: Boolean = false
    ) : UiState

    sealed interface Event : UiEvent {
        object FetchUsers : Event
        data class OnUpdateFavouriteClicked(val isFavourite: Boolean, val userId: Long) : Event
        object OnFavouriteClicked : Event
    }

    sealed interface Effect : UiEffect {
        object InitPaging : Effect
        object UpdateFavourite : Effect
        data class UpdateFavouriteClicked(val isFavourite: Boolean, val userId: Long) : Effect
    }
}