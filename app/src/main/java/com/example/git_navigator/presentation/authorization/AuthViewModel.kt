package com.example.git_navigator.presentation.authorization

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.git_navigator.data.network.GitHubService
import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.data.network.RetrofitBuilder
import com.example.git_navigator.data.network.UserGit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val _repList = MutableLiveData<List<Repository>?>()
    val repList: LiveData<List<Repository>?> get () = _repList
    private val _user = MutableLiveData<UserGit?>()
    val user: LiveData<UserGit?> get() = _user
    fun responseAuth(authToken: String) {
        var rs: UserGit? = null
        viewModelScope.launch {
            try {
                rs = RetrofitBuilder.create(authToken).getUser()
                _user.value = rs
                Log.d("user", "${user.value}")
            } catch (e: Exception) {
                Log.d("Auth", "Fail")
            }

        }
    }
    fun requestData(name: String, authToken: String): Boolean {
        var isSuccess = false
        viewModelScope.launch {
            try {
                val response = RetrofitBuilder.create(authToken).getUserRepos(name)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val repositories = body.take(10)
                        _repList.value = repositories
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