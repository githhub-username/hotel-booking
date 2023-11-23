package com.example.hotelbooking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HotelListAdapter(private val items: ArrayList<String>): RecyclerView.Adapter<HotelViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hotel, parent, false)
        return HotelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {

        val currentItem = items[position]
        holder.titleView.text = currentItem
    }


}

class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleView: TextView = itemView.findViewById(R.id.title)
}