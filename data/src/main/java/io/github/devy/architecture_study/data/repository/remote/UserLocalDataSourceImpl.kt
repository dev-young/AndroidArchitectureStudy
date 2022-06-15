package io.github.devy.architecture_study.data.repository.remote

import io.github.devy.architecture_study.data.db.GithubUserDao
import io.github.devy.architecture_study.data.model.LikeUserEntity
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(private val dao: GithubUserDao) :
    UserLocalDataSource {
    override suspend fun getLikeUsers(): Set<Int> {
        return dao.getLikeUsers().map { it.userId }.toSet()
    }

    override suspend fun insertLikeUser(userId: Int): Boolean {
        return dao.insertLikeUser(LikeUserEntity(userId)) > -1
    }

    override suspend fun deleteLikeUser(userId: Int): Boolean {
        return dao.deleteLikeUser(userId) > 0
    }

}