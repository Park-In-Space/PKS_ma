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
import com.example.myapplication.dataAccess.apolloClient
import com.example.myapplication.type.ParkinglotuserAuthInput
import com.example.myapplication.type.UserClienAuthInput
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.TextInputLayout

class RegistrationParkUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_park_user)

        val username: TextInputLayout = findViewById(R.id.userParkUsername)
        val name: TextInputLayout = findViewById(R.id.userParkName)
        val email: TextInputLayout = findViewById(R.id.userParkEmail)
        val password: TextInputLayout = findViewById(R.id.userParkPassword)
        val phone: TextInputLayout = findViewById(R.id.userParkPhone)


        val buttonRegister: Button = findViewById(R.id.userRegister)
        buttonRegister.setOnClickListener {
//            Log.d("ParkingList", "Success ${name.getEditText()?.getText().toString()}")
//            Log.d("ParkingList", "Success ${email.getEditText()?.getText().toString()}")
//            Log.d("ParkingList", "Success ${password.getEditText()?.getText().toString()}")
//            Log.d("ParkingList", "Success ${name.getEditText()?.getText().toString()}")

            //val user= ParkinglotuserAuthInput(username = Input.optional("asdas"),name= Input.optional("ALEJITA"), email = Input.optional("aza1234@gmail.com"), password = "1234567", phone =Input.optional("123123123"))
            val user= ParkinglotuserAuthInput(username = (Input.optional(username.getEditText()?.getText().toString())),name= Input.optional(name.getEditText()?.getText().toString()) ,email = (Input.optional(email.getEditText()?.getText().toString())), password = password.getEditText()?.getText().toString(), phone = (Input.optional(phone.getEditText()?.getText().toString())))
            Log.d("ParkingList", "email ${email.getEditText()?.getText().toString()}")
            Log.d("ParkingList", "usuario ${user}")

            lifecycleScope.launchWhenResumed {
                //val client= UserClienAuthInput(name = 'pzambi',email = 'perro@gmail.com' , age =  9,phoneNumber = phone.getEditText()?.getText().toString().toInt(),password = password.getEditText()?.getText().toString())
                try {
                    val response = apolloClient.mutate(PostParkingLotUserMutation(user)).await()
                    Log.d("ParkingList", "usuario ${response.data}")
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)

                }
                catch (e: ApolloHttpException){

                    Toast.makeText(applicationContext, "Email already in use", Toast.LENGTH_LONG).show()
                }
            }
        }





    }







}