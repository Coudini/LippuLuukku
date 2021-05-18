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
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(), LocationListener {

    lateinit var locationManager : LocationManager
    val locationPermissionCode = 2

    lateinit var preferencesButton : ImageButton
    lateinit var searchButton : ImageButton

    lateinit var locationSpinner : Spinner
    lateinit var keywordSpinner : Spinner

    lateinit var editKeyword : EditText
    lateinit var editLocation : EditText
    lateinit var locationSelected : String
    lateinit var keywordSelected : String

    var lat = 0.0
    var lon = 0.0
    var latlonString : String = "${lat},${lon}"

    var util = Util()

    var locationsArray : Array<String> = arrayOf("Here", "Anywhere", "Tampere")
    var keywordsArray : Array<String> = arrayOf("Anything", "Metallica")

    //Test-strings
    lateinit var gpsTest : TextView
    lateinit var locationTest : TextView
    lateinit var keywordTest : TextView
    lateinit var urlTest : TextView


    override fun onLocationChanged(location: Location) {
        lat = location.latitude
        lon = location.longitude
        latlonString = "${lat},${lon}"

        //test
        gpsTest.text = latlonString
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

        // save data into share SharePreference
        // save data into share SharePreference
        val sp1 = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
        val edit = sp1.edit()
        edit.putInt("key", 0)
        edit.apply()


        // get data from share SharePreference
        val sp2 = getSharedPreferences("FILE_NAME", MODE_PRIVATE)
        val result = sp2.getInt("key", 0)

        this.editKeyword = findViewById(R.id.editKeyword)
        this.editKeyword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                //s
                keywordSelected = s.toString()
                //test
                keywordTest.text = s.toString()
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

        //Test strings
        this.gpsTest = findViewById(R.id.gpsTest)
        this.locationTest = findViewById(R.id.locationTest)
        this.keywordTest = findViewById(R.id.keywordTest)
        this.urlTest = findViewById(R.id.urlTest)

/*
        Log.d("test", "getLocation()")
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        } else {Log.d("test","fail")}
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
*/



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
            val adapter2 = ArrayAdapter<String>(this, R.layout.ghost_text, this.locationsArray)
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, this.locationsArray)
            this.locationSpinner.dropDownVerticalOffset = 75
            this.locationSpinner.adapter = adapter



            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.d("test", "item selected: ${locationsArray[position]}")

                    locationSelected = locationsArray[position]
                    editLocation.setText(locationsArray[position])

                    //test
                    locationTest.text = locationSelected
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("test", "nothing selected")

                }
            }.also { this.locationSpinner.onItemSelectedListener = it }
        }

        this.keywordSpinner = findViewById(R.id.keywordSpinner)
        if (this.keywordSpinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, this.keywordsArray)
            this.keywordSpinner.dropDownVerticalOffset = 75
            this.keywordSpinner.adapter = adapter
            object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.d("test", "item selected: ${keywordsArray[position]}")
                    keywordSelected = keywordsArray[position]
                    editKeyword.setText(keywordsArray[position])
                    //test
                    keywordTest.text = keywordSelected
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("test", "nothing selected")
                }
            }.also { this.keywordSpinner.onItemSelectedListener = it }
        }

    }

    fun getLocation () {
        Log.d("test", "getLocation()")
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        } else {Log.d("test", "fail")}
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
        preferencesIntent.putExtra("locationsArray", this.locationsArray)
        preferencesIntent.putExtra("keywordsArray", this.keywordsArray)
        startActivityForResult(preferencesIntent, 1)
    }

    private fun searchOnClick(button: View) {
        val resultsIntent = Intent(this, ResultsActivity::class.java)
        //resultsIntent.putExtra("searchUrl", this.url1)
        var invalidParameters = false
        var tempUrl = ""
        // Dont use gps
        if (this.locationSelected != "Here" && this.locationSelected != "Anywhere") {
            if (this.keywordSelected != "Anything") {
                tempUrl = util.getUrl(locationSelected, keywordSelected)
            } else {
                tempUrl = util.getUrl(locationSelected, null)
            }
            // Use gps
        } else if (this.locationSelected == "Here") {
            if (this.keywordSelected != "Anything") {
                tempUrl = util.getUrlWithGps(latlonString, keywordSelected)
            } else {
                tempUrl = util.getUrlWithGps(latlonString, null)
            }
        } else if (this.locationSelected == "Anywhere") {
            if (this.keywordSelected != "Anything") {
                tempUrl = util.getUrl(null, keywordSelected)
            } else {
                invalidParameters = true
                val toast = Toast.makeText(getApplicationContext(),
                        "No search parameters",
                        Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        //test
        this.urlTest.text = tempUrl
        if (!invalidParameters) {
            resultsIntent.putExtra("url", tempUrl)
            Log.d("test", tempUrl)
            startActivity(resultsIntent)
        }
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
