package com.example.git_navigator.presentation

import com.example.git_navigator.data.network.GitHubService
import com.example.git_navigator.presentation.repository_list.listAdapter

interface NetworkInterface {
    suspend fun loadDataFromNetwork(service: GitHubService, userToken: String, holder: listAdapter.ListViewHolder)
}