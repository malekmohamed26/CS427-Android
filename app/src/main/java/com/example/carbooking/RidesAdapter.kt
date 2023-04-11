package com.example.carbooking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RidesAdapter (private val listOfRides: List<Rides>): RecyclerView.Adapter<RidesAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        // val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView1: TextView = itemView.findViewById(R.id.id)
        val textView2: TextView = itemView.findViewById(R.id.img_src)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = listOfRides[position]

        // sets the name to the textview from our itemHolder class
        holder.textView1.text = ItemsViewModel.id.toString()

        // sets the icons to the imageview from our itemHolder class
        holder.textView2.text = ItemsViewModel.img_src.toString()
    }

    override fun getItemCount(): Int {
        return listOfRides.size
    }
}
