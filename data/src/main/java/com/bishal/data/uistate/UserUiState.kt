package com.bishal.data.uistate

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.bishal.domain.model.MovieResponse
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class UserUiState(
    val isLoading: Boolean = false,
    val data: MovieResponse? = null,
    val isError: Boolean = false,
) : Parcelable {

    sealed class PartialState {
        object Loading : PartialState() // for simplicity: initial loading & refreshing

        data class Fetched(val response: MovieResponse) : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}

