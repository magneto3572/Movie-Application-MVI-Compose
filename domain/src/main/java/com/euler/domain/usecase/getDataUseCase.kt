package com.euler.domain.usecase

import android.util.Log
import com.euler.domain.model.MovieResponse
import com.euler.domain.repository.HomeRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.io.IOException

private const val RETRY_TIME_IN_MILLIS = 15_000L

fun interface HomeUseCase : suspend () -> Flow<Result<MovieResponse>>

suspend fun getUserData(
    homeRepository : HomeRepository,
): Flow<Result<MovieResponse>> = homeRepository.directApiCall()
    .map {
        Log.d("LogTagMovie1", it.toString())
        Result.success(it)
    }.retryWhen { cause, _ ->
        Log.d("LogTagMovie2", cause.toString())
        if (cause is IOException) {
            emit(Result.failure(cause))
            delay(RETRY_TIME_IN_MILLIS)
            true
        } else {
            false
        }
    }
    .catch {
        Log.d("LogTagMovieError3", it.toString())
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

