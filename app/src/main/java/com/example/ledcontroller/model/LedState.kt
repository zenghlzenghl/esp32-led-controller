package com.example.ledcontroller.model

import com.google.gson.annotations.SerializedName

data class LedState(
    @SerializedName("state") val isOn: Boolean = false,
    @SerializedName("r") val red: Int = 255,
    @SerializedName("g") val green: Int = 255,
    @SerializedName("b") val blue: Int = 255,
    @SerializedName("brightness") val brightness: Int = 100,
    @SerializedName("mode") val mode: Int = 0
) {
    override fun toString(): String {
        return "LedState(isOn=$isOn, rgb=($red,$green,$blue), brightness=$brightness%, mode=$mode)"
    }
}

data class ApiResponse(
    val status: String = "",
    val state: String? = null,
    val rgb: List<Int>? = null,
    val stateEnabled: Boolean? = null
) {
    override fun toString(): String {
        return "ApiResponse(status=$status, state=$state, rgb=$rgb, enabled=$stateEnabled)"
    }
}

data class ColorRequest(
    @SerializedName("r") val r: Int,
    @SerializedName("g") val g: Int,
    @SerializedName("b") val b: Int
) {
    fun toJsonString(): String {
        // 手动构建紧凑的JSON字符串（无空格），兼容ESP32的sscanf
        return "{\"r\":$r,\"g\":$g,\"b\":$b}"
    }
    
    override fun toString(): String {
        return toJsonString()
    }
}
data class BrightnessRequest(val brightness: Int)
data class ModeRequest(val mode: Int)