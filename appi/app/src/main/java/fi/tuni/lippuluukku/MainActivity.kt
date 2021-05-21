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

// Show RecyclerView lists for Locations and Keywords
// User can choose them from the lists or input their own
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

    //Util class holding helper functions for getting urls and manipulating data on User device
    var util = Util()

    // Keeps location up to date and updates the latlonString holding location values
    override fun onLocationChanged(location: Location) {
        lat = location.latitude
        lon = location.longitude
        latlonString = "${lat},${lon}"
    }

    // Request permission from user device to use device GPS-location
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

    //get user gps-location
    //start background animation
    //set up everything
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getLocation()

        animateBackground()

        // RecyclerViews for Locations and Keywords
        keywordsRecyclerView = findViewById(R.id.keywords_recyclerView)
        locationsRecyclerView = findViewById(R.id.locations_recyclerView)

        // LayoutManagers for RecyclerViews
        keywordsLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        locationsLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        keywordsRecyclerView.layoutManager = keywordsLinearLayoutManager
        locationsRecyclerView.layoutManager = locationsLinearLayoutManager

        // Use custom adapters on RecyclerViews with either Keywords or Locations data
        keywordsRecyclerView.adapter = KeywordRecyclerAdapter(util.loadUserData(this)?.keywords, this)
        locationsRecyclerView.adapter = LocationRecyclerAdapter(util.loadUserData(this)?.locations, this)

        // Listeners for EditTexts updating the current parameter for Keyword and Location
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

        // Initialize a chosen Keyword and Location as first items from the lists
        setLocation(util.loadUserData(this)?.locations?.first()?.name!!)
        setKeyword(util.loadUserData(this)?.keywords?.first()?.name!!)

        // Set OnClickListeners to buttons for starting activities
        this.preferencesButton = findViewById(R.id.imageButtonPreferences)
        this.preferencesButton.setOnClickListener(){
            this.preferencesOnClick(this.preferencesButton)
        }
        this.searchButton = findViewById(R.id.imageButtonSearch)
        this.searchButton.setOnClickListener(){
            this.searchOnClick(this.searchButton)
        }
    }

    // Update user location in case app has been open for a long time and user has moved
    // setLocation and setKeyword removed for better UX
    override fun onResume() {
        super.onResume()
        getLocation()
        //setLocation(util.loadUserData(this)?.locations?.first()?.name!!)
        //setKeyword(util.loadUserData(this)?.keywords?.first()?.name!!)
    }

    // LocationManager handling manifest permission checks and requesting Location Updates from device
    fun getLocation () {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    //Sets up and starts the background animation
    private fun animateBackground(){
        val linearLayout : LinearLayout = findViewById(R.id.layout)
        val animationDrawable : AnimationDrawable = linearLayout.getBackground() as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    // Function to start Preferences activty
    private fun preferencesOnClick(button: View){
        val preferencesIntent = Intent(this, PreferencesActivity::class.java)
        startActivityForResult(preferencesIntent, 1)
    }

    // Function to start Results activity
    // Handles url request according to parameters
    private fun searchOnClick(button: View) {
        val resultsIntent = Intent(this, ResultsActivity::class.java)

        var invalidParameters = false
        var tempUrl = ""
        var tempLoc : String?
        var tempKey : String?

        // Set tempLoc to match search parameter
        when (locationSelected) {
            "Here" -> tempLoc = latlonString
            "Anywhere" -> tempLoc = null
            "" -> tempLoc = null
            else -> tempLoc = locationSelected
        }

        // Set tempKey to match search parameter
        when (keywordSelected) {
            "Anything" -> tempKey = null
            "" -> tempKey = null
            else -> tempKey = keywordSelected
        }

        // Request the correct url from Util according to tempKey and tempLoc parameters
        when (tempLoc) {
            null -> when (tempKey) {
                null -> invalidParameters = true
                else -> tempUrl = util.getUrl(tempLoc, tempKey)
            }
            latlonString -> tempUrl = util.getUrlWithGps(tempLoc,tempKey)
            else -> tempUrl = util.getUrl(tempLoc,tempKey)
        }

        // Inform user of invalid parameters or start a new activity with url String in extras
        if (!invalidParameters) {
            resultsIntent.putExtra("url", tempUrl)
            startActivity(resultsIntent)
        } else {
            Toast.makeText(this,
                    "No search parameters",
                    Toast.LENGTH_SHORT).show()
        }
    }

    // Update chosen Location and update EditText accordingly
    fun setLocation(location: String){
        locationSelected = location
        editLocation.setText(location)
    }

    // Update chosen Keyword and update EditText accordingly
    fun setKeyword(keyword: String){
        keywordSelected = keyword
        editKeyword.setText(keyword)
    }

    // Used only when returning from PreferencesActivity to MainActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // requestCode 1 = PreferencesActivity
        if(requestCode == 1) {

            // When returning from PreferencesActivity updates the UserList class holding lists for Keywords and Locations
            if(resultCode == RESULT_OK) {
                val temp = util.loadUserData(this)

                //getLocation()
                //setLocation(temp?.locations?.first()?.name!!)
                //setKeyword(temp?.keywords?.first()?.name!!)
                
                // Updates the lists with reloaded data
                keywordsRecyclerView.adapter = KeywordRecyclerAdapter(temp?.keywords, this)
                locationsRecyclerView.adapter = LocationRecyclerAdapter(temp?.locations, this)
            }
        }
    }
}
