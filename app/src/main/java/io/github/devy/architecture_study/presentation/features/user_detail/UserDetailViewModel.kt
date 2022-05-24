package io.github.devy.architecture_study.presentation.features.user_detail

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    application: Application,
    private val repo: UserRepository,
) : AndroidViewModel(application) {

    private val res = application.resources

    private val _uiState = MutableLiveData<UiState>()
    val uiState = _uiState.toLiveData()

    private val _uiEvent = SingleLiveEvent<UiEvent>()
    val uiEvent = _uiEvent.toLiveData()

    private var userId = -1
    fun loadUser(id: Int) {
        viewModelScope.launch {
            userId = id
            repo.getUserFromCache(id)?.let {
                _uiState.value = UiState(it)
            } ?: kotlin.run {
                _uiEvent.value = UiEvent.Toast(res.getString(R.string.msg_fail_to_laod_user_detail))
            }
        }
    }

    fun toggleLike() {
        viewModelScope.launch {
            val user = uiState.value!!.user
            if (repo.updateLike(userId, !user.like)) {
                user.like = !user.like
                _uiState.value = UiState(user)
            }
        }
    }


    data class UiState(
        val user: User,
    )

    sealed class UiEvent {
        data class Toast(val msg: String) : UiEvent()
    }
}