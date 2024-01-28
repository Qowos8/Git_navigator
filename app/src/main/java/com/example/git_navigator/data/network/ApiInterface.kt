package com.example.git_navigator.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") user: String): List<Repository>

    @GET("user")
    suspend fun getUser(): UserGit

    @GET("repos/{username}/{repo}/commits")
    suspend fun getCommits(@Path("username") user: String, @Path("repo") repoName: String): List<Commit>
}