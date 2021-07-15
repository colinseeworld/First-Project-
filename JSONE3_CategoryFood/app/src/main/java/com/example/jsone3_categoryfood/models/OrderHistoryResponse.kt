package com.example.jsone3_categoryfood.models

import android.os.Parcelable

data class OrderHistoryResponse(
    val count: Int,
    val data: List<OrderData>,
    val error: Boolean
)

data class OrderData(
    val __v: Int,
    val _id: String,
    val date: String,
    val orderSummary: OrderSummary?,
    val payment: Payment?,
    val products: List<ProductInfo>?,
    val userId: String
)


data class OrderSummary(
    val _id: String,
    val deliveryCharges: Double,
    val discount: Double,
    val orderAmount: Double,
    val ourPrice: Double,
    val totalAmount: Double
)

data class Payment(
    val _id: String,
    val paymentMode: String,
    val paymentStatus: String
)

data class ProductInfo(
    val _id: String="",
    val mrp: Double=0.0,
    val price: Double=0.0,
    val quantity: Double=0.0,
    val image: String="",
)
