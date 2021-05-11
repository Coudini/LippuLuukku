package fi.tuni.lippuluukku

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import com.fasterxml.jackson.databind.ObjectMapper

class MainActivity : AppCompatActivity(), LocationListener {

    lateinit var locationManager : LocationManager
    val locationPermissionCode = 2

    lateinit var preferencesButton : ImageButton
    lateinit var searchButton : ImageButton

    lateinit var locationSpinner : Spinner
    lateinit var keywordSpinner : Spinner



    var lat = 61.49911
    var lon = 23.78712
    var latlonString : String = "${lat},${lon}"

    var util = Util()

    var locationsArray : Array<String> = arrayOf("Current location", "No location", "Tampere")
    var keywordsArray : Array<String> = arrayOf("Nothing", "Metallica")


    override fun onLocationChanged(location: Location) {
        lat = location.latitude
        lon = location.longitude
        latlonString = "${lat}${lon}"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getLocation()
        animateBackground()

        this.preferencesButton = findViewById(R.id.imageButtonPreferences)
        this.preferencesButton.setOnClickListener(){
            this.preferencesOnClick(this.preferencesButton)
        }

        this.searchButton = findViewById(R.id.imageButtonSearch)
        this.searchButton.setOnClickListener(){
            this.searchOnClick(this.searchButton)
        }

        this.locationSpinner = findViewById(R.id.locationSpinner)
        if (this.locationSpinner != null) {
            val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,this.locationsArray)
            this.locationSpinner.adapter = adapter
        }

        this.keywordSpinner = findViewById(R.id.keywordSpinner)
        if (this.keywordSpinner != null) {
            val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,this.keywordsArray)
            this.keywordSpinner.adapter = adapter
        }

    }

    private fun getLocation () {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 50f, this)
    }

    private fun animateBackground(){
        val linearLayout : LinearLayout = findViewById(R.id.layout)
        val animationDrawable : AnimationDrawable = linearLayout.getBackground() as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    private fun preferencesOnClick(button:View){
        val preferencesIntent = Intent(this, PreferencesActivity::class.java)
        preferencesIntent.putExtra("locationsArray",this.locationsArray)
        preferencesIntent.putExtra("keywordsArray",this.keywordsArray)
        startActivityForResult(preferencesIntent, 1)
    }

    private fun searchOnClick(button:View) {
        val resultsIntent = Intent(this, ResultsActivity::class.java)
        //resultsIntent.putExtra("searchUrl", this.url1)
        startActivity(resultsIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //preferences
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                val tempLocations = data?.extras?.getStringArray("locationsArray")
                if (tempLocations != null) {
                    this.locationsArray = tempLocations
                }
                val tempKeywords = data?.extras?.getStringArray("keywordsArray")
                if (tempKeywords != null) {
                    this.keywordsArray = tempKeywords
                }
            }
        }
    }
}
