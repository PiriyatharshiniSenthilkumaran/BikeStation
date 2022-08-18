package com.androidprojects.bikestation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidprojects.bikestation.R
import com.androidprojects.bikestation.adapter.BikeStationAdapter
import com.androidprojects.bikestation.model.BikeStationDetails
import com.androidprojects.bikestation.viewmodel.BikeStationViewModel


class BikeStationListFragment : Fragment(R.layout.fragment_bike_station_list) {

    var bikeStationDetailsArrayList: ArrayList<BikeStationDetails> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var bikeStationsRecyclerView: RecyclerView


    val bikeStationsViewModel: BikeStationViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bikeStationListView: View  =  inflater.inflate(R.layout.fragment_bike_station_list, container, false)


        bikeStationsRecyclerView = bikeStationListView.findViewById(R.id.bike_stations_recyclerview)

        return bikeStationListView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bikeStationsViewModel.getBikeStations().observe(viewLifecycleOwner, Observer<ArrayList<BikeStationDetails>>{ bikeStations ->
            //Test dummy data
//            val bikeStationDetails1: BikeStationDetails = BikeStationDetails("049", 45.33, 4.44, "23", "4", "Rocky Bike Station", "23")
//            val bikeStationDetails2: BikeStationDetails = BikeStationDetails("049", 45.33, 4.44, "23", "4", "Rocky Bike Station", "23")
//            val bikeStationDetails3: BikeStationDetails = BikeStationDetails("049", 45.33, 4.44, "23", "4", "Rocky Bike Station", "23")


            //bikeStationDetailsArrayList.add(bikeStationDetails1)
            //bikeStationDetailsArrayList.add(bikeStationDetails2)
            //bikeStationDetailsArrayList.add(bikeStationDetails3)

            //Update UI
            bikeStationDetailsArrayList = ArrayList<BikeStationDetails> (bikeStations)
            loadBikeStationRecyclerView(bikeStationDetailsArrayList)
        })

    }


    /**
     * Populate data in the RecyclerView
     */
    fun loadBikeStationRecyclerView(bikeStationsArrayList: ArrayList<BikeStationDetails>){
        var bikeStationAdapter = BikeStationAdapter(bikeStationsArrayList)
        linearLayoutManager = LinearLayoutManager(context)
        bikeStationsRecyclerView.layoutManager = linearLayoutManager
        bikeStationsRecyclerView.adapter = bikeStationAdapter
    }
}