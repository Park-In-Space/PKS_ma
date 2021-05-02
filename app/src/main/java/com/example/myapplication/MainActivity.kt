package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.dataAccess.apolloClient
import com.example.myapplication.type.OpenHoursInput
import com.example.myapplication.type.ParkingInputLoc
import com.example.myapplication.type.ParkinglotuserAuthInput
import com.example.myapplication.type.UserClienAuthInput

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

        val buttonSignUp: Button = findViewById(R.id.buttonLogin)
        buttonSignUp.setOnClickListener {
            // Do something in response to button click
        }

        lifecycleScope.launchWhenResumed {
            val openHoursList = listOf( OpenHoursInput(opening = Input.optional("06:00"),
                                            closing = Input.optional("23:59")),
                                        OpenHoursInput(opening = Input.optional("06:00"),
                                            closing = Input.optional("23:59")),
                                        OpenHoursInput(opening = Input.optional("06:00"),
                                            closing = Input.optional("23:59")),
                                        OpenHoursInput(opening = Input.optional("06:00"),
                                            closing = Input.optional("23:59")),
                                        OpenHoursInput(opening = Input.optional("06:00"),
                                            closing = Input.optional("23:59")),
                                        OpenHoursInput(opening = Input.optional("06:00"),
                                            closing = Input.optional("23:59")),
                                        OpenHoursInput(opening = Input.optional("11:00"),
                                            closing = Input.optional("22:00"))
                                        )
            val newParking = ParkingInputLoc( name = "City Parking Calle 85" ,
                                                address = Input.optional("Calle 85 # 12-46 Bpgpta"),
                                                pricePerMinute = 105,
                                                totalSpaces = 200,
                                                usedSpaces = 0,
                                                latitude = 4.669674656951608,
                                                longitude = -74.05285064827103,
                                                openHours = openHoursList)
            val response = apolloClient.mutate(PostParkingMutation( newParking )).await()
            Log.d("ParkingList", "Success ${response?.data}")
            //Log.d("ParkingList", "Success ${response?.data?.par_getParkings?.get(0)?.name}")
        }

    }
}