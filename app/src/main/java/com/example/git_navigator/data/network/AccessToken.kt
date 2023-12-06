package com.example.git_navigator.data.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class AccessToken(
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("access_token")
    val accessToken: String,
    val scope: String
) {
}