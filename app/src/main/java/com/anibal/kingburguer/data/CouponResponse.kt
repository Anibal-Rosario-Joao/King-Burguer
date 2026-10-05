package com.anibal.kingburguer.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CouponResponse(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("product_id")
    val productId: Int,
    val coupon: String,
    @SerializedName("expires_date")
    val expireDate: String,
    @SerializedName("created_date")
    val createdDate: Date
)
