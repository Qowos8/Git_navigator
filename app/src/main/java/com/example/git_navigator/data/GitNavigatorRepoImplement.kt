package com.example.git_navigator.data

import android.content.Context
import com.example.git_navigator.domain.GitNavigatorModule
import com.example.git_navigator.domain.GitNavigatorRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GitNavigatorRepoImplement @Inject constructor(val repository: GitNavigatorModule, @ApplicationContext val context: Context): GitNavigatorRepository {
    override fun saveUserName(userName: String) {
        repository.provideSharedPreferences(context).edit().putString("name", userName).apply()
    }

    override fun getUserName(): String {
        return repository.provideSharedPreferences(context).getString("name", "") ?: ""
    }

    override fun saveUserToken(token: String) {
        repository.provideSharedPreferences(context).edit().putString("password", token).apply()
    }

    override fun getUserToken(): String {
        return repository.provideSharedPreferences(context).getString("password", "") ?: ""
    }

}