package com.example.git_navigator.presentation.authorization

interface InputInterf {
    fun getTextInput(): String
    fun openRepos(input: String, login: String)
    fun onInvalidToken()
}