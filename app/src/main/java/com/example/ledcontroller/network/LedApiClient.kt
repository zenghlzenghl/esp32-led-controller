package com.example.ledcontroller.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object LedApiClient {

    private var baseUrl: String = "http://192.168.1.100"
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    private var apiService: ApiService? = null

    fun setBaseUrl(url: String) {
        if (!url.startsWith("http://")) {
            baseUrl = "http://$url"
        } else {
            baseUrl = url
        }
        apiService = null
    }

    fun getApiService(): ApiService {
        if (apiService == null) {
            apiService = Retrofit.Builder()
                .baseUrl("$baseUrl/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
        return apiService!!
    }

    fun getCurrentBaseUrl(): String = baseUrl
}