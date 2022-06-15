package io.github.devy.architecture_study.data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.devy.architecture_study.data.api.GitHubApi
import io.github.devy.architecture_study.data.api.ServiceGenerator
import io.github.devy.architecture_study.data.db.GitHubDatabase
import io.github.devy.architecture_study.data.db.GithubUserDao
import io.github.devy.architecture_study.data.repository.UserRepositoryImpl
import io.github.devy.architecture_study.data.repository.local.UserRemoteDataSource
import io.github.devy.architecture_study.data.repository.local.UserRemoteDataSourceImpl
import io.github.devy.architecture_study.data.repository.remote.UserLocalDataSource
import io.github.devy.architecture_study.data.repository.remote.UserLocalDataSourceImpl
import io.github.devy.architecture_study.domain.repositoty.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideGitHubDatabase(app: Application): GitHubDatabase {
        return Room.databaseBuilder(
            app,
            GitHubDatabase::class.java,
            GitHubDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideGitHubApi(): GitHubApi {
        return ServiceGenerator.createService(GitHubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGitHubUserDao(db: GitHubDatabase): GithubUserDao {
        return db.userDao
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(api: GitHubApi): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserLocalDataSource(db: GitHubDatabase): UserLocalDataSource {
        return UserLocalDataSourceImpl(db.userDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        remote: UserRemoteDataSource,
        local: UserLocalDataSource
    ): UserRepository {
        return UserRepositoryImpl(local, remote)
    }

}