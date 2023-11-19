package com.example.git_navigator.domain.entity

data class repositori_detail(
    val id: Int,
    val name: String,
    val link: String,
    val license: String,
    val stars: Int,
    val forks: Int,
    val watchers: Int,
    val readme: String
) {
}