package com.example.ledcontroller

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.ledcontroller.model.LedState
import com.example.ledcontroller.model.ModeRequest
import com.example.ledcontroller.network.LedApiClient
import com.example.ledcontroller.ui.LedPreviewView
import com.example.ledcontroller.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentState: LedState = LedState()
    private var isPolling = false

    companion object {
        const val MODE_NAMES = arrayOf("Static", "Breathing", "Blinking", "Rainbow", "Police")
        const val DEFAULT_IP = "10.127.64.170"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUi()
        startPolling()
    }

    private fun setupUi() {
        binding.editTextIp.setText(DEFAULT_IP)
        
        binding.buttonConnect.setOnClickListener {
            val ip = binding.editTextIp.text.toString().trim()
            if (ip.isNotEmpty()) {
                LedApiClient.setBaseUrl(ip)
                Toast.makeText(this, "Connecting to $ip...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.switchPower.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    if (isChecked) {
                        LedApiClient.getApiService().turnOn()
                    } else {
                        LedApiClient.getApiService().turnOff()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        binding.seekBarBrightness.setOnSeekBarChangeListener(object : 
            android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.textBrightnessValue.text = "$progress%"
                    sendBrightness(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })

        setupColorButtons()
        setupModeSpinner()
    }

    private fun setupColorButtons() {
        val colorButtons = mapOf(
            binding.btnRed to Triple(255, 0, 0),
            binding.btnGreen to Triple(0, 255, 0),
            binding.btnBlue to Triple(0, 0, 255),
            binding.btnWarm to Triple(255, 200, 100),
            binding.btnCyan to Triple(0, 255, 255),
            binding.btnPurple to Triple(180, 0, 255),
            binding.btnOrange to Triple(255, 140, 0),
            binding.btnPink to Triple(255, 50, 150),
            binding.btnYellow to Triple(255, 255, 0),
            binding.btnWhite to Triple(255, 255, 255)
        )

        colorButtons.forEach { (button, rgb) ->
            button.setOnClickListener {
                sendColor(rgb.first, rgb.second, rgb.third)
                sendMode(0)
            }
        }
    }

    private fun setupModeSpinner() {
        val adapter = ArrayAdapter(this, 
            android.R.layout.simple_spinner_item, MODE_NAMES)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMode.adapter = adapter

        binding.spinnerMode.onItemSelectedListener = object : 
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sendMode(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun sendColor(r: Int, g: Int, b: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                LedApiClient.getApiService().setColor(
                    com.example.ledcontroller.model.ColorRequest(r, g, b)
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendBrightness(value: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                LedApiClient.getApiService().setBrightness(
                    com.example.ledcontroller.model.BrightnessRequest(value)
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendMode(mode: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                LedApiClient.getApiService().setMode(ModeRequest(mode))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun startPolling() {
        isPolling = true
        lifecycleScope.launch(Dispatchers.IO) {
            while (isPolling) {
                try {
                    val response = LedApiClient.getApiService().getStatus()
                    if (response.isSuccessful && response.body() != null) {
                        currentState = response.body()!!
                        withContext(Dispatchers.Main) {
                            updateUi(currentState)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                delay(2000)
            }
        }
    }

    private fun updateUi(state: LedState) {
        binding.switchPower.isChecked = state.isOn
        binding.textPowerStatus.text = if (state.isOn) "ON" else "OFF"
        binding.textPowerStatus.setTextColor(
            if (state.isOn) 
                ContextCompat.getColor(this, R.color.green) 
            else 
                ContextCompat.getColor(this, R.color.gray)
        )

        binding.seekBarBrightness.progress = state.brightness
        binding.textBrightnessValue.text = "${state.brightness}%"

        binding.spinnerMode.setSelection(state.mode.coerceIn(0, MODE_NAMES.size - 1))

        binding.ledPreview.updateState(state)

        binding.textRgbValue.text = "${state.red}, ${state.green}, ${state.blue}"
        binding.textModeValue.text = MODE_NAMES[state.mode.coerceIn(0, MODE_NAMES.size - 1)]
        binding.textStateValue.text = if (state.isOn) "ON" else "OFF"

        val connectionInfo = "IP: ${LedApiClient.getCurrentBaseUrl().replace("http://", "")}"
        binding.textConnectionInfo.text = connectionInfo
    }

    override fun onDestroy() {
        super.onDestroy()
        isPolling = false
    }
}