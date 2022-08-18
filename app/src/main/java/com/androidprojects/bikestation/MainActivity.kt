package com.androidprojects.bikestation


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.androidprojects.bikestation.fragment.BikeStationListFragment
import com.androidprojects.bikestation.utility.Utility


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //call fragment
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<BikeStationListFragment>(R.id.fragment_container_view)
            }
        }

    }


}