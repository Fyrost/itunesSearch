package com.example.myapplication.ui


import androidx.appcompat.app.AppCompatActivity

import android.content.Intent

import android.os.Bundle
import android.os.Handler

import com.example.myapplication.R


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent =  Intent(this, MainActivity::class.java)
            startActivity(intent)
        },3000)
    }
}
