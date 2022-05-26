package io.github.devy.architecture_study.domain.bus

interface LikeEventBus : EventBus<Pair<Int, Boolean>> {

    suspend fun produceEvent(userId: Int, likeState: Boolean)

}