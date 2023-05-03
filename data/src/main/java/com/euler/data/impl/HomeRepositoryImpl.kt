package com.euler.data.impl

import com.euler.data.api.ApiService
import com.euler.domain.model.MovieResponse
import com.euler.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private var apiService: ApiService,
) : HomeRepository {

    override suspend fun directApiCall(): Flow<MovieResponse> = flow {
        emit(apiService.getUserData(1))
    }
}

