package com.example.hotelbooking

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.example.hotelbooking.databinding.ActivityNearbyHotelDetailsBinding

class NearbyHotelDetails : AppCompatActivity() {

    companion object {

        var hotel_name: String = ""
        var hotel_star_rating = ""
        var hotel_distance = ""
        var hotel_description = ""
        var hotel_url = ""
        var hotel_address = ""

    }

    private lateinit var binding: ActivityNearbyHotelDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNearbyHotelDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {

            hotelName.text = hotel_name
            hotelStarRating.text = hotel_star_rating
            hotelDistance.text = hotel_distance + " km"
            hotelAddress.text = hotel_address
            hotelDescription.text = hotel_description
            hotelUrl.text = hotel_url
        }

        binding.hotelUrl.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            val uri = Uri.parse(if (hotel_url.startsWith("http://") || hotel_url.startsWith("https://")) hotel_url else "http://$hotel_url")
            customTabsIntent.launchUrl(this, uri)
        }

    }
}

