package com.examples.coding.networkingdemoone.model.User


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class Dob(
    @SerializedName("age")
    val age: Int,
    @SerializedName("date")
    val date: String
) : Parcelable