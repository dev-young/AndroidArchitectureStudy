package io.github.devy.architecture_study.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.devy.architecture_study.data.model.LikeUserEntity

@Database(
    entities = [LikeUserEntity::class],
    version = 1
)
abstract class GitHubDatabase : RoomDatabase() {

    abstract val userDao: GithubUserDao

    companion object {
        const val DATABASE_NAME = "GitHubDB"
    }
}