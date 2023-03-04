package com.example.demo.ui.user

import com.example.core.UiEffect
import com.example.core.UiEvent
import com.example.core.UiState
import com.example.data.models.user.ui.User

interface Contract {
    data class State(val user: User?) : UiState

    sealed interface Event : UiEvent {
        data class ShowDetails(val userId: Long) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowDetails(val user: User?) : Effect
    }
}