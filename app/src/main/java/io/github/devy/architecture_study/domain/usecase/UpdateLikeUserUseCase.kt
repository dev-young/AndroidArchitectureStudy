package io.github.devy.architecture_study.domain.usecase

import io.github.devy.architecture_study.domain.repositoty.UserRepository
import javax.inject.Inject

class UpdateLikeUserUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(userId: Int, like: Boolean) = repo.updateLike(userId, like)
}