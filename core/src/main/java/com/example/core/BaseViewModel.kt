package com.example.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
abstract class BaseViewModel<S : UiState, Ev : UiEvent, E : UiEffect>(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state

    fun onEvent(event: Ev) {
        flowOf(event)
            .flatMapConcat { reduce(event, _state.value) }
            .map { effect -> updateState(effect, _state.value) }
            .flowOn(Dispatchers.IO)
            .onEach { newState -> _state.update { newState } }
            .launchIn(viewModelScope)

    }

    abstract fun reduce(event: Ev, state: UiState): Flow<E>
    abstract suspend fun updateState(effect: E, state: S): S

}
