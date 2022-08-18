package com.androidprojects.bikestation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.androidprojects.bikestation.R
import com.androidprojects.bikestation.fragment.BikeStationDetailsFragment
import com.androidprojects.bikestation.model.BikeStationDetails
import com.androidprojects.bikestation.utility.Utility
import com.androidprojects.bikestation.viewholder.BikeStationViewHolder

/**Adapter to handle loading the recyclerview
populate bike station data
*/
class BikeStationAdapter(private val bikeStationDataArrayList: ArrayList<BikeStationDetails>): RecyclerView.Adapter<BikeStationViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BikeStationViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cardview_bike_station_details, viewGroup, false)
        return  BikeStationViewHolder(view)
    }

    override fun onBindViewHolder(holder: BikeStationViewHolder, position: Int) {
        val bikeStationDetails: BikeStationDetails = bikeStationDataArrayList.get(position)
        holder.updateUI(bikeStationDetails)

        //when user taps on the item it will open BikeStationDetailsFragment
        holder.bikeStationCardview.setOnClickListener(View.OnClickListener {
            val activity=it.context as AppCompatActivity

            val bikeStationDetailsFragment: BikeStationDetailsFragment = BikeStationDetailsFragment(bikeStationDataArrayList.get(position))
            activity.supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_view,bikeStationDetailsFragment)
                .commitNow()

        })
    }

    /**
     * return number of items in the arraylist
     */
    override fun getItemCount(): Int {
        return bikeStationDataArrayList.size
    }

}