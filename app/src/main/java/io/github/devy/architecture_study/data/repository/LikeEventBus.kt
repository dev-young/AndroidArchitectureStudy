package io.github.devy.architecture_study.data.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class LikeEventBus {
    private val _events = MutableSharedFlow<Pair<Int, Boolean>>()
    val events = _events.asSharedFlow()

    suspend fun produceEvent(userId: Int, like: Boolean) {
        _events.emit(Pair(userId, like))
    }
}