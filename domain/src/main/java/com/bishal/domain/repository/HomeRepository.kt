package com.bishal.domain.repository


import com.bishal.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
//    fun getData(): Flow<List<UserData>>
//    suspend fun refreshData()
    suspend fun directApiCall() :  Flow<MovieResponse>
}