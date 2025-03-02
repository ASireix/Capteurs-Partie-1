package com.example.capteurstp2

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LightShowActivity : AppCompatActivity(), SensorEventListener {
    private var hasAccelerometer = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lightshowdisplay)
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            hasAccelerometer = true
            val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
            onAccelerometer(event)
        }
    }

    override fun onPause() {
        super.onPause()
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(this)
    }


    fun onAccelerometer(event: SensorEvent?){
        val x = event?.values?.get(0)
        val y = event?.values?.get(1)
        val z = event?.values?.get(2)

        findViewById<TextView>(R.id.x_View).setText("X = $x")
        findViewById<TextView>(R.id.y_View).text = "Y = $y"
        findViewById<TextView>(R.id.z_View).text = "Z = $z"

        val l = findViewById<LinearLayout>(R.id.lightshowLayout)

        val r = ((x!! + 10) / 20 * 255).toInt().coerceIn(0, 255)
        val g = ((y!! + 10) / 20 * 255).toInt().coerceIn(0, 255)
        val b = ((z!! + 10) / 20 * 255).toInt().coerceIn(0, 255)

        l.setBackgroundColor(Color.rgb(r,g,b))
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}