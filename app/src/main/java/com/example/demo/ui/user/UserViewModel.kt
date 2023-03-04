package com.example.demo.ui.user

import androidx.lifecycle.SavedStateHandle
import com.example.core.BaseViewModel
import com.example.core.UiState
import com.example.data.models.user.ui.User
import com.example.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val usersRepository: UsersRepository
) : BaseViewModel<Contract.State, Contract.Event, Contract.Effect>(Contract.State(User())) {

    init {
        savedStateHandle.get<String>("userId")?.toLongOrNull()
            ?.let { onEvent(Contract.Event.ShowDetails(it)) }
    }

    override fun reduce(event: Contract.Event, state: UiState): Flow<Contract.Effect> =
        when (event) {
            is Contract.Event.ShowDetails -> flow {
                emit(Contract.Effect.ShowDetails(usersRepository.getUserById(event.userId)))
            }
        }

    override suspend fun updateState(effect: Contract.Effect, state: Contract.State): Contract.State =
        when (effect) {
            is Contract.Effect.ShowDetails -> state.copy(user = effect.user)
        }
}
