package com.androidprojects.bikestation.viewholder

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.androidprojects.bikestation.R
import com.androidprojects.bikestation.model.BikeStationDetails
//Recyclerview --- ViewHolder to update UI
class BikeStationViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val bikeStationCardview: CardView = view.findViewById(R.id.bike_station_card_view)
    val  bikeStationNameTextView: TextView = view.findViewById(R.id.bike_station_name_textview)
    val bikeStationDistanceTextView: TextView = view.findViewById(R.id.bike_station_distance_textview)
    val availableBikesTextView: TextView = view.findViewById(R.id.available_bikes_textview)
    val availablePlacesTextView: TextView = view.findViewById(R.id.available_places_textview)


    //updates UI of each element
    fun updateUI(bikeStationDetails: BikeStationDetails){

        Log.v("ccpp_d", "inside updateui" + bikeStationDetails.label)
        bikeStationNameTextView.text = bikeStationDetails.bikeStationID + "   " + bikeStationDetails.label
        //TODO: calculate distance
        val latitude: Double = bikeStationDetails.latitude
        val longitude: Double = bikeStationDetails.longitude

        bikeStationDistanceTextView.text = bikeStationDetails.distance.toString() + "m" + " Bike Station"


        availableBikesTextView.text = bikeStationDetails.bikes
        availablePlacesTextView.text = bikeStationDetails.bikeRacks
    }



}