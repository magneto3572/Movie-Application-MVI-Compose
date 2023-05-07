package com.bishal.data.api


import com.bishal.data.impl.HomeRepositoryImpl
import com.bishal.domain.repository.HomeRepository
import com.bishal.domain.usecase.HomeUseCase
import com.bishal.domain.usecase.getUserData
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Singleton
    @Provides
    fun providesApi(remoteDataSource: RemoteDataSource): ApiService {
        return remoteDataSource.buildApi(ApiService::class.java)
    }

    @Provides
    fun provideGetHomeUseCase(
        homeRepository: HomeRepository,
    ): HomeUseCase {
        return HomeUseCase {
            getUserData(homeRepository)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {
        @Binds
        @Singleton
        fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository
    }
}
