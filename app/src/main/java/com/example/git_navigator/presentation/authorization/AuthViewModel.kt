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

class AuthViewModel(private val service: GitHubService) : ViewModel(), ApiInterface, inputInterface, Authorization_interface {
    private val _userToken = MutableLiveData<String>()
    val repList = MutableLiveData<List<Repository>?>()
    val reposList = MutableLiveData<Repository>()
    val userToken: LiveData<String> = _userToken
    val user = MutableLiveData<UserGit?>()
    val userName = MutableLiveData<String>()

    fun responseAuth(userToken: String) {
        var rs: UserGit? = null
        viewModelScope.launch {
            try {
                rs = service.getUser("Bearer $userToken")
                user.value = rs
                Log.d("user", "${user.value}")
            } catch (e: Exception) {
                Log.d("Auth", "Fail")
            }

        }
    }

    fun response(userToken: String, name: String) {
        viewModelScope.launch {
            try {
                val response = service.getUserRepos("Bearer $userToken", name)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val repositories = body.take(10)
                        repList.value = repositories
                    }
                } else {
                    Log.d("shit", "response failed")
                }
            } catch (e: Exception) {
                Log.d("response", "${e.message}")
            }
        }
    }


    override suspend fun getAccessToken(clientId: String, clientSecret: String, code: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserToken(token: String) {
        //RetrofitBuilder.create()
        //val response = ApiInterface.get
    }

    override fun check() {
        TODO("Not yet implemented")
    }

    override fun success() {
        TODO("Not yet implemented")
    }

    override fun getTextInput(): String {
        TODO("Not yet implemented")
    }

    override fun updateInput(input: String) {
        _userToken.value = input
    }

}