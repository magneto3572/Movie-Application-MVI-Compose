package com.euler.domain.mapper

import com.euler.domain.model.MovieResponse


fun MovieResponse.toUserDataMapping() = MovieResponse(
    page  = page,
    results = results,
    total_pages = total_pages,
    total_results = total_results
)


