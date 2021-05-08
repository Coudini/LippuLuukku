package fi.tuni.lippuluukku

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PreferencesActivity : AppCompatActivity(), LocationListener {

    lateinit var text : TextView
    lateinit var locationText : TextView
    lateinit var button : Button
    lateinit var locationManager : LocationManager
    private val locationPermissionCode = 2

    override fun onLocationChanged(location: Location) {
        this.text.text = "${location.latitude}, ${location.longitude}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        this.text = findViewById(R.id.exrasText)
        this.locationText = findViewById(R.id.locationText)
        this.button = findViewById(R.id.buttonSecond)
        this.button.setOnClickListener() {
            this.getLocation(this.button)
        }
        val extras = intent.extras
        if(extras != null) {
            text.text = extras.getString("testKey").toString()
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("second","secondTest")
        setResult(RESULT_OK, intent)
        super.onBackPressed()
    }
    fun getLocation (button : View) {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        //mintime and mindistance are how the location gets updated
        //so for an app like this its okay if the numers are high
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        //this.text.text = "homo"
    }
}