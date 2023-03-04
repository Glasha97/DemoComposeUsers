package com.example.demo.ui.users

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.core.BaseViewModel
import com.example.core.UiState
import com.example.data.models.user.toUi
import com.example.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UsersPageViewModel @Inject constructor(private val repository: UsersRepository) :
    BaseViewModel<Contract.State, Contract.Event, Contract.Effect>(Contract.State()) {

    init {
        onEvent(Contract.Event.FetchUsers)
    }

    override fun reduce(event: Contract.Event, state: UiState): Flow<Contract.Effect> =
        when (event) {
            Contract.Event.FetchUsers -> {
                flow { emit(Contract.Effect.InitPaging) }
            }
            is Contract.Event.OnUpdateFavouriteClicked -> flowOf(
                Contract.Effect.UpdateFavouriteClicked(
                    event.isFavourite,
                    event.userId
                )
            )
            Contract.Event.OnFavouriteClicked -> flowOf(Contract.Effect.UpdateFavourite)
        }

    override suspend fun updateState(
        effect: Contract.Effect,
        state: Contract.State
    ): Contract.State =
        when (effect) {
            is Contract.Effect.InitPaging -> state.copy(
                pagingFlow = repository.getUserPaging().cachedIn(viewModelScope).catch { }
                    .map {
                        it.map { user -> user.toUi() }
                    },
            )
            is Contract.Effect.UpdateFavouriteClicked -> {
                repository.updateIsFavourite(effect.isFavourite, effect.userId)
                state
            }
            Contract.Effect.UpdateFavourite -> {
                val showOnlyFavourite = !state.showOnlyFavourite
                val favouriteList =
                    if (showOnlyFavourite) repository.getFavouriteUsers() else emptyList()
                state.copy(favouriteList = favouriteList, showOnlyFavourite = showOnlyFavourite)
            }
        }
}