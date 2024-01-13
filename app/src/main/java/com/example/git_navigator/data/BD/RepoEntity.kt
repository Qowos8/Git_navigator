package com.example.git_navigator.data.BD

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RepoDB.DB_NAME)
data class RepoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = RepoDB.Repository.COLUMN_NAME_ID)
    val id: Int,
    @ColumnInfo(name = RepoDB.Repository.COLUMN_NAME_NAME)
    val name: String,
    @ColumnInfo(name = RepoDB.Repository.COLUMN_NAME_LANGUAGE)
    val language: String,
    @ColumnInfo(name = RepoDB.Repository.COLUMN_NAME_SIZE)
    val size: String
)