package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
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
        val phone: TextInputLayout = findViewById(R.id.userPhone)


        val buttonRegister: Button = findViewById(R.id.clientRegister)
        buttonRegister.setOnClickListener {
//            Log.d("ParkingList", "Success ${name.getEditText()?.getText().toString()}")
//            Log.d("ParkingList", "Success ${email.getEditText()?.getText().toString()}")
//            Log.d("ParkingList", "Success ${password.getEditText()?.getText().toString()}")
//            Log.d("ParkingList", "Success ${name.getEditText()?.getText().toString()}")
            val client= UserClienAuthInput(name = name.getEditText()?.getText().toString(),email = email.getEditText()?.getText().toString() , age =  age.getEditText()?.getText().toString().toInt(),phoneNumber = phone.getEditText()?.getText().toString().toInt(),password = password.getEditText()?.getText().toString())

            lifecycleScope.launchWhenResumed {
                               //val client= UserClienAuthInput(name = 'pzambi',email = 'perro@gmail.com' , age =  9,phoneNumber = phone.getEditText()?.getText().toString().toInt(),password = password.getEditText()?.getText().toString())
               try {
                   val response = apolloClient.mutate(PostClientUserMutation(client)).await()
               }
               catch (e: ApolloHttpException){

                   Toast.makeText(applicationContext, "Email already in use", Toast.LENGTH_LONG).show()
               }
            }
        }





    }







}