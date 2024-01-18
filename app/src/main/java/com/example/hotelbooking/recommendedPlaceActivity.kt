package com.example.hotelbooking

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.hotelbooking.databinding.ActivityRecommendedPlaceBinding
import com.example.newsapp.MySingleton

class recommendedPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendedPlaceBinding

    companion object {
        const val CITYNAME = "city_name"
        const val PLACENAME = "place_name"
    }

    val activity = this
    val list: ArrayList<PlaceModel> = ArrayList()
    val adapter = PlaceListAdapter(list, activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecommendedPlaceBinding.inflate(layoutInflater)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        setContentView(binding.root)


        val place_name = intent.getStringExtra(PLACENAME)

//        Toast.makeText(this, "$place_name", Toast.LENGTH_SHORT).show()

        ///////////

        if (place_name != null) {
            fetchData(place_name)
        }

        binding.apply {
            recyclerView.adapter = adapter
        }

    }

    private fun fetchData(place_name: String) {
        binding.progressBar.visibility = View.VISIBLE

        val url = "https://dad8-34-90-253-84.ngrok-free.app/hotels/$place_name"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,

            { response ->
                try {
                    if (response.has("hotels")) {
                        val placeJsonArray = response.getJSONArray("hotels")
                        val placeArray = ArrayList<PlaceModel>()

                        if (placeJsonArray.length() == 0) {
                            Toast.makeText(this, "No Places available", Toast.LENGTH_SHORT).show()
                        } else {
                            for (i in 0 until placeJsonArray.length()) {
                                val placeJsonObject = placeJsonArray.getJSONObject(i)

                                val places = PlaceModel(
                                    placeJsonObject.getString("Place"),
                                    placeJsonObject.getString("City"),
                                    placeJsonObject.getString("Ratings"),
                                    placeJsonObject.getString("Distance"),
                                    placeJsonObject.getString("Place_desc")
                                )
                                placeArray.add(places)
                            }

                            adapter.updateplaces(placeArray)
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