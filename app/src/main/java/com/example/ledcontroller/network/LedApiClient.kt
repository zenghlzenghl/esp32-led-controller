package com.example.ledcontroller.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object LedApiClient {

    private var baseUrl: String = "http://10.127.64.170"
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private var apiService: ApiService? = null

    fun setBaseUrl(url: String) {
        if (!url.startsWith("http://")) {
            baseUrl = "http://$url"
        } else {
            baseUrl = url
        }
        apiService = null
        android.util.Log.d("LedApiClient", "Base URL updated to: $baseUrl")
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