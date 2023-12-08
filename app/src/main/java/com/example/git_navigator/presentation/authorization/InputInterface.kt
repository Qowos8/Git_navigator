package com.example.git_navigator.presentation.authorization

interface InputInterface {
    fun getTextInput(): String
    fun openRepos(input: String)
    fun invalidToken()
}