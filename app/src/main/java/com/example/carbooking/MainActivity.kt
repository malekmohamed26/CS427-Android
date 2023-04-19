package com.example.carbooking

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.net.URL



class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        lifecycleScope.launch {
            val listResult = MarsApi.retrofitService.getPhotos()
            lifecycleScope.launchWhenCreated {
                try {
                    val listResult = MarsApi.retrofitService.getPhotos()
                    textView = findViewById(R.id.textView)
                    textView.setText("Success. ${listResult.size} Mars photos retrieved")
                } catch (e: IOException) {

                }
            }

        }
    }

    private fun getMarsPhotos() {
        lifecycleScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getPhotos()
                textView = findViewById(R.id.textView)
                textView.setText("Success. ${listResult.size} Mars photos retrieved")
            } catch (e: IOException) {

            }
        }
    }
}