package com.example.hotelbooking

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.hotelbooking.databinding.ActivityRecommendedHotelBinding
import com.example.newsapp.MySingleton

class recommendedHotelActivity : AppCompatActivity() {

    companion object {
        const val CITYNAME = "city_name"
    }

    private lateinit var binding : ActivityRecommendedHotelBinding

    val activity = this
    val list: ArrayList<HotelModel> = ArrayList()
    val adapter = HotelListAdapter(list, activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecommendedHotelBinding.inflate(layoutInflater)
        val view = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        setContentView(view)

        binding.apply {
            recyclerView.adapter = adapter
        }

        val city_name = intent.getStringExtra(CITYNAME)

  //      Toast.makeText(this, "$city_name", Toast.LENGTH_SHORT).show()

///////////
        if (city_name != null) {
            fetchData(city_name)
        }

    }

    private fun fetchData(city_name: String) {
        binding.progressBar.visibility = View.VISIBLE

        val url = "https://12a7-34-87-255-194.ngrok-free.app/hotels/$city_name"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,

            { response ->
                try {
                    if (response.has("hotels")) {
                        val hotelJsonArray = response.getJSONArray("hotels")
                        val hotelArray = ArrayList<HotelModel>()

                        if (hotelJsonArray.length() == 0) {
                            Toast.makeText(this, "No Hotels available", Toast.LENGTH_SHORT).show()
                        } else {
                            for (i in 0 until hotelJsonArray.length()) {
                                val hotelJsonObject = hotelJsonArray.getJSONObject(i)

                                val hotels = HotelModel(
                                    hotelJsonObject.getString("property_name"),
                                    hotelJsonObject.getString("address"),
                                    hotelJsonObject.getString("hotel_star_rating"),
                                    hotelJsonObject.getString("pageurl"),
                                    hotelJsonObject.getString("hotel_description")
                                )
                                hotelArray.add(hotels)
                            }

                            adapter.updatehotels(hotelArray)
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


}