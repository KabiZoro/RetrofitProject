package com.kabi.retrofitproject.presentation

sealed interface NewsAction {
    data object GetNews: NewsAction
}