package io.github.devy.architecture_study.data.repository.local

import io.github.devy.architecture_study.data.model.UserDto

interface UserRemoteDataSource {
    suspend fun getUsers(query: String): List<UserDto>
}