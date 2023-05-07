package com.bishal.data.impl

import com.bishal.data.api.ApiService
import com.bishal.domain.model.MovieResponse
import com.bishal.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private var apiService: ApiService,
) : HomeRepository {

    override suspend fun directApiCall(): Flow<MovieResponse> = flow {
        emit(apiService.getUserData(PageHandler.page))
    }
}

