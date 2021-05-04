package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.drawerlayout.widget.DrawerLayout
import com.apollographql.apollo.coroutines.await

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.dataAccess.*
import com.example.myapplication.service.MapInformation.Companion.GetAllParkingsByOwnerId
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.material.navigation.NavigationView


class MapsParkUserActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer2)

        val toolbar: Toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout2)
        val navView: NavigationView = findViewById(R.id.nav_view2)
        val navController = findNavController(R.id.nav_host_fragment2)

        navView.setupWithNavController(navController)

        val buttonRegister: Button = findViewById(R.id.startActivityButton)
        buttonRegister.setOnClickListener {
            val intent = Intent(this, MapsParkingActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer2, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(applicationContext, "Sesión cerrada correctamente", Toast.LENGTH_LONG).show()
        clientID = ""
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)

        return super.onOptionsItemSelected(item)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private val LOCATION_PERMISSION_REQUEST = 1


    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
        else
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                mMap.isMyLocationEnabled = true
            }
            else {
                Toast.makeText(this, "User has not granted location access permission", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

        getLocationAccess()
        lifecycleScope.launchWhenResumed {
            val response = GetAllParkingsByOwnerId(parkingLotUserID)
            //mMap.addMarker(MarkerOptions().position(Museo).title(response?.data?.par_getParkings?.get(0)?.name))
            for( parking in response!! ){
                if( parking == null || parking.idLocation == null ) continue
                val locationResponse = apolloClient.query(GetLocationByIdQuery( parking.idLocation )).await()
                if( locationResponse?.data?.loc_location == null ) continue
                val Parking = LatLng(locationResponse?.data?.loc_location?.latitude!! ,locationResponse?.data?.loc_location?.longitude!!)
                val markerBitmap = ResourcesCompat.getDrawable(resources, R.drawable.ic_pin, null)?.toBitmap()
                val icon = BitmapDescriptorFactory.fromBitmap(markerBitmap)
                mMap.addMarker(MarkerOptions()
                    .position(Parking)
                    .title(parking.name)
                    .snippet(parking.address)
                    .icon(icon)
                )
                Log.d("ParkingList", "Location ${locationResponse?.data?.loc_location?.latitude} ${locationResponse?.data?.loc_location?.longitude}")
            }
            //Log.d("ParkingList", "Success ${response?.data?.par_getParkings?.get(0)?.name}")
        }


        //val miPosicion =  getLocationAccess()

        //mMap.addMarker(MarkerOptions().position(miPosicion).title("Mi posicion"))
        val Bogota = LatLng(4.60971, -74.08175)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Bogota))
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(Bogota,10f))
        googleMap.setOnInfoWindowClickListener(this)

    }

    override fun onInfoWindowClick(marker: Marker) {

        lifecycleScope.launchWhenResumed {
            val response = apolloClient.query(GetParkingsQuery()).await()
            //mMap.addMarker(MarkerOptions().position(Museo).title(response?.data?.par_getParkings?.get(0)?.name))
            for (parking in response?.data?.par_getParkings!!) {
                if (parking == null || parking.idLocation == null) continue
                if (marker.title == parking.name && marker.snippet == parking.address) {
                    parkingId = parking.id.toInt()
                    break
                }
                //Log.d("ParkingList", "Location ${locationResponse?.data?.loc_location?.latitude} ${locationResponse?.data?.loc_location?.longitude}")
            }

            val intent = Intent(applicationContext, ParkingViewUserPark::class.java)
            startActivity(intent)
            //Log.d("ParkingList", "Success ${response?.data?.par_getParkings?.get(0)?.name}")
        }
    }
}