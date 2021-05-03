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
            val user = ParkinglotuserAuthInput(email = Input.optional("aaa"),password = "a")
            val response = apolloClient.mutate(LoginMutation(email = "verstappen@redbull.com",password = "123456")).await()
            Log.d("ParkingList", "LOGIN ${response?.data}")
            if( response.data?.ath_login != null ){
                val email = response.data!!.ath_login?.email
                val type = UserLogin.userType( email )
                Log.d("ParkingList", "TYPE $type")
                if( type != null ){
                    if( type == "client" ){
                        val clientUser = UserLogin.getClientByEmail( email )
                        Log.d("ParkingList", "CLIENT $clientUser")
                    }else if( type == "parking" ){
                        val clientUser = UserLogin.getParkingLotUserByEmail( email )
                        Log.d("ParkingList", "PLU $clientUser")
                    }
                }
            }
        }
    }
}