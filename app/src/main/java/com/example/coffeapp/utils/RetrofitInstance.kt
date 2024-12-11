package com.example.coffeapp.utils

import com.example.coffeapp.data.ApiCoffeInstance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api : ApiCoffeInstance by lazy {
        Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCoffeInstance::class.java)
    }

}