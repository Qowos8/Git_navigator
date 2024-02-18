package com.example.git_navigator.domain.repository


interface GitNavigatorRepository {

    fun saveUserName(userName: String)
    fun getUserName(): String

    fun saveUserToken(token: String)
    fun getUserToken(): String

    fun inputCheck(input: String): Boolean
}