package com.example.git_navigator.domain

import android.content.Context
import android.content.SharedPreferences
import com.example.git_navigator.data.repository.GitNavigatorRepoImplement
import com.example.git_navigator.domain.repository.GitNavigatorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GitNavigatorModule {

    @Provides
    fun provideGitNavigatorRepository(repoImpl: GitNavigatorRepoImplement): GitNavigatorRepository {
        return repoImpl
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
}