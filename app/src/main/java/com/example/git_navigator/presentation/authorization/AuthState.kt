package com.example.git_navigator.presentation.authorization

import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.data.network.UserGit

sealed class AuthState {
    object LoadingUser : AuthState()
    data class SuccessUser(val user: UserGit) : AuthState()
    data class ErrorUser(val errorMessage: String) : AuthState()

    object LoadingRepos : AuthState()
    data class SuccessRepos(val repository: List<Repository>) : AuthState()
    data class ErrorRepos(val errorMessage: String) : AuthState()
}