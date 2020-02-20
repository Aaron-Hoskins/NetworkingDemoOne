package com.examples.coding.networkingdemoone.model.User


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class UserResponce(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Result>
) : Parcelable