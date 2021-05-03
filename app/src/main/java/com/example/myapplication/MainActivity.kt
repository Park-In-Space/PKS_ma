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
import com.example.myapplication.service.MapInformation
import com.example.myapplication.service.UserLogin
import com.example.myapplication.type.ParkinglotuserAuthInput
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val buttonSignUp: Button = findViewById(R.id.buttonSignUpClient)
        buttonSignUp.setOnClickListener {
            val intent = Intent(this, RegistrationClient::class.java)
            startActivity(intent)
        }

        lifecycleScope.launchWhenResumed {
            val output = MapInformation.GetAllParkingsByOwnerId( 9 )
            Log.d("ParkingList", "Success $output")
        }
    }
}