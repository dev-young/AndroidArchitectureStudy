package io.github.devy.architecture_study.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.devy.architecture_study.presentation.bus.LikeEventBus
import io.github.devy.architecture_study.presentation.bus.LikeEventBusImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLikeEventBus(): LikeEventBus {
        return LikeEventBusImpl()
    }

}