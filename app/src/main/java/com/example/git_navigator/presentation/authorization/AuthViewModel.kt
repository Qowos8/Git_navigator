package com.example.git_navigator.presentation.authorization

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.git_navigator.data.network.ApiInterface
import com.example.git_navigator.data.network.GitHubService
import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.data.network.UserGit
import kotlinx.coroutines.launch

class AuthViewModel(private val service: GitHubService) : ViewModel() {
    val repList = MutableLiveData<List<Repository>?>()
    val user = MutableLiveData<UserGit?>()
    fun responseAuth(userToken: String) {
        var rs: UserGit? = null
        viewModelScope.launch {
            try {
                rs = service.getUser()
                user.value = rs
                Log.d("user", "${user.value}")
            } catch (e: Exception) {
                Log.d("Auth", "Fail")
            }

        }
    }
    fun response(name: String): Boolean {
        var isSuccess = false
        viewModelScope.launch {
            try {
                val response = service.getUserRepos(name)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val repositories = body.take(10)
                        repList.value = repositories
                        isSuccess = true
                    }
                } else {
                    Log.d("fun response", "response failed")
                }
            } catch (e: Exception) {
                Log.d("response", "${e.message}")
            }
        }
        return isSuccess
    }
}