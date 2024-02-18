package com.example.git_navigator.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.git_navigator.domain.useCases.AuthUseCase
import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.di.RetrofitModule
import com.example.git_navigator.data.network.UserGit
import com.example.git_navigator.domain.repository.GitNavigatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(repository: GitNavigatorRepository) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.LoadingUser)
    val authState: StateFlow<AuthState> get() = _authState

    private val _reposState = MutableStateFlow<AuthState>(AuthState.LoadingUser)
    val reposState: StateFlow<AuthState> get() = _authState

    val userNameSharedPref = MutableLiveData <String>()

    private val _repList = MutableLiveData<List<Repository>?>()
    val repList: LiveData<List<Repository>?> get() = _repList

    private val _user = MutableLiveData<UserGit?>()
    val user: LiveData<UserGit?> get() = _user
    private val authUseCases = AuthUseCase(repository)

    fun saveUserName(name: String){
        return authUseCases.saveUserName(name)
    }

    fun saveUserToken(token: String){
        return authUseCases.saveUserToken(token)
    }

    fun inputCheck(input: String): Boolean{
        _authState.value = AuthState.LoadingUser
        return authUseCases.inputCheck(input)
    }

    fun responseAuth(authToken: String): Boolean? {
        var result: Boolean? = false
        viewModelScope.launch {
            try {
                val rs = RetrofitModule.create(authToken).getUser()
                _user.value = rs
                _authState.emit(AuthState.SuccessUser(rs))
                result = true
            } catch (e: Exception) {
                if(e is CancellationException){
                    throw e
                }
                _authState.emit(AuthState.ErrorUser("Exception: ${e.message}"))
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
                _reposState.emit(ReposState.SuccessRepos(repositories))
                result = true
                _repList.value = repositories
            } catch (e: Exception) {
                if(e is CancellationException){
                    throw e
                }
                _reposState.emit(ReposState.ErrorRepos("Exception: ${e.message}"))
            }
        }
        return result
    }
}