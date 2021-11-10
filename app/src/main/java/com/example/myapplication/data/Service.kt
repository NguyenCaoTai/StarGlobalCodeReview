package com.example.myapplication.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("?randomapi")
    fun random(): Call<ResponseBody>

}