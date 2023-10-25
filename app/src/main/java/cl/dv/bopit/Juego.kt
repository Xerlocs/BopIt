package cl.dv.bopit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat

class Juego : AppCompatActivity(), GestureDetector.OnGestureListener{

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer
    private lateinit var mediaPlayer3: MediaPlayer

    private lateinit var mDetector: GestureDetectorCompat

    private lateinit var gesture: TextView
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        mDetector = GestureDetectorCompat(this, this)

        mediaPlayer = MediaPlayer.create(this, R.raw.victory_theme)
        mediaPlayer2 = MediaPlayer.create(this, R.raw.lose_theme)
        mediaPlayer3 = MediaPlayer.create(this, R.raw.relaxe_theme)

        gesture = findViewById(R.id.gestureText)

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
    }

    override fun onResume(){
        super.onResume()
        if(!mediaPlayer3.isPlaying){
            mediaPlayer3.start()
        }
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
}