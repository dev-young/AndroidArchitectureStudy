package io.github.devy.architecture_study.data.repository

import io.github.devy.architecture_study.data.model.toUser
import io.github.devy.architecture_study.data.repository.local.UserRemoteDataSource
import io.github.devy.architecture_study.data.repository.remote.UserLocalDataSource
import io.github.devy.architecture_study.domain.model.User
import io.github.devy.architecture_study.domain.repositoty.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    private val userCache = linkedMapOf<Int, User>()

    override suspend fun getUsers(query: String): List<User> = withContext(Dispatchers.IO) {
        val likeUserSet = localDataSource.getLikeUsers()
        remoteDataSource.getUsers(query).map { it.toUser(likeUserSet.contains(it.id)) }
            .apply {
                userCache.clear()
                forEach { userCache[it.id] = it }
            }
    }

    override suspend fun updateLike(userId: Int, like: Boolean): Boolean {
        return if (like) localDataSource.insertLikeUser(userId)
        else localDataSource.deleteLikeUser(userId)
    }

    override fun getUserFromCache(userId: Int) = userCache[userId]

    override fun getUsersFromCache(onlyLike: Boolean): List<User> {
        return if (onlyLike) userCache.values.filter { it.like }
        else userCache.values.toList()
    }

}