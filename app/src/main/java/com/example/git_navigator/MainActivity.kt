package com.example.git_navigator

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.git_navigator.R
import com.example.git_navigator.presentation.authorization.AuthFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed ({
            val fragment = AuthFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.splash_container, fragment)
                .commit()
        }, 2000)
    }
}