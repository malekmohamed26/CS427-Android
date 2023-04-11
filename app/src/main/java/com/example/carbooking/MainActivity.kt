package com.example.carbooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Time
import java.util.Random


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<Rides>()
//        val randomNumber: Random = Random()
//        val arrivalTime:Time = Time(15)
//
//        for (i in 1..10) {
//                data.add(Rides(randomNumber, 10, arrivalTime))
////            //data.add(Rides())
//        }
        val id : Int
        val img_src : String


        // This will pass the ArrayList to our Adapter
        val adapter = RidesAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter
    }
}