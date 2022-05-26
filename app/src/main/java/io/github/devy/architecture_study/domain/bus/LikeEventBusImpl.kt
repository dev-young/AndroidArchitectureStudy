package io.github.devy.architecture_study.domain.bus

class LikeEventBusImpl : BaseEventBus<Pair<Int, Boolean>>(), LikeEventBus {

    override suspend fun produceEvent(userId: Int, likeState: Boolean) {
        super.produceEvent(Pair(userId, likeState))
    }
}
