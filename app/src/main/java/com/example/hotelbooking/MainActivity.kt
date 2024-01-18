package com.example.hotelbooking

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.hotelbooking.databinding.ActivityMainBinding
import com.example.newsapp.MySingleton
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var latitude: String
    private lateinit var longitude: String

    val activity = this
    val list: ArrayList<NearbyPlaceModel> = ArrayList()
    val adapter = NearbyPlaceListAdapter(list, activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.apply {

            recyclerView.adapter = adapter

        }

        binding.findPlaceButton.setOnClickListener {

            val intent = Intent(this, recommendedPlaceActivity::class.java)
            startActivity(intent)

        }

        binding.findHotelButton.setOnClickListener {
            val intent = Intent(this, cityFeatureActivity::class.java)
            startActivity(intent)
        }

        binding.findPlaceButton.setOnClickListener {

            val place_name = binding.s.text.toString()

            if (place_name.isNotEmpty()) {
                val intent = Intent(this, recommendedPlaceActivity::class.java)
                intent.putExtra(recommendedPlaceActivity.PLACENAME, place_name)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Fill mandatory fields", Toast.LENGTH_SHORT).show()
            }
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
        }

        task.addOnSuccessListener {
            if (it != null) {
                latitude = it.latitude.toString()
                longitude = it.longitude.toString()

  //              intent.putExtra(NearbyHotelActivity.LATITUDE, latitude)
    //            intent.putExtra(NearbyHotelActivity.LONGITUDE, longitude)

                Toast.makeText(applicationContext, "$latitude , $longitude", Toast.LENGTH_LONG).show()

                fetchData(latitude, longitude)
            }
        }
    }

    private fun fetchData(latitude: String, longitude: String) {
        binding.progressBar.visibility = View.VISIBLE

        val url = "https://dad8-34-90-253-84.ngrok-free.app/hotels/$latitude/$longitude"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,

            { response ->
                try {
                    if (response.has("hotels")) {
                        val nearby_placesJsonArray = response.getJSONArray("hotels")
                        val nearby_placesArray = ArrayList<NearbyPlaceModel>()

                        if (nearby_placesJsonArray.length() == 0) {
                            Toast.makeText(this, "No Places available", Toast.LENGTH_SHORT).show()
                        } else {
                            for (i in 0 until nearby_placesJsonArray.length()) {
                                val nearby_placeJsonObject = nearby_placesJsonArray.getJSONObject(i)

                                val nearby_places = NearbyPlaceModel(
                                    nearby_placeJsonObject.getString("City"),
                                    nearby_placeJsonObject.getString("Distance"),
                                )
                                nearby_placesArray.add(nearby_places)
                            }

                            adapter.update_nearby_places(nearby_placesArray)
                        }
                    } else {
                        Toast.makeText(this, "No Places available", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error processing response", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

                binding.progressBar.visibility = View.GONE // Hide progress bar after data is loaded
            },
            {
                Toast.makeText(this, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE // Hide progress bar in case of an error
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

}
