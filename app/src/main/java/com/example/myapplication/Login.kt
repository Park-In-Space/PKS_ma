package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.dataAccess.apolloClient
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.api.Input
import com.example.myapplication.service.UserLogin
import com.example.myapplication.type.ParkinglotuserAuthInput
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.TextInputLayout

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val email: TextInputLayout = findViewById(R.id.loginEmail)
        val password: TextInputLayout = findViewById(R.id.loginPassword)

        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            var emailString = email.getEditText()?.getText().toString()
            var passwordString = password.getEditText()?.getText().toString()
            lifecycleScope.launchWhenResumed {
                //val user = ParkinglotuserAuthInput(email = Input.optional(email= email.getEditText()?.getText().toString(),password = "a"))
                val response = apolloClient.mutate(LoginMutation(email = emailString,password = passwordString)).await()
                Log.d("ParkingList", "LOGIN ${response?.data}")
                if( response.data?.ath_login != null ){
                    val email = response.data!!.ath_login?.email
                    val type = UserLogin.userType( email )
                    Log.d("ParkingList", "TYPE $type")
                    if( type != null ){
                        if( type == "client" ){
                            val clientUser = UserLogin.getClientByEmail( email )
                            Log.d("ParkingList", "CLIENT $clientUser")
                            val intent = Intent(applicationContext, MapsActivity::class.java)
                            startActivity(intent)
                        }else if( type == "parking" ){
                            val clientUser = UserLogin.getParkingLotUserByEmail( email )
                            Log.d("ParkingList", "PLU $clientUser")
                            val intent = Intent(applicationContext, MapsActivity::class.java)
                            startActivity(intent)
                        }
                    }

                }
                else{
                    Log.d("ParkingList", "fallo ")
                    Toast.makeText(applicationContext, "Email o contraseña incorrecta", Toast.LENGTH_LONG).show()
                }
            }


        }

    }
}