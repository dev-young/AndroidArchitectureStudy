package io.github.devy.architecture_study.domain.usecase

import io.github.devy.architecture_study.domain.model.User
import io.github.devy.architecture_study.domain.repositoty.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repo: UserRepository) {
    suspend operator fun invoke(userId: Int, fromCache: Boolean = true): User? {
        return if (fromCache) {
            repo.getUserFromCache(userId)
        } else {
            // TODO: 원격으로부터 요청
            null
        }
    }
}