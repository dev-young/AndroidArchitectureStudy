package io.github.devy.architecture_study.domain.repositoty

import io.github.devy.architecture_study.domain.model.User

interface UserRepository {

    suspend fun getUsers(query: String): List<User>

    suspend fun updateLike(userId: Int, like: Boolean): Boolean

    /**
     * 캐싱된 데이터에서 유저 정보 획득
     * */
    fun getUserFromCache(userId: Int): User?

    fun getUsersFromCache(onlyLike: Boolean): List<User>
}