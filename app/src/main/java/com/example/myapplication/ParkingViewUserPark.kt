package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.dataAccess.apolloClient
import com.example.myapplication.dataAccess.parkingId
import com.google.android.gms.maps.model.Marker
import com.google.android.material.textfield.TextInputLayout

class ParkingViewUserPark : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_view_user_park)

        val name: TextView = findViewById(R.id.parkingLotName)
        val address: TextView = findViewById(R.id.parkingLotAddress)
        val price: TextView = findViewById(R.id.parkingLotPrice)
        val space: TextView = findViewById(R.id.parkingLotSpace)
        val monday: TextView = findViewById(R.id.lunes_horario)
        val tuesday: TextView = findViewById(R.id.martes_horario)
        val wednesday: TextView = findViewById(R.id.miercoles_horario)
        val thursday: TextView = findViewById(R.id.jueves_horario)
        val friday: TextView = findViewById(R.id.viernes_horario)
        val saturday: TextView = findViewById(R.id.sabado_horario)
        val sunday: TextView = findViewById(R.id.domingo_horario)


        lifecycleScope.launchWhenResumed {
            val response = apolloClient.query(GetParkingByIdQuery(parkingId)).await()
            val parking = response.data!!.par_getParkingById

            if (parking != null) {
                name.setText(parking.name)
                address.setText(parking.address)
                Log.d("ParkingList", "Success ${parking}")
                Log.d("ParkingList", "Success ${parking.pricePerMinute.toString()}")
                price.setText(parking.pricePerMinute.toString())
                space.setText(parking.totalSpaces.toString())
                monday.setText((parking.openHours[0]?.opening + " - "+ parking.openHours[0]?.closing).toString())
                tuesday.setText((parking.openHours[1]?.opening + " - "+parking.openHours[1]?.closing).toString())
                wednesday.setText((parking.openHours[2]?.opening + " - "+parking.openHours[2]?.closing).toString())
                thursday.setText((parking.openHours[3]?.opening + " - "+parking.openHours[3]?.closing).toString())
                friday.setText((parking.openHours[4]?.opening + " - "+parking.openHours[4]?.closing).toString())
                saturday.setText((parking.openHours[5]?.opening + " - "+parking.openHours[5]?.closing).toString())
                sunday.setText((parking.openHours[6]?.opening + " - "+parking.openHours[6]?.closing).toString())

            }

            //mMap.addMarker(MarkerOptions().position(Museo).title(response?.data?.par_getParkings?.get(0)?.name))

        }
    }



}