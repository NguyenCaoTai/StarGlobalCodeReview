package com.example.myapplication.data

import retrofit2.Retrofit

object ServiceFactory {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/api/0.4/")
        .build()


    fun getService(): Service = retrofit.create(Service::class.java)
}