package com.example.capteurstp2

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CapteursActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.capteursdisplay)
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val layoutParent = findViewById<LinearLayout>(R.id.capteurLayout)

        for (sensor in sensorManager.getSensorList(Sensor.TYPE_ALL)) {
            val textView = TextView(this).apply {
                text = sensor.name
                textSize = 10f
                setPadding(16, 8, 16, 8)
            }
            layoutParent.addView(textView)
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null){
            Toast.makeText(this,"Nothing makes sense", Toast.LENGTH_SHORT).show()
        }
    }
}