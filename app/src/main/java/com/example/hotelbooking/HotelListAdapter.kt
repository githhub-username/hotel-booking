package com.example.hotelbooking

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.databinding.ItemHotelBinding

class HotelListAdapter(private var list: ArrayList<HotelModel>, var context: Context): RecyclerView.Adapter<HotelListAdapter.HotelViewHolder>() {
    class HotelViewHolder (var binding: ItemHotelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: HotelModel, context: Context) {
            binding.apply {
                hotelName.text = model.property_name
                hotelAddress.text = model.address
                hotelStarRating.text = model.hotel_star_rating
                hotelUrl.text = model.pageurl

                hotelUrl.setOnClickListener {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    val uri = Uri.parse(if (model.pageurl.startsWith("http://") || model.pageurl.startsWith("https://")) model.pageurl else "http://${model.pageurl}")
                    customTabsIntent.launchUrl(root.context, uri)
                }

                root.setOnClickListener {
                    val intent = Intent(root.context, HotelDetailsActivity::class.java)

                    NearbyHotelDetails.hotel_name = model.property_name
                    NearbyHotelDetails.hotel_star_rating = model.hotel_star_rating
                    NearbyHotelDetails.hotel_description = model.hotel_description
                    NearbyHotelDetails.hotel_url = model.pageurl
                    NearbyHotelDetails.hotel_address = model.address
                    root.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {

        return HotelViewHolder(ItemHotelBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {

        holder.bind(list[position], context)
    }

    fun updatehotels(hotelArray: ArrayList<HotelModel>) {
        list.clear()
        list.addAll(hotelArray)

        notifyDataSetChanged()
    }


}

//class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//    val titleView: TextView = itemView.findViewById(R.id.title)
//}