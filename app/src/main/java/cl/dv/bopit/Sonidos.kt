package cl.dv.bopit

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Sonidos : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer
    private lateinit var mediaPlayer3: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sonidos)

        mediaPlayer = MediaPlayer.create(this, R.raw.victory_theme)
        mediaPlayer2 = MediaPlayer.create(this, R.raw.lose_theme)
        mediaPlayer3 = MediaPlayer.create(this, R.raw.relaxe_theme)

        mediaPlayer3.start()

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
            mediaPlayer3.pause()
        }
    }
}