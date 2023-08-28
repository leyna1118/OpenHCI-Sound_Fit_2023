package com.example.measuredata

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.measuredata.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Math.round

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private val viewModel: MainViewModel by viewModels()

    private lateinit var mData: TextView

    private var sensorManager: SensorManager? = null
    private var sensors: List<Sensor>? = null
    private var sensor: Sensor? = null
    private var listener: SensorEventListener? = null

    val BLYNK_AUTH_TOKEN = "bAjlTM2rCNTsq-fuv13RiNNWLIs4T2nI"
    var heartrate = 0
    var accelerometer = 0.00
    var sound = 0.00

    val client = OkHttpClient()

    private lateinit var soundMeterValueTextView: TextView;
    private lateinit var soundInstrument: SoundInstrument;
    private val requestCodeAudioRecord = 1001
    private var handler: Handler? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ToDo: Move Hand
        mData = findViewById(R.id.data)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensors = sensorManager?.getSensorList(Sensor.TYPE_ALL)
        if (sensors.isNullOrEmpty()) {
            Toast.makeText(this, "No sensors returned from getSensorList", Toast.LENGTH_SHORT).show()
            Log.wtf(TAG, "No sensors returned from getSensorList")
        } else {
            sensors?.forEachIndexed { index, sensor ->
                Log.wtf(TAG, "Found sensor $index $sensor")
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                Log.i(TAG, "Body sensors permission granted")
                lifecycleScope.launchWhenStarted {
                    viewModel.measureHeartRate()
                }
            } else {
                Log.i(TAG, "Body sensors permission not granted")
            }
        }

        // Bind viewmodel state to the UI.
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                updateViewVisibility(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.heartRateAvailable.collect {
                binding.statusText.text = getString(R.string.measure_status, it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.heartRateBpm.collect {
                binding.lastMeasuredValue.text = String.format("%.1f", it)
                heartrate = it.toInt()
            }
        }

        initViews()
        checkPermission()

        // Using GlobalScope to launch background task
        GlobalScope.launch {
            while (isActive) {
                updateData()
                delay(200)  // Wait for 500 ms
            }
        }
    }

    //ToDo: Translate Data to App
    // Using OkHttpClient to execute network operations
    fun updateData() {
        val currentUpdateUrl = "https://blynk.cloud/external/api/batch/update?token=$BLYNK_AUTH_TOKEN&v6=$heartrate&v7=$accelerometer&v8=$sound"
        val request = Request.Builder().url(currentUpdateUrl).build()

        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                println(response.body?.string())
            } else {
                println("Failed to retrieve content. HTTP Status Code: ${response.code}")
            }
        }
    }

    //ToDo: Sound
    fun initViews() {
        soundMeterValueTextView = findViewById(R.id.soundMeterValueTextView)
    }
    fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                this,
                getString(R.string.permission_insufficient),
                Toast.LENGTH_SHORT
            ).show()

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                requestCodeAudioRecord
            )
        } else {
            startListening()
        }
    }

    fun startListening() {
        soundInstrument = SoundInstrument()
        soundInstrument.start()

        handler = Handler()

        val runnable: Runnable = object : Runnable {
            override fun run() {
//                val amplitude = soundInstrument.getAmplitude()
                var amplitude = soundInstrument.getAmplitude()
                amplitude /= 100
                soundMeterValueTextView.setText(amplitude.toString())
                handler!!.postDelayed(this, 500)
                sound = amplitude.toDouble()
            }
        }
        handler!!.postDelayed(runnable, 500)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == requestCodeAudioRecord){
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    getString(R.string.permission_granted),
                    Toast.LENGTH_SHORT
                ).show()

                startListening()
            }
        }
    }

    //ToDo: Active
    override fun onStart() {
        super.onStart()
        permissionLauncher.launch(android.Manifest.permission.BODY_SENSORS)
        registerSensor()
    }

    override fun onStop() {
        super.onStop()
        sensorManager?.unregisterListener(listener)
    }

    private fun updateViewVisibility(uiState: UiState) {
        binding.progress.apply { isVisible = uiState is UiState.Startup }
        binding.brokenHeart.apply { isVisible = uiState is UiState.HeartRateNotAvailable }
        binding.notAvailable.apply { isVisible = uiState is UiState.HeartRateNotAvailable }
        binding.statusText.apply { isVisible = uiState is UiState.HeartRateAvailable }
        binding.lastMeasuredLabel.apply { isVisible = uiState is UiState.HeartRateAvailable }
        binding.lastMeasuredValue.apply { isVisible = uiState is UiState.HeartRateAvailable }
        binding.heart.apply { isVisible = uiState is UiState.HeartRateAvailable }
    }

    private fun registerSensor() {
        sensorManager = sensorManager ?: getSystemService(SENSOR_SERVICE) as SensorManager
        sensors = sensorManager?.getSensorList(Sensor.TYPE_ACCELEROMETER)
        sensor = sensors?.firstOrNull()

        listener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Not handling accuracy events
            }

            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                        val x = it.values[0]
                        val y = it.values[1]
                        val z = it.values[2]
                        var magnitude = Math.sqrt((x * x + y * y + z * z).toDouble())
                        val msg = "x: $x\ny: $y\nz: $z"
                        Log.d(TAG, "Magnitude: $magnitude")
                        magnitude = Math.abs(magnitude - 9.8)
                        accelerometer = magnitude
                        mData.text = magnitude.toString()
                    }
                }
            }
        }
        sensorManager?.registerListener(listener, sensor, 100000)
    }
}
