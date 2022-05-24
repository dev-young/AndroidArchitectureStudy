package io.github.devy.architecture_study.data.api

import io.github.devy.architecture_study.data.model.UserListRes
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/users")
    suspend fun getUserListRes(@Query("q") query: String): UserListRes
}