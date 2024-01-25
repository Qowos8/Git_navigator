package com.example.git_navigator.data

import com.example.git_navigator.domain.GitNavigatorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class UseCase @Inject constructor(private val repository: GitNavigatorRepository) {

    fun saveUserName(name: String){
        return repository.saveUserName(name)
    }
    fun getUserName(): String{
        return repository.getUserName()
    }

    fun saveUserToken(token: String){
        return repository.saveUserToken(token)
    }
    fun getUserToken(): String{
        return repository.getUserToken()
    }
}