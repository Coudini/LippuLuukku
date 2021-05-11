package fi.tuni.lippuluukku

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        val extras = intent.extras
        if(extras != null) {
            val testArray1 = extras.getStringArray("locationsArray")
            val testArray2 = extras.getStringArray("keywordsArray")
            Log.d("extras", testArray1?.count().toString())
            Log.d("extras", testArray2?.count().toString())
        }
        animateBackground()
    }
    fun animateBackground(){
        val linearLayout : LinearLayout = findViewById(R.id.layout)
        val animationDrawable : AnimationDrawable = linearLayout.getBackground() as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("second","secondTest")
        setResult(RESULT_OK, intent)
        super.onBackPressed()
    }
}