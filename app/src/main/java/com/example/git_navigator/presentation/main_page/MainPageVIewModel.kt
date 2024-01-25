package com.example.git_navigator.presentation.main_page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.git_navigator.data.UseCase
import com.example.git_navigator.data.network.Commit
import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.data.network.RetrofitModule
import com.example.git_navigator.domain.GitNavigatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    //@Inject lateinit var useCase: UseCase
    private val _commits = MutableLiveData<List<Commit>>()
    val commits: LiveData<List<Commit>> get() = _commits

    private val _loadState = MutableLiveData<MainPageState>()

    private val _repos = MutableLiveData<List<Repository>>()
    val repos: LiveData<List<Repository>> get() = _repos


    fun getUserName(): String {
        return useCase.getUserName()
    }


    fun getUserToken(): String {
        return useCase.getUserToken()
    }


    fun requestRepos(name: String, authToken: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitModule.create(authToken).getUserRepos(name)
                val repos = response.take(4)
                Log.d("requestData", "Success: ${repos.size} repositories")
                _repos.value = repos
            } catch (e: Exception) {
                Log.d("response", "${e.message}")
            }
        }
    }


    fun requestCommits(name: String, authToken: String, repoName: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitModule.create(authToken).getCommits(name, repoName)
                Log.d("requestCommits", "Success: ${response.size} repositories")
                _commits.value = response
            } catch (e: Exception) {
                Log.d("response", "${e.message}")
            }
        }
    }


    fun getLanguages(languages: MutableList<String>): MutableMap<String, Int> {
        val dictionary: MutableMap<String, Int> = mutableMapOf()
        for (lang in languages) {
            if (lang !in dictionary) {
                dictionary[lang] = 1
            } else {
                dictionary[lang] = dictionary[lang]!! + 1
            }
        }
        return dictionary
    }


    fun getCommitCount(commits: List<Commit>): MutableMap<String, Int> {
        val dictionary: MutableMap<String, Int> = mutableMapOf()
        for (commit in commits) {
            if (commit.name !in dictionary) {
                dictionary[commit.name] = 1
            } else {
                dictionary[commit.name] = dictionary[commit.name]!! + 1
            }
        }
        return dictionary
    }
}