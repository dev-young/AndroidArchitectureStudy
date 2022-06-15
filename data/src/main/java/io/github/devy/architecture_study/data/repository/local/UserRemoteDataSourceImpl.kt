package io.github.devy.architecture_study.data.repository.local

import io.github.devy.architecture_study.data.api.GitHubApi
import io.github.devy.architecture_study.data.model.UserDto
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val api: GitHubApi) :
    UserRemoteDataSource {
    override suspend fun getUsers(query: String): List<UserDto> {
        val res = api.getUserListRes(query)
        return res.list
    }
}