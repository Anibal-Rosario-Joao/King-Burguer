package com.anibal.kingburguer.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anibal.kingburguer.api.KingBurguerService
import com.anibal.kingburguer.compose.home.HomeUiState
import com.anibal.kingburguer.data.ApiResult
import com.anibal.kingburguer.data.KingBurguerLocalStorage
import com.anibal.kingburguer.data.KingBurguerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: KingBurguerRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        start()
    }
    fun start() {
        _uiState.update {it.copy(isLoading = true)}
        viewModelScope.launch {
            val response = repository.fetchFeed()
            when (response) {
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = response.message) }
                }

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            categories = response.data.categories
                        )
                    }
                }
            }
        }
    }

    companion object{
        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]!!.applicationContext
                val service = KingBurguerService.create()
                val localStorage = KingBurguerLocalStorage(application)
                val repository = KingBurguerRepository(service, localStorage)
                HomeViewModel(repository)
            }
        }
    }
}