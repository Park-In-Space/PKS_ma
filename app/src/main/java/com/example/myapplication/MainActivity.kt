package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.dataAccess.apolloClient
import com.example.myapplication.service.MapInformation
import com.example.myapplication.service.UserLogin
import com.example.myapplication.type.ParkinglotuserAuthInput
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var  rotateAnimation: Animation
    private lateinit var  logoButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logoButton = findViewById(R.id.logo)

        rotateAnimation()

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

        val buttonSignUpPark: Button = findViewById(R.id.buttonSignUpPark)
        buttonSignUpPark.setOnClickListener {
            val intent = Intent(this, RegistrationParkUser::class.java)
            startActivity(intent)
        }

        logoButton.setOnClickListener {
            rotateAnimation()
        }

    }

    fun rotateAnimation(){
        rotateAnimation =  AnimationUtils.loadAnimation(this, R.anim.rotate_logo)
        rotateAnimation.setDuration(5000);
        logoButton.startAnimation(rotateAnimation)

    }


}