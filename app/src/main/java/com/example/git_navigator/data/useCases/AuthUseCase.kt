package com.example.git_navigator.data.useCases

import com.example.git_navigator.domain.GitNavigatorRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AuthUseCase @Inject constructor(private val repository: GitNavigatorRepository) {

    fun saveUserName(name: String){
        repository.saveUserName(name)
    }
    fun getUserName(): String{
        return repository.getUserName()
    }

    fun saveUserToken(token: String){
        repository.saveUserToken(token)
    }
    fun getUserToken(): String{
        return repository.getUserToken()
    }
    fun inputCheck(input: String): Boolean{
        return repository.inputCheck(input)
    }
}