package com.example.carbooking.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carbooking.R

class ResponsePhotoAdapter(val context: Context, val userList: List<ResponsePhotos>):
    RecyclerView.Adapter<ResponsePhotoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var image_id: TextView

        init {
            image = itemView.findViewById(R.id.image_view)
            image_id = itemView.findViewById(R.id.image_id)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResponsePhotoAdapter.ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResponsePhotoAdapter.ViewHolder, position: Int) {
        holder.image_id.text = userList[position].image_id
        holder.image = userList[position].image
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}