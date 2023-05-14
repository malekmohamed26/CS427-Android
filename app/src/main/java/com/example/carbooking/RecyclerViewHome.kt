package com.example.carbooking

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carbooking.network.MarsPhoto
import kotlinx.coroutines.launch
import java.io.IOException

class RecyclerViewHome : AppCompatActivity() {
    lateinit var responsePhotoAdapter: ResponsePhotoAdapter
    lateinit var recycler_view: RecyclerView
    lateinit var progress_bar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_home)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.statusBarColor = Color.WHITE
        }

        progress_bar = findViewById(R.id.progress_bar)
        recycler_view = findViewById(R.id.recycler_view)

        progress_bar.visibility = View.INVISIBLE

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = ResponsePhotoAdapter(this, ArrayList<MarsPhoto>())


        getMarsPhotos()
    }

    private fun getMarsPhotos() {
        lifecycleScope.launch {
            try {
                progress_bar.visibility = View.VISIBLE
                recycler_view.adapter = ResponsePhotoAdapter(applicationContext,
                    MarsApi.retrofitService.getPhotos())
                progress_bar.visibility = View.INVISIBLE
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}