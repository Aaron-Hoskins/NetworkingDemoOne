package com.examples.coding.networkingdemoone.model.User


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class Id(
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String
) : Parcelable