package com.anibal.kingburguer.compose.product

import com.anibal.kingburguer.data.ProductDetailResponse

data class ProductUiState(
    val isLoading: Boolean = false,
    val productDetail: ProductDetailResponse? = null,
    val error: String? = null
)
