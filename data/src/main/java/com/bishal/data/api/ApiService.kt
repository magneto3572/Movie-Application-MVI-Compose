package com.bishal.data.api

import com.bishal.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("movie/popular")
    suspend fun getUserData(@Query("page") page: Int): MovieResponse
}
