package cl.dv.bopit

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import kotlin.random.Random

class Juego : AppCompatActivity(), GestureDetector.OnGestureListener, SensorEventListener{

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer
    private lateinit var mediaPlayer3: MediaPlayer

    private lateinit var mDetector: GestureDetectorCompat

    private lateinit var isaac: ImageView

    private lateinit var gesture: TextView

    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null

    private var agitado: Boolean = false

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var puntos: TextView
    private var puntaje = 0

    private val instrucciones = listOf(
        "Desliza!!",
        "Agita!!",
        "Manten precionado!!"
    )

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        mDetector = GestureDetectorCompat(this, this)

        mediaPlayer = MediaPlayer.create(this, R.raw.correct_sound)
        mediaPlayer2 = MediaPlayer.create(this, R.raw.lose_theme)
        mediaPlayer3 = MediaPlayer.create(this, R.raw.relaxe_theme)

        isaac = findViewById(R.id.verificador)

        gesture = findViewById(R.id.gestureText) //Intrucciones
        puntos = findViewById(R.id.puntajeText)

        puntos.text = puntaje.toString()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        mediaPlayer3.start()
        mediaPlayer3.isLooping

        showRandomInstruccion()
    }

    private fun showRandomInstruccion() {
        val randomIndex = (0 until instrucciones.size).random()
        val randomInstruction = instrucciones[randomIndex]
        gesture.text = randomInstruction
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

        if(gesture.text == "Desliza!!")
        {
            gesture.setText(R.string.logro)
            puntaje = puntaje + 1
            puntos.text = puntaje.toString()
            mediaPlayer.start()
            isaac.setImageResource(R.drawable.correcto)
            scheduleRandomInstruction()
        }else
        {
            gesture.setText(R.string.fallo)
            mediaPlayer2.start()
            isaac.setImageResource(R.drawable.fallaste)
        }
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        if(gesture.text == "Manten precionado!!")
        {
            gesture.setText(R.string.logro)
            puntaje = puntaje + 1
            puntos.text = puntaje.toString()
            mediaPlayer.start()
            scheduleRandomInstruction()
            isaac.setImageResource(R.drawable.correcto)
        }else
        {
            gesture.setText(R.string.fallo)
            mediaPlayer2.start()
            isaac.setImageResource(R.drawable.fallaste)
        }
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
            val threshold = 10+.0f

            if (acceleration > threshold) {
                if(gesture.text == "Agita!!")
                {
                    agitado = true
                    gesture.setText(R.string.logro)
                    puntaje = puntaje + 1
                    puntos.text = puntaje.toString()
                    mediaPlayer.start()
                    isaac.setImageResource(R.drawable.correcto)
                    scheduleRandomInstruction()
                }else if(agitado == false)
                {
                    gesture.setText(R.string.fallo)
                    mediaPlayer2.start()
                    isaac.setImageResource(R.drawable.fallaste)
                }
            } else {
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    private fun scheduleRandomInstruction() {
        handler.postDelayed({
            agitado = false
            isaac.setImageResource(R.drawable.neutral)
            showRandomInstruccion()
        }, 3000)
    }
}