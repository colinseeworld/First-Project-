package com.example.jsone3_categoryfood.models

data class SubCategory(
    val count: Int,
    val data: List<SubData>,
    val error: Boolean
)

data class SubData(
    //val __v: Int,
    //val _id: String,
    val catId: Int,
    //val position: Int,
    //val status: Boolean,
    //val subDescription: String,
    val subId: Int,
    //val subImage: String,
    val subName: String
)