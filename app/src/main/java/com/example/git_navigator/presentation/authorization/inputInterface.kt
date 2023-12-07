package com.example.git_navigator.presentation.authorization

interface inputInterface {
    fun getTextInput(): String
    fun openRepos(input: String)
    fun invalidToken()
}