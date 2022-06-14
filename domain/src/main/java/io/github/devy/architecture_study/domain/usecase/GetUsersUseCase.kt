package io.github.devy.architecture_study.domain.usecase

import io.github.devy.architecture_study.domain.repositoty.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repo: UserRepository) {
    suspend operator fun invoke(query: String) = repo.getUsers(query)
}