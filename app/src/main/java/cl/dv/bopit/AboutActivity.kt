package cl.dv.bopit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val buttonNavigate = findViewById<Button>(R.id.regresarButton)

        buttonNavigate.setOnClickListener{
            val intentAbout = Intent(this, MainActivity::class.java)
            startActivity(intentAbout)
        }
    }
}