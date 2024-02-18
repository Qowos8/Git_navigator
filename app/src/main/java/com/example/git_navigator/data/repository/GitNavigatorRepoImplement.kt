package com.example.git_navigator.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.git_navigator.domain.repository.GitNavigatorRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GitNavigatorRepoImplement @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @ApplicationContext val context: Context
) : GitNavigatorRepository {

    override fun saveUserName(userName: String) {
        sharedPreferences.edit().putString("name", userName).apply()
    }

    override fun getUserName(): String {
        return sharedPreferences.getString("name", "") ?: ""
    }

    override fun saveUserToken(token: String) {
        sharedPreferences.edit().putString("password", token).apply()
    }

    override fun getUserToken(): String {
        return sharedPreferences.getString("password", "") ?: ""
    }

    override fun inputCheck(input: String): Boolean {
        val regex = "^[a-zA-Z0-9_]+$".toRegex()
        return regex.matches(input)
    }

}