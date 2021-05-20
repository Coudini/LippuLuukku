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
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), LocationListener {

    lateinit var locationManager : LocationManager
    val locationPermissionCode = 2

    lateinit var preferencesButton : ImageButton
    lateinit var searchButton : ImageButton

    lateinit var editKeyword : EditText
    lateinit var editLocation : EditText
    lateinit var locationSelected : String
    lateinit var keywordSelected : String

    lateinit var keywordsRecyclerView : RecyclerView
    lateinit var locationsRecyclerView : RecyclerView
    lateinit var keywordsLinearLayoutManager : LinearLayoutManager
    lateinit var locationsLinearLayoutManager : LinearLayoutManager

    var lat = 0.0
    var lon = 0.0
    var latlonString : String = "${lat},${lon}"

    var util = Util()

    override fun onLocationChanged(location: Location) {
        lat = location.latitude
        lon = location.longitude
        latlonString = "${lat},${lon}"
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getLocation()

        animateBackground()

        keywordsRecyclerView = findViewById(R.id.keywords_recyclerView)
        locationsRecyclerView = findViewById(R.id.locations_recyclerView)

        keywordsLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        locationsLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        keywordsRecyclerView.layoutManager = keywordsLinearLayoutManager
        locationsRecyclerView.layoutManager = locationsLinearLayoutManager

        keywordsRecyclerView.adapter = KeywordRecyclerAdapter(util.loadUserData(this)?.keywords, this)
        locationsRecyclerView.adapter = LocationRecyclerAdapter(util.loadUserData(this)?.locations, this)

        this.editKeyword = findViewById(R.id.editKeyword)
        this.editKeyword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                keywordSelected = s.toString()
            }
        })

        this.editLocation = findViewById(R.id.editLocation)
        this.editLocation.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                locationSelected = s.toString()
            }
        })

        setLocation(util.loadUserData(this)?.locations?.first()?.name!!)
        setKeyword(util.loadUserData(this)?.keywords?.first()?.name!!)

        this.preferencesButton = findViewById(R.id.imageButtonPreferences)
        this.preferencesButton.setOnClickListener(){
            this.preferencesOnClick(this.preferencesButton)
        }

        this.searchButton = findViewById(R.id.imageButtonSearch)
        this.searchButton.setOnClickListener(){
            this.searchOnClick(this.searchButton)
        }
    }

    override fun onResume() {
        super.onResume()
        getLocation()
        setLocation(util.loadUserData(this)?.locations?.first()?.name!!)
        setKeyword(util.loadUserData(this)?.keywords?.first()?.name!!)
    }

    fun getLocation () {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    private fun animateBackground(){
        val linearLayout : LinearLayout = findViewById(R.id.layout)
        val animationDrawable : AnimationDrawable = linearLayout.getBackground() as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    private fun preferencesOnClick(button: View){
        val preferencesIntent = Intent(this, PreferencesActivity::class.java)
        startActivityForResult(preferencesIntent, 1)
    }

    private fun searchOnClick(button: View) {
        val resultsIntent = Intent(this, ResultsActivity::class.java)

        var invalidParameters = false
        var tempUrl = ""
        var tempLoc : String?
        var tempKey : String?

        when (locationSelected) {
            "Here" -> tempLoc = latlonString
            "Anywhere" -> tempLoc = null
            "" -> tempLoc = null
            else -> tempLoc = locationSelected
        }

        when (keywordSelected) {
            "Anything" -> tempKey = null
            "" -> tempKey = null
            else -> tempKey = keywordSelected
        }

        when (tempLoc) {
            null -> when (tempKey) {
                null -> invalidParameters = true
                else -> tempUrl = util.getUrl(tempLoc, tempKey)
            }
            latlonString -> tempUrl = util.getUrlWithGps(tempLoc,tempKey)
            else -> tempUrl = util.getUrl(tempLoc,tempKey)
        }

        if (!invalidParameters) {
            resultsIntent.putExtra("url", tempUrl)
            startActivity(resultsIntent)
        } else {
            Toast.makeText(this,
                    "No search parameters",
                    Toast.LENGTH_SHORT).show()
        }
    }

    fun setLocation(location: String){
        locationSelected = location
        editLocation.setText(location)
    }

    fun setKeyword(keyword: String){
        keywordSelected = keyword
        editKeyword.setText(keyword)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // requestCode 1 = PreferencesActivity
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                val temp = util.loadUserData(this)
                getLocation()
                setLocation(temp?.locations?.first()?.name!!)
                setKeyword(temp?.keywords?.first()?.name!!)
                keywordsRecyclerView.adapter = KeywordRecyclerAdapter(temp?.keywords, this)
                locationsRecyclerView.adapter = LocationRecyclerAdapter(temp?.locations, this)
            }
        }
    }
}
