package cl.dv.bopit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //About
        val aboutNavigate = findViewById<Button>(R.id.aboutButton)

        aboutNavigate.setOnClickListener{
            val intentAbout = Intent(this, AboutActivity::class.java)
            startActivity(intentAbout)
        }

        //Preferencias
        val prefNavigate = findViewById<Button>(R.id.prefButton)

        prefNavigate.setOnClickListener{
            val intentAbout = Intent(this, SettingsActivity::class.java)
            startActivity(intentAbout)
        }
    }
}