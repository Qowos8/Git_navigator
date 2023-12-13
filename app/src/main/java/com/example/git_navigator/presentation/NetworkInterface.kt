package com.example.git_navigator.presentation

import com.example.git_navigator.data.network.GitHubService
import com.example.git_navigator.presentation.repository_list.ListAdapter

interface NetworkInterface {
    suspend fun loadDataFromNetwork(userToken: String, holder: ListAdapter.ListViewHolder)
}