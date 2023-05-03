package com.euler.domain.repository


import com.euler.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
//    fun getData(): Flow<List<UserData>>
//    suspend fun refreshData()
    suspend fun directApiCall() :  Flow<MovieResponse>
}