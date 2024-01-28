package com.example.git_navigator.presentation.main_page

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.git_navigator.data.network.Commit
import com.example.git_navigator.data.network.Repository
import com.example.git_navigator.data.network.RetrofitModule
import com.example.git_navigator.data.useCases.AuthUseCase
import com.example.git_navigator.domain.GitNavigatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(repository: GitNavigatorRepository) : ViewModel() {

    private val _reposState = MutableLiveData<ReposState>()
    val reposState: MutableLiveData<ReposState> get() = _reposState

    private val _commitState = MutableLiveData<CommitsState>()
    val commitState: MutableLiveData<CommitsState> get() = _commitState

    private var repoList: List<Repository> = mutableListOf()
    private val authUseCases = AuthUseCase(repository)


    private fun getUserName(): String {
        return authUseCases.getUserName()
    }


    private fun getUserToken(): String {
        return authUseCases.getUserToken()
    }


    fun requestRepos() {
        viewModelScope.launch {
            try {
                val response = RetrofitModule.create(getUserToken()).getUserRepos(getUserName())
                val repos = response.take(4)
                Log.d("requestData", "Success: ${repos.size} repositories")
                _reposState.postValue(ReposState.Success(repos))
                repoList = repos
            } catch (e: Exception) {
                Log.d("response", "${e.message}")
            }
        }
    }

    fun requestCommits(repos: List<Repository>) {
        viewModelScope.launch {
            try {
                val counts: MutableList<Int> = mutableListOf()
                for (repo in repos) {
                    val response =
                        RetrofitModule.create(getUserToken()).getCommits(getUserName(), repo.name)
                    val count = getCommitCount(response)
                    counts.add(count)
                }
                _commitState.postValue(CommitsState.Success(counts))
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                }
                _commitState.postValue(CommitsState.Error("Error ${e.message}"))
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

    private fun getCommitCount(commits: List<Commit>): Int {
        return commits.size
    }
}