package com.example.demo

import androidx.lifecycle.SavedStateHandle
import com.example.data.models.user.ui.User
import com.example.data.repository.UsersRepository
import com.example.demo.ui.user.Contract
import com.example.demo.ui.user.UserViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserViewModelTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    private val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var vm: UserViewModel

    private var rep = mockk<UsersRepository>()

    @Test
    @Before
    fun setUp() {
        val user = User()
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { savedStateHandle.get<String>("userId") } returns "1"
        coEvery { rep.getUserById(1) } returns user
        vm = UserViewModel(savedStateHandle, rep)
        vm.onEvent(Contract.Event.ShowDetails(1))
        assertEquals(user, vm.state.value.user)
    }
}