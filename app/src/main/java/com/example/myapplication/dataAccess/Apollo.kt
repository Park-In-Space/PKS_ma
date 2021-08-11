package com.example.myapplication.dataAccess

import com.apollographql.apollo.ApolloClient

val apolloClient = ApolloClient.builder()
        .serverUrl("https://pks-proxy-1-gpvlioxixq-uc.a.run.app/graphql")
        .build()

class Apollo {

}