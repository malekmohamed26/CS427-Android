package com.example.carbooking

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import java.io.IOException
import java.net.URL



private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .baseUrl(BASE_URL)
    .build()

interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}

val retrofitService: MarsApiService by lazy {
    retrofit.create(MarsApiService::class.java)
}

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val log_out: Button = findViewById(R.id.log_out)

        log_out.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, Login::class.java))
        }
    }

//    private fun getMarsPhotos() {
//        lifecycleScope.launch {
//            try {
//                val listResult = retrofitService.getPhotos()
//                textView = findViewById(R.id.textView)
//                textView.setText("Success. ${listResult.size} Mars photos retrieved")
//            } catch (e: IOException) {
//
//            }
//        }
//    }
}