package com.kabi.retrofitproject.presentation

import com.kabi.retrofitproject.domain.model.Article

data class NewsState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val news: List<Article> = emptyList()
)