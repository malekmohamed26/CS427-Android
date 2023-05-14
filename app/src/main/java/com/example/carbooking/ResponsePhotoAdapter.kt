package com.example.carbooking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carbooking.network.MarsPhoto

class ResponsePhotoAdapter(val context: Context, val userList: List<MarsPhoto>):
    RecyclerView.Adapter<ResponsePhotoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image_view)
        var image_id: TextView = itemView.findViewById(R.id.image_id)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image_id.text = userList[position].id
        Glide.with(context).load(userList[position].imgSrcUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return userList.count()
    }

}