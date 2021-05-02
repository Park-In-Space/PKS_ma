package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.dataAccess.apolloClient
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
            val response = apolloClient.query(GetAllParkingLotUsersQuery()).await()
            Log.d("ParkingList", "Success ${response?.data}")
            //Log.d("ParkingList", "Success ${response?.data?.par_getParkings?.get(0)?.name}")
        }

    }
}