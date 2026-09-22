package com.example.ledcontroller.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.ledcontroller.model.LedState

class LedPreviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var ledColor: Int = Color.WHITE
    private var isOn: Boolean = false
    private var brightness: Float = 100f
    
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    fun updateState(state: LedState) {
        isOn = state.isOn
        brightness = state.brightness / 100f
        ledColor = Color.rgb(state.red, state.green, state.blue)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = minOf(width, height) / 2f * 0.7f

        if (isOn && brightness > 0) {
            paint.style = Paint.Style.FILL
            
            val alphaOuter = (brightness * 40).toInt().coerceIn(0, 255)
            paint.color = Color.argb(alphaOuter, Color.red(ledColor), Color.green(ledColor), Color.blue(ledColor))
            canvas.drawCircle(centerX, centerY, radius * 1.6f, paint)

            val alphaMid = (brightness * 80).toInt().coerceIn(0, 255)
            paint.color = Color.argb(alphaMid, Color.red(ledColor), Color.green(ledColor), Color.blue(ledColor))
            canvas.drawCircle(centerX, centerY, radius * 1.3f, paint)

            val alphaMain = (brightness * 255).toInt().coerceIn(0, 255)
            paint.color = Color.argb(alphaMain, Color.red(ledColor), Color.green(ledColor), Color.blue(ledColor))
            canvas.drawCircle(centerX, centerY, radius, paint)

            val alphaHighlight = (brightness * 120).toInt().coerceIn(0, 255)
            paint.color = Color.argb(alphaHighlight, 255, 255, 255)
            canvas.drawCircle(centerX - radius * 0.25f, centerY - radius * 0.25f, radius * 0.25f, paint)
        } else {
            paint.color = Color.parseColor("#CCCCCC")
            canvas.drawCircle(centerX, centerY, radius, paint)
            
            paint.color = Color.parseColor("#AAAAAA")
            canvas.drawCircle(centerX - radius * 0.25f, centerY - radius * 0.25f, radius * 0.2f, paint)
        }
    }
}