package com.example.git_navigator.data.BD

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RepoDB.DB_NAME)
data class RepoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = BaseColumns._ID)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "title")
    val language: String,
    @ColumnInfo(name = "release")
    val size: String
)