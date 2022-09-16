package com.github.hachimori.samplequoteapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.hachimori.samplequoteapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}