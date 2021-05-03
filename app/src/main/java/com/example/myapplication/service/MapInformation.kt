package com.example.myapplication.service

import com.apollographql.apollo.coroutines.await
import com.example.myapplication.GetAllClientUsersQuery
import com.example.myapplication.GetAllParkingLotUsersQuery
import com.example.myapplication.GetParkinglotOwnersQuery
import com.example.myapplication.GetParkingsQuery
import com.example.myapplication.dataAccess.apolloClient
import kotlinx.coroutines.runBlocking

class MapInformation {
    companion object{
        fun GetAllParkingsByOwnerId( id : Int? ): List<GetParkingsQuery.Par_getParking?>?{
            if( id == null ) return null
            var ret = mutableListOf<GetParkingsQuery.Par_getParking?>()
            runBlocking {
                val owners = apolloClient.query(GetParkinglotOwnersQuery()).await()
                val allParkings = apolloClient.query( GetParkingsQuery() ).await()
                var includedIds = mutableListOf<Int>()
                for( owner in owners.data?.plu_getAllParkinglot!!){
                    if( owner == null ) continue
                    if( owner.parkinglotuser!!.id == id ) includedIds.add( owner.parkingid )
                }
                for( parking in allParkings.data?.par_getParkings!! ){
                    if( parking == null ) continue
                    if( includedIds.contains( parking.id.toInt() ) ) ret.add( parking )
                }
            }
            return ret
        }
    }
}