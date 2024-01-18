package com.example.hotelbooking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelbooking.databinding.ActivityPlaceDetailBinding

class PlaceDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaceDetailBinding


    companion object {

        var place_name: String = ""
        var place_cityname = ""
        var place_rating = ""
        var place_desc = ""
        var place_distance = ""

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlaceDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {

            placeName.text = place_name


        }

    }
}