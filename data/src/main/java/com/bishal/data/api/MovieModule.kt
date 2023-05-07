package com.bishal.data.api


import com.bishal.data.uistate.UserUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object MovieModule {

    @Provides
    fun provideUiState(): UserUiState = UserUiState()
}
