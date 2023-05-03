package com.euler.companion.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.euler.companion.base.BaseViewModel
import com.euler.companion.event.UsersEvent
import com.euler.companion.intent.UsersIntent
import com.euler.data.uistate.UserUiState
import com.euler.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

private const val HTTP_PREFIX = "http"
private const val HTTPS_PREFIX = "https"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    userUiState: UserUiState,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<UserUiState, UserUiState.PartialState, UsersEvent, UsersIntent>
    (savedStateHandle, userUiState) {

    init {
        acceptIntent(UsersIntent.GetUserData)
    }

    override fun mapIntents(intent: UsersIntent): Flow<UserUiState.PartialState> = when (intent) {
        is UsersIntent.GetUserData -> getData()
        is UsersIntent.ItemClicked -> itemClicked(intent.uri)
    }



    override fun reduceUiState(
        previousState: UserUiState,
        partialState: UserUiState.PartialState
    ): UserUiState = when (partialState) {
        is UserUiState.PartialState.Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )
        is UserUiState.PartialState.Fetched -> previousState.copy(
            isLoading = false,
            data = partialState.response,
            isError = false,
        )
        is UserUiState.PartialState.Error -> previousState.copy(
            isLoading = false,
            isError = true,
        )
    }


    private fun getData(): Flow<UserUiState.PartialState> = flow {
        homeUseCase().onStart {
                emit(UserUiState.PartialState.Loading)
            }.collect { result ->
                result
                    .onSuccess { res ->
                        emit(UserUiState.PartialState.Fetched(res))
                    }.onFailure {
                        emit(UserUiState.PartialState.Error(it))
                    }
            }
    }

    private fun itemClicked(uri: String): Flow<UserUiState.PartialState> {
        if (uri.startsWith(HTTP_PREFIX) || uri.startsWith(HTTPS_PREFIX)) {
            publishEvent(UsersEvent.OpenWebBrowserWithDetails(uri))
        }
        return emptyFlow()
    }
}