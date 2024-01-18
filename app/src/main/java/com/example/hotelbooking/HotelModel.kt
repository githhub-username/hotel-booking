package com.example.hotelbooking

data class HotelModel(
    val property_name: String,
    val address: String,
    val hotel_star_rating: String,
    val pageurl: String,
    val hotel_description: String,
)

data class NearbyHotelModel(
    val property_name: String,
    val address: String,
    val hotel_star_rating: String,
    val pageurl: String,
    val hotel_description: String,
    val hotel_distance: String,
)

data class PlaceModel(
    val Place: String,
    val City: String,
    val Ratings: String,
    val Distance: String,
    val Place_desc: String,
)

data class NearbyPlaceModel(
    val City: String,
    val Distance: String,
)



