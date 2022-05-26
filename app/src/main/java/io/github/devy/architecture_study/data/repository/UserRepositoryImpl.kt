package io.github.devy.architecture_study.data.repository

import io.github.devy.architecture_study.data.api.GitHubApi
import io.github.devy.architecture_study.data.db.GithubUserDao
import io.github.devy.architecture_study.data.model.LikeUserEntity
import io.github.devy.architecture_study.data.model.toUser
import io.github.devy.architecture_study.domain.model.User
import io.github.devy.architecture_study.domain.repositoty.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val api: GitHubApi,
    private val userDao: GithubUserDao,
) : UserRepository {

    private val userCache = linkedMapOf<Int, User>()

    override suspend fun getUsers(query: String): List<User> = withContext(Dispatchers.IO) {
        val likeUserSet = userDao.getLikeUsers().map { it.userId }.toSet()
        api.getUserListRes(query).let { it.list.map { it.toUser(likeUserSet.contains(it.id)) } }
            .apply {
                userCache.clear()
                forEach { userCache[it.id] = it }
            }
    }

    override suspend fun updateLike(userId: Int, like: Boolean): Boolean {
        return if (like) userDao.insertLikeUser(LikeUserEntity(userId)) > -1
        else userDao.deleteLikeUser(userId) > 0
    }

    override fun getUserFromCache(userId: Int) = userCache[userId]

    override fun getUsersFromCache(onlyLike: Boolean): List<User> {
        return if (onlyLike) userCache.values.filter { it.like }
        else userCache.values.toList()
    }

}