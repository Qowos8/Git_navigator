package com.example.git_navigator.data.network

import kotlinx.serialization.Serializable

@Serializable
class UserGit(
    val id: Int,
    val login: String,
    val name: String?,
    val email: String?,
)