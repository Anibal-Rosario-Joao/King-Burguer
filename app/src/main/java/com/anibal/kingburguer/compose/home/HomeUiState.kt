package com.anibal.kingburguer.compose.home

import com.anibal.kingburguer.data.CategoryResponse

data class HomeUiState(
    val isLoading: Boolean = false,
    val categories: List<CategoryResponse> = emptyList(),
    val error: String? = null
)
