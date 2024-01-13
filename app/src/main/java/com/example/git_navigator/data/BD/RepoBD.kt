package com.example.git_navigator.data.BD
import android.provider.BaseColumns

object RepoDB {
    const val DB_NAME = "Repo.db"

    object Repository{
        const val TABLE_NAME = "Repository"
        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_LANGUAGE = "title"
        const val COLUMN_NAME_SIZE = "release"
    }
}