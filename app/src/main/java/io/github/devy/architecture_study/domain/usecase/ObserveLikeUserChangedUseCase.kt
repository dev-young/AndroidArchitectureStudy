package io.github.devy.architecture_study.domain.usecase

import io.github.devy.architecture_study.domain.bus.LikeEventBus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveLikeUserChangedUseCase @Inject constructor(
    private val bus: LikeEventBus
) {
    operator fun invoke(): Flow<Pair<Int, Boolean>> = bus.events
}
