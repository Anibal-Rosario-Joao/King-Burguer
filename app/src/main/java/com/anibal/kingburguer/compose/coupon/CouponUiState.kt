package com.anibal.kingburguer.compose.coupon

import com.anibal.kingburguer.common.Coupon
import com.anibal.kingburguer.data.CouponResponse

data class CouponUiState(
    val isLoading: Boolean = false,
    val coupons: List<Coupon> = emptyList(), // Aqui usamos o modelo da UI
    val error: String? = null
)

