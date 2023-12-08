package com.example.git_navigator.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.intellij.lang.annotations.Language

@Serializable
data class Repository(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("language")
    val language: String,
    @SerialName("description")
    val description: String
)

