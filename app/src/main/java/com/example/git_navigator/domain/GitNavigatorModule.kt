package com.example.git_navigator.domain

import android.content.Context
import android.content.SharedPreferences
import com.example.git_navigator.data.GitNavigatorRepoImplement
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

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