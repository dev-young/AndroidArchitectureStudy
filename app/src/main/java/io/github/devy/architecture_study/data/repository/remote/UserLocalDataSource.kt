package io.github.devy.architecture_study.data.repository.remote

interface UserLocalDataSource {
    suspend fun getLikeUsers(): Set<Int>
    suspend fun insertLikeUser(userId: Int): Boolean
    suspend fun deleteLikeUser(userId: Int): Boolean
}