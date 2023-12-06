package com.example.git_navigator.data.network

import kotlinx.serialization.Serializable
import org.intellij.lang.annotations.Language
@Serializable
data class listRepository(
    val result: List<Repository>
)
@Serializable
data class Repository(
    val id: Int,
    val name: String,
    val language: String,
    val description: String
){}

