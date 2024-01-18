package com.example.hotelbooking

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.databinding.ItemPlaceBinding

class PlaceListAdapter(private var list: ArrayList<PlaceModel>, var  context: Context): RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(var binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PlaceModel, context: Context) {
            binding.apply {

                placeCityname.text = model.City
                placeName.text = model.Place
                placeRating.text = model.Ratings
                placeDesc.text = model.Place_desc
                placeDistance.text = model.Distance

                root.setOnClickListener {

                    val intent = Intent(root.context, PlaceDetailActivity::class.java)
                    PlaceDetailActivity.place_name = model.Place
                    PlaceDetailActivity.place_cityname = model.City
                    PlaceDetailActivity.place_rating = model.Ratings
                    PlaceDetailActivity.place_desc = model.Place_desc
                    PlaceDetailActivity.place_distance = model.Distance

                    root.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(
            ItemPlaceBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(list[position], context)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateplaces(placeArray: ArrayList<PlaceModel>) {
        list.clear()
        list.addAll(placeArray)

        notifyDataSetChanged()
    }

}
