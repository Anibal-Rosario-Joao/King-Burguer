package com.anibal.kingburguer.common

import com.anibal.kingburguer.data.ListCuponResponse

// Função para converter da API para a UI
fun ListCuponResponse.toCoupon(): Coupon {
    return Coupon(
        id = this.id,
        productId = this.productId,
        code = this.coupon,
        expirationAt = this.expirationAt,
        createdAt = this.createdAt
    )
}

data class Coupon(
    val id: Int,
    val productId: Int,
    val code: String,
    val expirationAt: String,
    val createdAt: String
)