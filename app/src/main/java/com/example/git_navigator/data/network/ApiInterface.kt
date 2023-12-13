package com.example.git_navigator.data.network

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login/oauth/access_token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_id") clientSecret: String,
        @Field("code") code: String
    )

    @GET("Authorization: Bearer {token}")
    suspend fun getUserToken(@Path("token") token: String)

}
interface GitHubService {
    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") user: String): Response<List<Repository>>

    @GET("user")
    suspend fun getUser(): UserGit
}