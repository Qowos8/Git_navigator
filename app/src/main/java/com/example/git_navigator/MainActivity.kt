package com.example.git_navigator

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.git_navigator.R
import com.example.git_navigator.presentation.authorization.AuthFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed ({
            val fragment = AuthFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container1, fragment)
                .commit()
        }, 2000)
    }
}