package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.dataAccess.apolloClient
import com.example.myapplication.type.UserClienAuthInput
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.TextInputLayout

class RegistrationClient : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_client)

        val name: TextInputLayout = findViewById(R.id.userName)
        val email: TextInputLayout = findViewById(R.id.userEmail)
        val password: TextInputLayout = findViewById(R.id.userPassword)
        val age: TextInputLayout = findViewById(R.id.userAge)


        //val a = name.getEditText().getText().toString()
        //val cliente= UserClienAuthInput(name = name.get,email = "elbocho@gmail" , age = 9,phoneNumber = 123,password = "123456")

//
//        lifecycleScope.launchWhenResumed {
//            val response = apolloClient.mutate(PostClientUserMutation(cliente)).await()
//            //mMap.addMarker(MarkerOptions().position(Museo).title(response?.data?.par_getParkings?.get(0)?.name))
//            Log.d("ParkingList", "Success ${response?.data}")
//            //Log.d("ParkingList", "Success ${response?.data?.par_getParkings?.get(0)?.name}")
//        }



    }





}