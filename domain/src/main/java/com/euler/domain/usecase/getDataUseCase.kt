package com.euler.domain.usecase

import com.euler.domain.model.MovieResponse
import com.euler.domain.repository.HomeRepository
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

//suspend fun getUserData(
//    homeRepository: HomeRepository,
//): Result<Flow<MovieResponse>> = resultOf {
//    homeRepository.directApiCall()
//}
//
//inline fun <R> resultOf(block: () -> R): Result<R> {
//    return try {
//        Result.success(block())
//    } catch (t: TimeoutCancellationException) {
//        Result.failure(t)
//    } catch (c: CancellationException) {
//        throw c
//    } catch (e: Exception) {
//        Result.failure(e)
//    }
//}

