package com.bishal.domain.usecase

import com.bishal.domain.model.MovieResponse
import com.bishal.domain.repository.HomeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.io.IOException

private const val RETRY_TIME_IN_MILLIS = 15_000L

fun interface HomeUseCase : suspend () -> Flow<Result<MovieResponse>>

suspend fun getUserData(
    homeRepository: HomeRepository,
): Flow<Result<MovieResponse>> = homeRepository.directApiCall()
    .map {
        Result.success(it)
    }.retryWhen { cause, _ ->
        if (cause is IOException) {
            emit(Result.failure(cause))
            delay(RETRY_TIME_IN_MILLIS)
            true
        } else {
            false
        }
    }
    .catch {
        emit(Result.failure(it)) // also catch does re-throw CancellationException
    }

