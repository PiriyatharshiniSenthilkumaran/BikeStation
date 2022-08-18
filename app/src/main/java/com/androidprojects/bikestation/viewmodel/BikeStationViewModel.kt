package com.androidprojects.bikestation.viewmodel

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.androidprojects.bikestation.model.BikeStationDetails
import com.androidprojects.bikestation.utility.Utility
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.roundToInt

class BikeStationViewModel(application: Application) : AndroidViewModel(application) {

    private val LOCATION_PERMISSION_REQ_CODE = 1000;

    // Instantiate the RequestQueue.
    val context = getApplication<Application>().applicationContext

    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var userLocationLatitude = 0.0
    var userLocationLongitude = 0.0



    lateinit var jsonObjectRequest: JsonObjectRequest

    val url = "https://www.poznan.pl/mim/plan/map_service.html?mtype=pub_transport&co=stacje_rowerowe"

    private val bikeStations: MutableLiveData<ArrayList<BikeStationDetails>> by lazy{
        MutableLiveData<ArrayList<BikeStationDetails>>().also{

            if(Utility.isOnline(context)) {
                loadBikeStations()
            }else{
                Toast.makeText(context, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }




    fun getBikeStations(): LiveData<ArrayList<BikeStationDetails>>{
        return bikeStations
    }


    /**
    Fetch Bike Stations data from the API
     */
    private fun loadBikeStations() {


        getCurrentLocation()
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        //TODO: fetch data
        Log.v("CCPP", "loadBikeStations")
        Log.v("CCPP", "url: " + url)
// Request a string response from the provided URL.
        jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, JSONObject(),
            Response.Listener<JSONObject?> {

                val bikeStationsArrayList:  ArrayList<BikeStationDetails> = ArrayList<BikeStationDetails>();
                val featureJsonObject: JSONObject = it
                val featureJsonArray: JSONArray = featureJsonObject.getJSONArray("features")
                Log.v("CCPP", "success: "  + featureJsonArray.length())
                for (i in 0 until featureJsonArray.length()) {
                    val bikeStationJSONObject = featureJsonArray.getJSONObject(i)

                    val geometryJSONObject = bikeStationJSONObject.getJSONObject("geometry")


                    val coordinatesJSONArray = geometryJSONObject.getJSONArray("coordinates")


                    var latitude = coordinatesJSONArray.getDouble(0)
                    var longitude = coordinatesJSONArray.getDouble(1)


                    var bikeStationID = bikeStationJSONObject.getString("id")
                    var propertiesJSONObject = bikeStationJSONObject.getJSONObject("properties")
                    var freeRacks = propertiesJSONObject.getString("free_racks")
                    var bikes = propertiesJSONObject.getString("bikes")
                    var label = propertiesJSONObject.getString("label")
                    var bikeRacks = propertiesJSONObject.getString("bike_racks")


                    val loc1 = Location("")
                    loc1.latitude = userLocationLatitude
                    loc1.longitude = userLocationLongitude

                    val loc2 = Location("")
                    loc2.latitude = latitude
                    loc2.longitude = longitude


                    val distance = loc1.distanceTo(loc2)
                    val distanceInMeters = loc1.distanceTo(loc2).roundToInt()

                    Log.v("ccpp_dis", " user lat"+ userLocationLatitude + "  user long" + userLocationLongitude)
                    Log.v("ccpp_dis", " bike lat"+ latitude + "  bike long" + longitude)


                    var bikeStationDetails = BikeStationDetails(bikeStationID, latitude, longitude, distanceInMeters, freeRacks, bikes, label, bikeRacks)

                    bikeStationsArrayList.add(bikeStationDetails)


                    // Your code here
                }

                bikeStations.setValue(bikeStationsArrayList);

                Log.v("CCPP", "success: " )
            }, Response.ErrorListener {
                Log.v("CCPP", "failed: " + it.localizedMessage)
            })
        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["mtype"] = "pub_transport"
                params["co"] = "stacje_rowerowe"
                Log.v("CCPP", "params: " + params)
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params.put("Content-Type", "application/json");
                return params
            }

        }


        var socketTimeout = 2500
        var policy: RetryPolicy = DefaultRetryPolicy(
            socketTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        jsonObjectRequest.setRetryPolicy(policy)
        requestQueue.add<JSONObject?>(jsonObjectRequest)

    }





    /**
     * method to get user's current location
     *
     */

    private fun getCurrentLocation() {
        // checking location permission
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request permission
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE);
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // getting the last known or current location
                if (location != null) {
                    userLocationLatitude = location.longitude
                    userLocationLongitude = location.latitude
                    Log.v("ccpp_loc", "location: long: " + userLocationLongitude + "lat: " + userLocationLatitude)
                }

            }
            .addOnFailureListener {
                it.localizedMessage
//                Toast.makeText(this, "Failed on getting current location",
//                    Toast.LENGTH_SHORT).show()

            }
    }


    //checks whether permission is granted or not
    fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {

        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                } else {
                    // permission denied
                    Log.d("ccpp", "You need to grant permission to access location" )


//                    Toast.makeText(this, "You need to grant permission to access location",
//                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }







}