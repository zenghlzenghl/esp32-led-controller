package com.example.ledcontroller.network

import com.example.ledcontroller.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("/api/status")
    suspend fun getStatus(): Response<LedState>

    @POST("/api/on")
    suspend fun turnOn(): Response<ApiResponse>

    @POST("/api/off")
    suspend fun turnOff(): Response<ApiResponse>

    @POST("/api/color")
    suspend fun setColor(@Body color: ColorRequest): Response<ApiResponse>

    @POST("/api/brightness")
    suspend fun setBrightness(@Body brightness: BrightnessRequest): Response<ApiResponse>

    @POST("/api/mode")
    suspend fun setMode(@Body mode: ModeRequest): Response<ApiResponse>
}