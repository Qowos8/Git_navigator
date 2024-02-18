package com.example.git_navigator.presentation.authorization

import com.example.git_navigator.data.network.Repository

sealed class ReposState{
    object LoadingRepos : AuthState()
    data class SuccessRepos(val repository: List<Repository>) : AuthState()
    data class ErrorRepos(val errorMessage: String) : AuthState()
}