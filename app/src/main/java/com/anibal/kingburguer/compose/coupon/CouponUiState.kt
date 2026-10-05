package com.anibal.kingburguer.compose.coupon

import com.anibal.kingburguer.data.CouponResponse

data class CouponUiState(
    val isLoading: Boolean = false,
    val coupons: List<CouponResponse> = emptyList(),
    val error: String? = null
)
