package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.dataAccess.apolloClient

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import androidx.lifecycle.lifecycleScope


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
        val i=0
        lifecycleScope.launchWhenResumed {
            val response = apolloClient.query(GetParkingsQuery()).await()
            //mMap.addMarker(MarkerOptions().position(Museo).title(response?.data?.par_getParkings?.get(0)?.name))
            for( parking in response?.data?.par_getParkings!! ){
                if( parking == null || parking.idLocation == null ) continue
                val locationResponse = apolloClient.query(GetLocationByIdQuery( parking.idLocation )).await()
                if( locationResponse?.data?.loc_location == null ) continue
                val Parking = LatLng(locationResponse?.data?.loc_location?.latitude!! ,locationResponse?.data?.loc_location?.longitude!!)
                mMap.addMarker(MarkerOptions().position(Parking).title(parking.name))
                Log.d("ParkingList", "Location ${locationResponse?.data?.loc_location?.latitude} ${locationResponse?.data?.loc_location?.longitude}")
            }
            Log.d("ParkingList", "Success ${response?.data}")
            //Log.d("ParkingList", "Success ${response?.data?.par_getParkings?.get(0)?.name}")
        }
        //val miPosicion =  getLocationAccess()

        //mMap.addMarker(MarkerOptions().position(miPosicion).title("Mi posicion"))
        val Bogota = LatLng(4.60971, -74.08175)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Bogota))
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(Bogota,10f))
    }
}