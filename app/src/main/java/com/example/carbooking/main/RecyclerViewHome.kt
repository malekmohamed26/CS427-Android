package com.example.carbooking.main

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carbooking.MarsApi
import com.example.carbooking.R
import kotlinx.coroutines.launch
import java.io.IOException

class RecyclerViewHome : AppCompatActivity() {
    lateinit var responsePhotoAdapter: ResponsePhotoAdapter
    lateinit var recycler_view: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_home)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.statusBarColor = Color.WHITE
        }

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)

        getMarsPhotos()
    }

    private fun getMarsPhotos() {
        lifecycleScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getPhotos()


            } catch (e: IOException) {

            }
        }
    }
}