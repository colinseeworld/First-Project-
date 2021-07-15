package com.example.jsone3_categoryfood.models

data class CartItemsResponse(
    var id: String,
    var name: String,
    var image: String,
    var price: Float,
    var mrp: Float,
    var quantity: Int
)
