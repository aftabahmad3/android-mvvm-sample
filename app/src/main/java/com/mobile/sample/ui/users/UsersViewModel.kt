package com.mobile.sample.ui.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UsersRepository
import com.mobile.sample.utils.CoroutineContextProvider
import com.mobile.sample.utils.Failure
import com.mobile.sample.utils.LiveEvent
import com.mobile.sample.utils.Success
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(
        private val contextProvider: CoroutineContextProvider,
        private val usersRepository: UsersRepository
) : ViewModel(), UserItemActionsListener {

    private val userId = LiveEvent<Int>()
    internal val viewState: MutableLiveData<ViewState> = MutableLiveData()

    init {
        viewModelScope.launch(contextProvider.Main) {
            val result = usersRepository.getUsers()
            viewState.value = when (result) {
                is Success -> ViewState.HasItems(result.data)
                is Failure -> ViewState.Error(result.throwable.localizedMessage)
            }
        }
    }

    fun getUserWithId(userId: Int) {
        viewModelScope.launch(contextProvider.Main) {
            val result = usersRepository.getUser(userId)
            viewState.value = when (result) {
                is Success -> ViewState.HasUser(result.data)
                is Failure -> ViewState.Error(result.throwable.localizedMessage)
            }
        }
    }

    fun getUserClickedEvent() = userId

    override fun onUserClicked(userId: Int) {
        this.userId.value = userId
    }

    sealed class ViewState {
        class HasItems(val items: List<User>) : ViewState()
        class HasUser(val user: User) : ViewState()
        class Error(val errorMessage: String) : ViewState()
    }
}