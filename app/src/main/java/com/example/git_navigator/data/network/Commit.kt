package com.example.git_navigator.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Commit(
    @SerialName("sha")
    val sha: String,
    @SerialName("node_id")
    val id: String,
    @SerialName("url")
    val url: String
)