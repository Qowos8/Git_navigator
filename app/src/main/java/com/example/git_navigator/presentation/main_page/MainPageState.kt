package com.example.git_navigator.presentation.main_page

import com.example.git_navigator.data.network.Repository

sealed class ReposState {
    object Loading : ReposState()
    data class Success(val repository: List<Repository>) : ReposState()
    data class Error(val errorMessage: String) : ReposState()
}

sealed class CommitsState {
    object Loading : CommitsState()
    data class Success(val commitsState: List<Int>) : CommitsState()
    data class Error(val errorMessage: String) : CommitsState()
}