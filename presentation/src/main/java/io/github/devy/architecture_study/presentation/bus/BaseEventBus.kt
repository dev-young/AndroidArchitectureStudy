package io.github.devy.architecture_study.presentation.bus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

open class BaseEventBus<T> : EventBus<T> {
    private val _events = MutableSharedFlow<T>()
    override val events = _events.asSharedFlow()

    override suspend fun produceEvent(event: T) {
        _events.emit(event)
    }
}