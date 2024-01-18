package com.example.hotelbooking

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.hotelbooking.databinding.ActivityNearbyHotelBinding
import com.example.newsapp.MySingleton
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class NearbyHotelActivity : AppCompatActivity() {

 //   companion object {
   //     const val LATITUDE = "latitude"
     //   const val LONGITUDE = "longitude"
   // }

    private lateinit var latitude: String
    private lateinit var longitude: String

    private lateinit var binding: ActivityNearbyHotelBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    val activity = this
    val list: ArrayList<NearbyHotelModel> = ArrayList()
    val adapter = NearbyHotelListAdapter(list, activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()

        binding = ActivityNearbyHotelBinding.inflate(layoutInflater)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        setContentView(binding.root)

     //   val latitude = intent.getStringExtra(LATITUDE)
       // val longitude = intent.getStringExtra(LONGITUDE)



        binding.apply {
            recyclerView.adapter = adapter
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
                        val nearbyHotelJsonArray = response.getJSONArray("hotels")
                        val nearby_hotelArray = ArrayList<NearbyHotelModel>()

                        if (nearbyHotelJsonArray.length() == 0) {
                            Toast.makeText(this, "No Places available", Toast.LENGTH_SHORT).show()
                        } else {
                            for (i in 0 until nearbyHotelJsonArray.length()) {
                                val nearby_hotelJsonObject = nearbyHotelJsonArray.getJSONObject(i)

                                val nearby_hotels = NearbyHotelModel(
                                    nearby_hotelJsonObject.getString("property_name"),
                                    nearby_hotelJsonObject.getString("address"),
                                    nearby_hotelJsonObject.getString("hotel_star_rating"),
                                    nearby_hotelJsonObject.getString("pageurl"),
                                    nearby_hotelJsonObject.getString("hotel_description"),
                                    nearby_hotelJsonObject.getString("hotel_distance")
                                )
                                nearby_hotelArray.add(nearby_hotels)
                            }

                            adapter.update_nearby_hotels(nearby_hotelArray)
                        }
                    } else {
                        Toast.makeText(this, "No Hotels available", Toast.LENGTH_SHORT).show()
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
                this.latitude = it.latitude.toString()
                this.longitude = it.longitude.toString()

                Toast.makeText(applicationContext, "$latitude , $longitude", Toast.LENGTH_LONG).show()

                fetchData(latitude,longitude)
            }
        }
    }
}
