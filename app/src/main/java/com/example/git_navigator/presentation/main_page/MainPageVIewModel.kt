package com.example.git_navigator.presentation.main_page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.data.network.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(): ViewModel() {
    private val _commits = MutableLiveData<Int>()
    val commits: LiveData<Int> = _commits
    suspend fun requestData(name: String, authToken: String): List<Repository> {
        return viewModelScope.async {
            try {
                val response = RetrofitModule.create(authToken).getUserRepos(name)
                val repos = response.take(4)
                Log.d("requestData", "Success: ${repos.size} repositories")
                repos
            } catch (e: Exception) {
                Log.d("response", "${e.message}")
                emptyList()
            }
        }.await()
    }
}