package com.androidprojects.bikestation.model

/**
 * Model Class of BikeStations - consisting of the details
 */
data class BikeStationDetails(
    val bikeStationID: String,
    var latitude: Double,
    val longitude: Double,
    val distance: Int,
    val freeRacks: String,
    val bikes: String,
    val label: String,
    val bikeRacks: String

)
