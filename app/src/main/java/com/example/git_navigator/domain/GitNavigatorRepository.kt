package com.example.git_navigator.domain


interface GitNavigatorRepository {

    fun saveUserName(userName: String)
    fun getUserName(): String

    fun saveUserToken(token: String)
    fun getUserToken(): String
}