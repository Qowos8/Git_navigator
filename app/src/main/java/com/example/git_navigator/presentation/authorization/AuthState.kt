package com.example.git_navigator.presentation.authorization

import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.data.network.UserGit

sealed class AuthState {
    data class SuccessUser(val user: UserGit) : AuthState()
    object ErrorUser : AuthState()

    data class SuccessRepos(val repositories: List<Repository>) : AuthState()
    object ErrorRepos : AuthState()
}