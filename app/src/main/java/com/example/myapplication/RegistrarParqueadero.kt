package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.example.myapplication.dataAccess.*
import com.example.myapplication.type.OpenHoursInput
import com.example.myapplication.type.ParkingInputLoc
import com.example.myapplication.type.UserClienAuthInput
import com.google.android.material.textfield.TextInputLayout

class RegistrarParqueadero : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_parqueadero)


        val name: TextInputLayout = findViewById(R.id.parkingName)
        val address: TextInputLayout = findViewById(R.id.parkingAddress)
        val price: TextInputLayout = findViewById(R.id.parkingPrice)
        val space: TextInputLayout = findViewById(R.id.parkinTotalSpace)
        val enterMonday: TextInputLayout = findViewById(R.id.parkingEnterMonday)
        val exitMonday: TextInputLayout = findViewById(R.id.parkingExitMonday)
        val enterTuesday: TextInputLayout = findViewById(R.id.parkingEnterTuesday)
        val exitTuesday: TextInputLayout = findViewById(R.id.parkingExitTuesday)
        val enterWednesday: TextInputLayout = findViewById(R.id.parkingEnterWednesday)
        val exitWednesday: TextInputLayout = findViewById(R.id.parkingExitWednesday)
        val enterThursday: TextInputLayout = findViewById(R.id.parkingEnterThursday)
        val exitThursday: TextInputLayout = findViewById(R.id.parkingExitThursday)
        val enterFriday: TextInputLayout = findViewById(R.id.parkingEnterFriday)
        val exitFriday: TextInputLayout = findViewById(R.id.parkingExitFriday)
        val enterSaturday: TextInputLayout = findViewById(R.id.parkingEnterSaturday)
        val exitSaturday: TextInputLayout = findViewById(R.id.parkingExitSaturday)
        val enterSunday: TextInputLayout = findViewById(R.id.parkingEnterSunday)
        val exitSunday: TextInputLayout = findViewById(R.id.parkingExitSunday)




        val buttonRegister: Button = findViewById(R.id.parkingRegister)
        buttonRegister.setOnClickListener {
           val openHoursList = listOf( OpenHoursInput(opening = Input.optional(enterMonday.getEditText()?.getText().toString()),
                                            closing = Input.optional(exitMonday.getEditText()?.getText().toString())),
                                        OpenHoursInput(opening = Input.optional(enterTuesday.getEditText()?.getText().toString()),
                                            closing = Input.optional(exitTuesday.getEditText()?.getText().toString())),
                                        OpenHoursInput(opening = Input.optional(enterWednesday.getEditText()?.getText().toString()),
                                            closing = Input.optional(exitWednesday.getEditText()?.getText().toString())),
                                        OpenHoursInput(opening = Input.optional(enterThursday.getEditText()?.getText().toString()),
                                            closing = Input.optional(exitThursday.getEditText()?.getText().toString())),
                                        OpenHoursInput(opening = Input.optional(enterFriday.getEditText()?.getText().toString()),
                                            closing = Input.optional(exitFriday.getEditText()?.getText().toString())),
                                        OpenHoursInput(opening = Input.optional(enterSaturday.getEditText()?.getText().toString()),
                                            closing = Input.optional(exitSaturday.getEditText()?.getText().toString())),
                                        OpenHoursInput(opening = Input.optional(enterSunday.getEditText()?.getText().toString()),
                                            closing = Input.optional(exitSunday.getEditText()?.getText().toString()))
                                        )
            val newParking = ParkingInputLoc( name = name.getEditText()?.getText().toString(),
                                                address = Input.optional(address.getEditText()?.getText().toString()),
                                                pricePerMinute = price.getEditText()?.getText().toString().toInt(),
                                                totalSpaces = space.getEditText()?.getText().toString().toInt(),
                                                usedSpaces = 0,
                                                latitude = savedLatitude,
                                                longitude = savedLongitude,
                                                openHours = openHoursList,
                                                idplu = parkingLotUserID!!
            )

            lifecycleScope.launchWhenResumed {
                //val client= UserClienAuthInput(name = 'pzambi',email = 'perro@gmail.com' , age =  9,phoneNumber = phone.getEditText()?.getText().toString().toInt(),password = password.getEditText()?.getText().toString())
                Log.d("ParkingList", "${newParking}")
                try {
                    val response = apolloClient.mutate(PostParkingMutation( newParking )).await()

                    val intent = Intent(applicationContext, MapsParkUserActivity::class.java)
                    startActivity(intent)
                }
                catch (e: ApolloHttpException){

                    Toast.makeText(applicationContext, "Error en registro", Toast.LENGTH_LONG).show()
                }
            }
        }





    }
}