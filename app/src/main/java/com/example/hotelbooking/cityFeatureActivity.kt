package com.example.hotelbooking

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelbooking.databinding.ActivityCityFeatureBinding

class cityFeatureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCityFeatureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCityFeatureBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        supportActionBar?.hide()

        binding.buttonSearchHotel.setOnClickListener {

            val city_name = binding.inputCityName.text.toString()

            if(city_name.isEmpty()) {

                Toast.makeText(this, "Mandatary Fields must be filled", Toast.LENGTH_LONG).show()
            }
            else {

                val intent = Intent(this, recommendedHotelActivity::class.java)
                intent.putExtra(recommendedPlaceActivity.CITYNAME, city_name)
                startActivity(intent)

            }

        }

        binding.nearbySearchText.setOnClickListener {

            startActivity(Intent(this, NearbyHotelActivity::class.java))
        }
    }
}