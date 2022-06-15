package io.github.devy.architecture_study.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.devy.architecture_study.data.model.LikeUserEntity

@Dao
interface GithubUserDao {

    @Query("SELECT * FROM LikeUserEntity")
    suspend fun getLikeUsers(): List<LikeUserEntity>

    @Query("DELETE FROM LikeUserEntity WHERE userId = :id")
    suspend fun deleteLikeUser(id: Int): Int

    @Insert
    suspend fun insertLikeUser(entity: LikeUserEntity): Long
}