package com.example.jsone3_categoryfood.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class AddressResponse(
    val count: Int,
    val data: List<Address>,
    val error: Boolean
)

@Parcelize
data class Address(
    val __v: Int,
    val _id: String,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String
) : Parcelable