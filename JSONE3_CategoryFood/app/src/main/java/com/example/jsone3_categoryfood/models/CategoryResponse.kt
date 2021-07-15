package com.example.jsone3_categoryfood.models

import java.io.Serializable

data class CategoryResponse(
    val count: Int,
    val data: List<Data>,
    val error: Boolean
)

data class Data(
    //val __v: Int,
    //val _id: String,
    //val catDescription: String,
    val catId: Int,
    val catImage: String? = null,
    val catName: String? = null
    //val position: Int,
    //val slug: String,
    //val status: Boolean
):Serializable{
    companion object{
        const val KEY_FOOD = "food"
    }
}



