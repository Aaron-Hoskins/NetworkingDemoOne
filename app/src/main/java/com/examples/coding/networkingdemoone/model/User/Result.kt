package com.examples.coding.networkingdemoone.model.User


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Result(
    @SerializedName("cell")
    val cell: String,
    @SerializedName("dob")
    val dob: Dob,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Id,
    @SerializedName("location")
    val location: Location,
    @SerializedName("login")
    val login: Login,
    @SerializedName("name")
    val name: Name,
    @SerializedName("nat")
    val nat: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("picture")
    val picture: Picture,
    @SerializedName("registered")
    val registered: Registered
) : Parcelable