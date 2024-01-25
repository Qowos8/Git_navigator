package com.example.git_navigator.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Commit(
    @SerialName("name")
    val name: String,
    @SerialName("id")
    val id: Int,
    @SerialName("url")
    val url: String
)