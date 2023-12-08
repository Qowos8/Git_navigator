package com.example.git_navigator.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserGit(
    @SerialName("id")
    val id: Int,
    @SerialName("login")
    val login: String,
    @SerialName("name")
    val name: String?,
    @SerialName("email")
    val email: String?,
)