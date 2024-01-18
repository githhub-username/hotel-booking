package com.example.hotelbooking

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.databinding.ItemPlaceNearbyBinding

class NearbyPlaceListAdapter(private var list: ArrayList<NearbyPlaceModel>, var context: Context) : RecyclerView.Adapter<NearbyPlaceListAdapter.NearbyPlaceViewHolder>() {

    class NearbyPlaceViewHolder(var binding: ItemPlaceNearbyBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(nearbyPlaceModel: NearbyPlaceModel, context: Context) {

            binding.apply {
                placeName.text = nearbyPlaceModel.City
                placeDistance.text = nearbyPlaceModel.Distance + " km"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyPlaceViewHolder {
        return NearbyPlaceViewHolder(ItemPlaceNearbyBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: NearbyPlaceViewHolder, position: Int) {
        holder.bind(list[position], context)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update_nearby_places(nearbyPlacesarray: ArrayList<NearbyPlaceModel>) {

        list.clear()
        list.addAll(nearbyPlacesarray)

        notifyDataSetChanged()
    }

}


