package com.example.capteurstp2

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class DirectionActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var arrow_north : ImageView
    private lateinit var arrow_south : ImageView
    private lateinit var arrow_east : ImageView
    private lateinit var arrow_west : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.directiondisplay)

        arrow_north = findViewById(R.id.arrow_n)
        arrow_south = findViewById(R.id.arrow_s)
        arrow_east = findViewById(R.id.arrow_e)
        arrow_west = findViewById(R.id.arrow_w)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
            onAcceleratorChanged(event)
        }
    }

    override fun onResume() {
        super.onResume()
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(this)
    }

    private fun onAcceleratorChanged(event: SensorEvent?){
        val x = event?.values?.get(0)
        val y = event?.values?.get(1)

        val direction = when {
            x!! < -3 -> "East"
            x > 3 -> "West"
            y!! < -3 -> "South"
            y > 3 -> "North"
            else -> "Stable"
        }
        updateDirection(direction)
    }

    private fun updateDirection(dir : String){
        when {
            dir == "West" -> {
                arrow_west.setColorFilter(Color.RED)
                arrow_east.setColorFilter(Color.BLACK)
                arrow_south.setColorFilter(Color.BLACK)
                arrow_north.setColorFilter(Color.BLACK)
            }
            dir == "East" -> {
                arrow_west.setColorFilter(Color.BLACK)
                arrow_east.setColorFilter(Color.RED)
                arrow_south.setColorFilter(Color.BLACK)
                arrow_north.setColorFilter(Color.BLACK)
            }
            dir == "North" -> {
                arrow_west.setColorFilter(Color.BLACK)
                arrow_east.setColorFilter(Color.BLACK)
                arrow_south.setColorFilter(Color.BLACK)
                arrow_north.setColorFilter(Color.RED)
            }
            dir == "South" -> {
                arrow_west.setColorFilter(Color.BLACK)
                arrow_east.setColorFilter(Color.BLACK)
                arrow_south.setColorFilter(Color.RED)
                arrow_north.setColorFilter(Color.BLACK)
            }
            else -> {arrow_west.setColorFilter(Color.BLACK)
                    arrow_east.setColorFilter(Color.BLACK)
                    arrow_south.setColorFilter(Color.RED)
                    arrow_north.setColorFilter(Color.BLACK)}
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}