package com.example.git_navigator.data.BD

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDao{
    @Query("SELECT * FROM ${RepoDB.Repository.TABLE_NAME}")
    fun insertRepo(): List<RepoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun getRepo(repos: List<RepoEntity>)
}