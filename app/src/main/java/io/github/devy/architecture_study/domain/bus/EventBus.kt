package io.github.devy.architecture_study.domain.bus

import kotlinx.coroutines.flow.SharedFlow

interface EventBus<T> {
    val events: SharedFlow<T>

    suspend fun produceEvent(event: T)
}