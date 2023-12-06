package com.example.git_navigator.presentation.repository_list

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class list_entity (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val review: String,
    @SerializedName("language")
    val language: String
)
