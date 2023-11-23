package com.example.hotelbooking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbooking.databinding.ActivityRecommendedHotelBinding

class recommendedHotelActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecommendedHotelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecommendedHotelBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        supportActionBar?.hide()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val items = fetchData()
        val adapter = HotelListAdapter(items)

        binding.recyclerView.adapter = adapter
    }

    private fun fetchData(): ArrayList<String> {

        val list = ArrayList<String>()

        for(i in 0 until 100) {
            list.add("Hotel $i")
        }

        return list
    }
}