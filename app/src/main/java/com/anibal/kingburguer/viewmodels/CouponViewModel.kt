package com.anibal.kingburguer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anibal.kingburguer.api.KingBurguerService
import com.anibal.kingburguer.compose.coupon.CouponUiState
import com.anibal.kingburguer.data.ApiResult
import com.anibal.kingburguer.data.KingBurguerLocalStorage
import com.anibal.kingburguer.data.KingBurguerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CouponViewModel(
    private val repository: KingBurguerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CouponUiState())
    val uiState: StateFlow<CouponUiState> = _uiState.asStateFlow()

    init {
        fetchCoupons()
    }

    fun fetchCoupons(page: Int = 0, expired: Boolean? = null) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = repository.fetchCoupons(page, expired)) {
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                is ApiResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, coupons = result.data) }
                }
            }
        }
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]!!.applicationContext
                val service = KingBurguerService.create()
                val localStorage = KingBurguerLocalStorage(application)
                val repository = KingBurguerRepository(service, localStorage)
                CouponViewModel(repository)
            }
        }
    }
}