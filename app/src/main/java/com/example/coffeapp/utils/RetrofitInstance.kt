package com.example.coffeapp.utils

import com.example.coffeapp.data.ApiCoffeeInstance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api : ApiCoffeeInstance by lazy {
        Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCoffeeInstance::class.java)
    }

}