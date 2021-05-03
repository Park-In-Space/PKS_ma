package com.example.myapplication.service

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.GetAllClientUsersQuery
import com.example.myapplication.GetAllParkingLotUsersQuery
import com.example.myapplication.LoginMutation
import com.example.myapplication.dataAccess.apolloClient
import com.example.myapplication.type.ParkinglotuserAuthInput
import kotlinx.coroutines.runBlocking

class UserLogin {
    companion object {
        fun userType(email: String?): String? {
            if( email == null ) return null
            var ret: String? = null
            runBlocking {
                val clients = apolloClient.query(GetAllClientUsersQuery()).await()
                val parkingLotUsers = apolloClient.query(GetAllParkingLotUsersQuery()).await()
                for (client in clients.data?.clu_getAllUsers!!) {
                    if (client == null) continue
                    if (client.email.equals(email)) ret = "client"
                }
                for (parkingLotUser in parkingLotUsers.data?.plu_getAllParkinglotuser!!) {
                    if (parkingLotUser == null) continue
                    if (parkingLotUser.email.equals(email)) ret = "parking"
                }
            }
            return ret
        }

        fun getClientByEmail(email: String?): GetAllClientUsersQuery.Clu_getAllUser? {
            if( email == null ) return null
            var ret: GetAllClientUsersQuery.Clu_getAllUser? = null
            runBlocking {
                val clients = apolloClient.query(GetAllClientUsersQuery()).await()
                for (client in clients.data?.clu_getAllUsers!!) {
                    if (client == null) continue
                    if (client.email.equals(email)) ret = client
                }
            }
            return ret
        }

        fun getParkingLotUserByEmail(email: String?): GetAllParkingLotUsersQuery.Plu_getAllParkinglotuser? {
            if( email == null ) return null
            var ret: GetAllParkingLotUsersQuery.Plu_getAllParkinglotuser? = null
            runBlocking {
                val parkingLotUsers = apolloClient.query(GetAllParkingLotUsersQuery()).await()
                for (parkingLotUser in parkingLotUsers.data?.plu_getAllParkinglotuser!!) {
                    if (parkingLotUser == null) continue
                    if (parkingLotUser.email.equals(email)) ret = parkingLotUser
                }
            }
            return ret
        }
    }
}