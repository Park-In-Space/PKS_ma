package com.example.myapplication.dataAccess

import com.apollographql.apollo.ApolloClient

val apolloClient = ApolloClient.builder()
        .serverUrl("http://3.142.246.39:4000/graphql")
        .build()

class Apollo {

}