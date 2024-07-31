package com.example.carbooking.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.carbooking.MarsApi.retrofitService
import com.example.carbooking.R
import com.example.carbooking.authentication.Login
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.io.IOException


class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val log_out: Button = findViewById(R.id.log_out)

        textView = findViewById(R.id.textView)

        log_out.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, Login::class.java))
        }

        getMarsPhotos()
    }

    private fun getMarsPhotos() {
        lifecycleScope.launch {
            try {
                val listResult = retrofitService.getPhotos()
                textView.text = "Success. ${listResult.size} Mars photos retrieved"
            } catch (e: IOException) {

            }
        }
    }
}