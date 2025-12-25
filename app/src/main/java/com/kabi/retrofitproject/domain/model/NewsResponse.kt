package com.kabi.retrofitproject.domain.model

data class NewsResponse(
    val nextPage: String,
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)