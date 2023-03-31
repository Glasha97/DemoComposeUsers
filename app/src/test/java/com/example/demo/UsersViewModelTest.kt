package com.example.demo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.data_source.LocalUserDataSource
import com.example.data.data_source.RemoteUsersDataSource
import com.example.data.mediator.UserRemoteMediator
import com.example.data.models.user.ui.User
import com.example.data.repository.UsersRepository
import com.example.demo.ui.users.Contract
import com.example.demo.ui.users.UsersViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UsersViewModelTest {

    @MockK
    private val localUserDataSource: LocalUserDataSource = mockk()

    @MockK
    private val remoteUsersDataSource: RemoteUsersDataSource = mockk()

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var vm: UsersViewModel

    private var rep = mockk<UsersRepository>()

    @OptIn(ExperimentalPagingApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        val pager = Pager(
            config = PagingConfig(10),
            initialKey = 0,
            remoteMediator = UserRemoteMediator(localUserDataSource, remoteUsersDataSource),
            pagingSourceFactory = { localUserDataSource.userPagingSource() }
        ).flow

        every { rep.getUserPaging() } returns pager
        vm = UsersViewModel(rep)
    }

    @Test
    @OptIn(ExperimentalPagingApi::class)
    fun test_FetchUsers() {
        val pager = Pager(
            config = PagingConfig(10),
            initialKey = 0,
            remoteMediator = UserRemoteMediator(localUserDataSource, remoteUsersDataSource),
            pagingSourceFactory = { localUserDataSource.userPagingSource() }
        ).flow

        every { rep.getUserPaging() } returns pager
        vm.onEvent(Contract.Event.FetchUsers)
        coVerify() { vm.updateState(Contract.Effect.InitPaging, Contract.State()) }
        assertNotEquals(vm.state.value.pagingFlow, emptyFlow<PagingData<User>>())
    }

    @Test
    fun test_OnFavouriteButtonClicked() {
        val users = listOf(
            User(
                1L,
                "email1",
                "f1",
                "ln1",
                "ava1",
                true
            ),
            User(
                2L,
                "email2",
                "f2",
                "ln2",
                "ava2",
                false
            )
        )

        coEvery { rep.getFavouriteUsers() } returns users.filter { it.isFavourite }
        vm.onEvent(Contract.Event.OnFavouriteButtonClicked)
        coVerify() {
            vm.updateState(
                Contract.Effect.OnFavouriteButtonClicked,
                Contract.State(favouriteList = users.filter { it.isFavourite }, showOnlyFavourite = false)
            )
        }
        assertEquals(users.filter { it.isFavourite }, vm.state.value.favouriteList)
        assertTrue(vm.state.value.showOnlyFavourite)

        vm.onEvent(Contract.Event.OnFavouriteButtonClicked)

        coVerify() { vm.updateState(Contract.Effect.OnFavouriteButtonClicked, Contract.State()) }
        assertEquals(vm.state.value.favouriteList, emptyList<User>())
        assertFalse(vm.state.value.showOnlyFavourite)
    }

    @Test
    fun test_OnUpdateFavouriteUserClicked() {
        coEvery { rep.updateIsFavourite(true, 1) } returns mockk()
        vm.onEvent(Contract.Event.OnUpdateFavouriteUserClicked(true, 1))
        coVerify() {
            vm.updateState(
                Contract.Effect.OnUpdateFavouriteUserClicked(true, 1),
                Contract.State()
            )
        }
        coVerify { rep.updateIsFavourite(true, 1) }
    }

    @Test
    fun test(){
        assertTrue(false)
    }
}