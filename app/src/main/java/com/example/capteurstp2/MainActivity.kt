package com.example.capteurstp2

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maindisplay)
        //setSupportActionBar(findViewById(R.id.my_toolbar))

        val button = findViewById<Button>(R.id.lightshowButton)
        val dButton = findViewById<Button>(R.id.directionButton)
        val sButton = findViewById<Button>(R.id.shakeButton)
        val pButton = findViewById<Button>(R.id.proximityButton)
        val gButton = findViewById<Button>(R.id.geoButton)
        val cButton = findViewById<Button>(R.id.displayButton)
        cButton.setOnClickListener(buttonListener)
        gButton.setOnClickListener(buttonListener)
        pButton.setOnClickListener(buttonListener)
        dButton.setOnClickListener(buttonListener)
        button.setOnClickListener(buttonListener)
        sButton.setOnClickListener(buttonListener)
    }

    val buttonListener = View.OnClickListener { view ->
        when(view.id){
            R.id.lightshowButton -> switchActivity(LightShowActivity::class.java)
            R.id.directionButton -> switchActivity(DirectionActivity::class.java)
            R.id.shakeButton -> switchActivity(ShakeActivity::class.java)
            R.id.proximityButton -> switchActivity(ProximityActivity::class.java)
            R.id.geoButton -> switchActivity(GeolocalisationActivity::class.java)
            R.id.displayButton -> switchActivity(CapteursActivity::class.java)
        }
    }

    fun switchActivity(activityName: Class<out AppCompatActivity>){
        val intent = Intent(this,activityName)
        startActivity(intent)
    }

    override fun onSensorChanged(event: SensorEvent?) {
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}