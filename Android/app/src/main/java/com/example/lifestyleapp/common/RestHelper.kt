package com.example.lifestyleapp.common

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//todo implement
/**
 * example usage
 * weather = buildRestService().getWeather(
 * q = city,
 * appid = "a742f92606870e1ee06b22a9502b644d"
 * )
 */
fun buildRestService(): RestService {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
    val client = OkHttpClient
        .Builder()
        .addInterceptor(logging)
        .build()
    return Retrofit
        .Builder()
        .baseUrl("https://api.openweathermap.org")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(RestService::class.java)
}




