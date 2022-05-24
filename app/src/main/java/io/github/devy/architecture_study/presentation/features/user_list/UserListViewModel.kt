package io.github.devy.architecture_study.presentation.features.user_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.devy.architecture_study.R
import io.github.devy.architecture_study.domain.model.User
import io.github.devy.architecture_study.domain.repositoty.UserRepository
import io.github.devy.architecture_study.presentation.SingleLiveEvent
import io.github.devy.architecture_study.toLiveData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    application: Application,
    private val repo: UserRepository,
) : AndroidViewModel(application) {
    private val res = application.resources
    private val _uiState = MutableLiveData<UiState>()
    val uiState = _uiState.toLiveData()

    private val _uiEvent = SingleLiveEvent<UiEvent>()
    val uiEvent = _uiEvent.toLiveData()

    init {
        loadUserList()

        viewModelScope.launch {
            repo.getLikeChangeEvent().collectLatest { (id, _) ->
                _uiEvent.value = UiEvent.LikeStateChanged(id)
            }
        }
    }

    fun loadUserList(query: String = "devy") {
        viewModelScope.launch {
            _uiState.value = UiState(loading = true)
            kotlin.runCatching { repo.getUsers(query) }.onSuccess {
                _uiState.value = UiState(header = query, userList = it)
            }.onFailure {
                _uiState.value = uiState.value!!.copy(loading = false)
                _uiEvent.value = UiEvent.Toast(res.getString(R.string.msg_fail_to_load))
            }
        }
    }

    fun toggleLike(user: User) {
        viewModelScope.launch {
            if (repo.updateLike(user.id, !user.like)) {
                user.like = !user.like
                _uiEvent.value = UiEvent.LikeStateChanged(user.id)
            }
        }
    }

    fun toggleOnlyLike() {
        val onlyLike = !uiState.value!!.onlyLike
        _uiState.value = uiState.value!!.copy(
            loading = false,
            onlyLike = onlyLike,
            userList = repo.getUsersFromCache(onlyLike))
    }


    data class UiState(
        val header: String = "",
        val userList: List<User> = emptyList(),
        val loading: Boolean = false,
        val onlyLike: Boolean = false,
    )

    sealed class UiEvent {
        data class LikeStateChanged(val userId: Int) : UiEvent()
        data class Toast(val msg: String) : UiEvent()
    }
}