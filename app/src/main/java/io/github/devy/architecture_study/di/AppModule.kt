package io.github.devy.architecture_study.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.devy.architecture_study.data.api.GitHubApi
import io.github.devy.architecture_study.data.api.ServiceGenerator
import io.github.devy.architecture_study.data.db.GitHubDatabase
import io.github.devy.architecture_study.data.repository.UserRepositoryImpl
import io.github.devy.architecture_study.domain.repositoty.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideUserRepository(db: GitHubDatabase, api: GitHubApi): UserRepository {
        return UserRepositoryImpl(api, db.userDao)
    }

}