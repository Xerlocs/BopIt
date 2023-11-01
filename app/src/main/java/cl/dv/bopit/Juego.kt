package cl.dv.bopit

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat

class Juego : AppCompatActivity(), GestureDetector.OnGestureListener, SensorEventListener{

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer
    private lateinit var mediaPlayer3: MediaPlayer

    private lateinit var mDetector: GestureDetectorCompat

    private lateinit var gesture: TextView
    private lateinit var square: TextView

    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        mDetector = GestureDetectorCompat(this, this)

        mediaPlayer = MediaPlayer.create(this, R.raw.victory_theme)
        mediaPlayer2 = MediaPlayer.create(this, R.raw.lose_theme)
        mediaPlayer3 = MediaPlayer.create(this, R.raw.relaxe_theme)

        gesture = findViewById(R.id.gestureText)
        square = findViewById(R.id.tv_square)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        mediaPlayer3.start()
        mediaPlayer3.isLooping

        val victoria = findViewById<Button>(R.id.victoriaButton)
        victoria.setOnClickListener{
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
            }
        }

        val derrota = findViewById<Button>(R.id.perderButton)
        derrota.setOnClickListener{
            if(!mediaPlayer2.isPlaying){
                mediaPlayer2.start()
            }
        }

        val fondo = findViewById<Button>(R.id.fondoButton)
        fondo.setOnClickListener{
            if(!mediaPlayer3.isPlaying){
                mediaPlayer3.start()
            }else{
                mediaPlayer3.pause()
            }
        }


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onPause(){
        super.onPause()
        if(mediaPlayer.isPlaying || mediaPlayer2.isPlaying || mediaPlayer3.isPlaying){
            mediaPlayer.pause()
            mediaPlayer2.pause()
            mediaPlayer3.pause()
        }
        sensorManager?.unregisterListener(this)
    }

    override fun onResume(){
        super.onResume()
        if(!mediaPlayer3.isPlaying){
            mediaPlayer3.start()
        }
        sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }
    override fun onDown(event: MotionEvent): Boolean {
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        gesture.setText(R.string.desliza)
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        gesture.setText(R.string.longpress)
    }

    override fun onScroll(
        event1: MotionEvent,
        event2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return true
    }

    override fun onShowPress(event: MotionEvent) {
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        return true
    }

    override fun onSensorChanged(p0: SensorEvent) {
        if (p0.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = p0.values[0]
            val y = p0.values[1]
            val z = p0.values[2]
            val acceleration = Math.sqrt(x * x + y * y + z * z.toDouble()).toFloat()

            // You can adjust the acceleration threshold based on your needs
            val threshold = 10.0f

            if (acceleration > threshold) {
                // Change the background color to black
                square.setBackgroundColor(Color.RED)
            } else {
                // Change the background color to white
                square.setBackgroundColor(Color.BLUE)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }
}