package com.example.git_navigator.presentation.authorization

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.data.network.RetrofitModule
import com.example.git_navigator.data.network.UserGit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> get() = _authState
    private val _repList = MutableLiveData<List<Repository>?>()
    val repList: LiveData<List<Repository>?> get() = _repList

    private val _user = MutableLiveData<UserGit?>()
    val user: LiveData<UserGit?> get() = _user
    fun responseAuth(authToken: String): Boolean {
        var result: Boolean = false
        viewModelScope.launch {
            try {
                val rs = RetrofitModule.create(authToken).getUser()
                _user.value = rs
                _authState.value = AuthState.SuccessUser(rs)
                result = true
                //callback(true, rs.login)
                Log.d("user", "${authState.value}")
            } catch (e: Exception) {
                //callback(false, null)
                _authState.value = AuthState.ErrorUser
                Log.d("responseAuth", "error: ${e.message}")
            }
        }
        return result
    }


    fun requestData(name: String, authToken: String): Boolean {
        var result: Boolean = false
        viewModelScope.launch {
            try {
                val response = RetrofitModule.create(authToken).getUserRepos(name)
                val repositories = response.take(10)
                _authState.value = AuthState.SuccessRepos(repositories)
                result = true
                _repList.value = repositories
                Log.d("requestData", "Success: ${repositories.size} repositories")
            } catch (e: Exception) {
                _authState.value = AuthState.ErrorRepos
                Log.d("response", "${e.message}")
            }
        }
        return result
    }
}