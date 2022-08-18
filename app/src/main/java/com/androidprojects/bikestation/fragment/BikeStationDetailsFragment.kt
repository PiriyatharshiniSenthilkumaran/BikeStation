package com.androidprojects.bikestation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.androidprojects.bikestation.R
import com.androidprojects.bikestation.model.BikeStationDetails
import com.androidprojects.bikestation.utility.Utility
import com.androidprojects.bikestation.viewholder.BikeStationViewHolder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class BikeStationDetailsFragment(val bikeStationDetails: BikeStationDetails) : Fragment(R.layout.fragment_bike_station_details), OnMapReadyCallback{

    private lateinit var bikeStationMap: GoogleMap

    var bikeStationLatitude: Double = 0.0
    var bikeStationLongitude: Double = 0.0
    var bikeStationLabel: String = "Bike Station"

    lateinit var viewHolder: BikeStationViewHolder
    lateinit var detailCardView: CardView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var bikeStationDetailView: View = inflater.inflate(R.layout.fragment_bike_station_details, container, false)

        bikeStationLatitude = bikeStationDetails.latitude
        bikeStationLongitude = bikeStationDetails.longitude
        bikeStationLabel = bikeStationDetails.label

        detailCardView = bikeStationDetailView.findViewById(R.id.bike_station_card_view)

        viewHolder = BikeStationViewHolder(detailCardView)
        viewHolder.updateUI(bikeStationDetails)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return bikeStationDetailView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button pressed even from the device's software button
                // close the current fragment
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@BikeStationDetailsFragment).commit()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // bikeStationsViewModel = ViewModelProvider(this).get(BikeStationViewModel::class.java)
        // TODO: Use the ViewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    /**
    shows location of the bike station in the map with marker
     */
    override fun onMapReady(googleMap: GoogleMap) {
        bikeStationMap = googleMap

        // Add a marker in Sydney and move the camera
        val bikeStationCoordinate = LatLng(bikeStationLatitude, bikeStationLongitude)
        bikeStationMap.addMarker(
            MarkerOptions()
                .position(bikeStationCoordinate)
                .icon(context?.let { Utility.bitmapDescriptorFromVector(it, R.drawable.ic_bike) })
                .title(bikeStationLabel))
        bikeStationMap.moveCamera(CameraUpdateFactory.newLatLng(bikeStationCoordinate))
        bikeStationMap.animateCamera(CameraUpdateFactory.zoomTo( 8.0f ))
    }



}