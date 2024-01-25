package com.example.git_navigator.data.BD

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDao{
    @Query("SELECT * FROM ${RepoDB.Repository.TABLE_NAME}")
    suspend fun getRepo(): List<RepoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repos: List<RepoEntity>)
}