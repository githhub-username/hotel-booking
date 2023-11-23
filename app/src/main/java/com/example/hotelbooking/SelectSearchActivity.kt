package com.example.hotelbooking

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.hotelbooking.databinding.ActivitySelectSearchBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class SelectSearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySelectSearchBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        binding.searchCityFeature.setOnClickListener {

            val intent = Intent(this, cityFeatureActivity::class.java)
            startActivity(intent)

        }

        binding.searchNearby.setOnClickListener {

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

            fetchLocation()

            val intent = Intent(this, recommendedHotelActivity::class.java)
            startActivity(intent)

        }

    }

    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }

        task.addOnSuccessListener {
            if(it != null)
            {
                Toast.makeText(applicationContext,"${it.latitude} ${it.longitude}", Toast.LENGTH_LONG).show()
            }
        }
    }
}