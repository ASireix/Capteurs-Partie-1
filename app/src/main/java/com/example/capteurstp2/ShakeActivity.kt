package com.example.capteurstp2

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class ShakeActivity : AppCompatActivity(), SensorEventListener {
    private var lastX: Float = 0.0f
    private var lastY: Float = 0.0f
    private var lastZ: Float = 0.0f

    private val shakeTreshold: Float = 1f  // Seuil de secousse
    private var shakeBuildup: Float = 0f   // Niveau d'accumulation
    private val shakeDecay: Float = 2f   // Vitesse de diminution
    private val shakeTrigger: Float = 50f  // Niveau pour déclencher un shake

    private var lastShakeTime : Long = 0
    private val shakeCooldown : Long = 1000// en ms

    private var isTorchOn : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shakedisplay)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        acc?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onResume() {
        super.onResume()
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        acc?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            // Calcul de la variation de l'accélération
            val deltaX = Math.abs(x - lastX)
            val deltaY = Math.abs(y - lastY)
            val deltaZ = Math.abs(z - lastZ)

            val currentTime = System.currentTimeMillis()
            val isInCooldown = currentTime - lastShakeTime <= shakeCooldown
            // Vérifier si le shake dépasse le seuil
            if ((deltaX > shakeTreshold || deltaY > shakeTreshold || deltaZ > shakeTreshold) && !isInCooldown) {
                shakeBuildup += deltaX + deltaY + deltaZ
            }

            // Appliquer le shake decay (diminuer progressivement)
            shakeBuildup = Math.max(0f, shakeBuildup - shakeDecay)


            // Détection du shake
            if (shakeBuildup >= shakeTrigger && !isInCooldown) {
                shakeDetected()
                lastShakeTime = currentTime
                shakeBuildup = 0f  // Réinitialisation après détection
            }

            // Mettre à jour les dernières valeurs
            lastX = x
            lastY = y
            lastZ = z
        }
        Log.d("SHAKY", shakeBuildup.toString())
    }

    private fun shakeDetected() {
        isTorchOn = !isTorchOn
        if (isTorchOn){
            findViewById<ConstraintLayout>(R.id.shakelayout).setBackgroundColor(Color.RED)
        }else{
            findViewById<ConstraintLayout>(R.id.shakelayout).setBackgroundColor(Color.BLACK)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
