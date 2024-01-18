package com.example.hotelbooking

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.example.hotelbooking.databinding.ActivityHotelDetailsBinding

class HotelDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHotelDetailsBinding

    companion object {

        var hotel_name: String = ""
        var hotel_star_rating = ""
        var hotel_description = ""
        var hotel_url = ""
        var hotel_address = ""

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHotelDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {

            hotelName.text = NearbyHotelDetails.hotel_name
            hotelStarRating.text = NearbyHotelDetails.hotel_star_rating
            hotelAddress.text = NearbyHotelDetails.hotel_address
            hotelDescription.text = NearbyHotelDetails.hotel_description
            hotelUrl.text = NearbyHotelDetails.hotel_url
        }

        binding.hotelUrl.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            val uri = Uri.parse(if (hotel_url.startsWith("http://") || NearbyHotelDetails.hotel_url.startsWith("https://")) NearbyHotelDetails.hotel_url else "http://${NearbyHotelDetails.hotel_url}")
            customTabsIntent.launchUrl(this, uri)
        }
    }
}