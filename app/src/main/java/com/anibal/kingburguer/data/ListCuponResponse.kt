package com.anibal.kingburguer.data

import com.google.gson.annotations.SerializedName

data class CouponBaseResponse(
    val total: Int,
    val limit: Int,
    val data: List<ListCuponResponse>
)

data class ListCuponResponse (
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    val coupon: String,
    @SerializedName("expiration_at") // Corrigido para bater com o JSON
    val expirationAt: String,
    @SerializedName("created_at") // Corrigido para bater com o JSON
    val createdAt: String
) {
}