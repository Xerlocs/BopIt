package cl.dv.bopit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var maxContador: Long = 3000
        var diff: Long = 1000

        val timer = object: CountDownTimer(maxContador, diff){
            override fun onTick(millisUntilFinished: Long) {
                var diff: Long = maxContador - millisUntilFinished;
            }

            override fun onFinish() {
                var intent = Intent(this@SplashActivity , MainActivity::class.java)
                startActivity(intent)
            }

        }
        timer.start()

    }
}