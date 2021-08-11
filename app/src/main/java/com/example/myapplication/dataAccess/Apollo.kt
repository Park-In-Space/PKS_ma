package com.example.myapplication.dataAccess

import com.apollographql.apollo.ApolloClient

val apolloClient = ApolloClient.builder()
        .serverUrl("http://34.72.29.68:80/graphql")
        .build()

class Apollo {

}