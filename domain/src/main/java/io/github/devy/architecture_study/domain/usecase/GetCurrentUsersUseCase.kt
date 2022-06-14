package io.github.devy.architecture_study.domain.usecase

import io.github.devy.architecture_study.domain.repositoty.UserRepository
import javax.inject.Inject

class GetCurrentUsersUseCase @Inject constructor(private val repo: UserRepository) {
    operator fun invoke(onlyLike: Boolean) = repo.getUsersFromCache(onlyLike)
}