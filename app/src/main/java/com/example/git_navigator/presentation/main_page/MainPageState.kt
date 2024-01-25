package com.example.git_navigator.presentation.main_page

import com.example.git_navigator.data.network.Repository

open class MainPageState {
    object Loading : MainPageState()
    data class Success(val announce: Repository) : MainPageState()
    data class Error(val errorMessage: String) : MainPageState()
}