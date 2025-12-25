package com.kabi.retrofitproject.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabi.retrofitproject.data.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(NewsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                fetchNews()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = NewsState()
        )

    fun onAction(action: NewsAction) {
        when (action) {
            NewsAction.GetNews -> fetchNews()
        }
    }

    private fun fetchNews() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false
                )
            }

            try {
                val response = apiService.getLatest(apikey = "pub_d37734ca227c4e08ab3c854c174021f0")
                _state.update {
                    it.copy(
                        isLoading = false,
                        news = response.articles,
                        isError = false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }

            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

}