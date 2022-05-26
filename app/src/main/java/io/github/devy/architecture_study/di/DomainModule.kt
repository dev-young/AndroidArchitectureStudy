package io.github.devy.architecture_study.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.devy.architecture_study.domain.bus.LikeEventBus
import io.github.devy.architecture_study.domain.bus.LikeEventBusImpl
import io.github.devy.architecture_study.domain.repositoty.UserRepository
import io.github.devy.architecture_study.domain.usecase.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideLikeEventBus(): LikeEventBus {
        return LikeEventBusImpl()
    }

    @Provides
    @Singleton
    fun provideGetCurrentUsersUseCase(repo: UserRepository): GetCurrentUsersUseCase {
        return GetCurrentUsersUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetUsersUseCase(repo: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(repo: UserRepository): GetUserUseCase {
        return GetUserUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideUpdateLikeUserUseCase(
        repo: UserRepository,
        bus: LikeEventBus
    ): UpdateLikeUserUseCase {
        return UpdateLikeUserUseCase(repo, bus)
    }

    @Provides
    @Singleton
    fun provideObserveLikeUserChangedUseCase(bus: LikeEventBus): ObserveLikeUserChangedUseCase {
        return ObserveLikeUserChangedUseCase(bus)
    }

}