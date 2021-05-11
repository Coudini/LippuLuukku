package fi.tuni.lippuluukku

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import com.fasterxml.jackson.databind.ObjectMapper

class MainActivity : AppCompatActivity() {

    //lateinit var text : TextView
    lateinit var location : TextView
    lateinit var keyword : TextView

    /* test-buttons
    lateinit var button1 : Button
    lateinit var button2 : Button
    lateinit var button3 : Button
    lateinit var button4 : Button
    lateinit var button5 : Button

     */
    lateinit var preferencesButton : ImageButton
    lateinit var searchButton : ImageButton

    lateinit var locationSpinner : Spinner
    lateinit var keywordSpinner : Spinner

//    lateinit var keyWordText : EditText
//    lateinit var cityText : EditText

    var apikey : String = "wl5A0tEYNyQIQ9cTVA9VGVWlB3R8NgfO"
    var band = "metallica"
    var city = "tampere"
    var radius = 30
    var lat = 61.49911
    var lon = 23.78712
    var latlonString : String = "${lat},${lon}"

    var locationsArray : Array<String> = arrayOf("Current location", "No location", "Tampere")
    var keywordsArray : Array<String> = arrayOf("Nothing", "Metallica")

    //request with keyword and city
    var url1 : String = "https://app.ticketmaster.com/discovery/v2/events?apikey=${apikey}&keyword=${band}&locale=*&city=${city}"

    //request with keyword
    var url2 : String = "https://app.ticketmaster.com/discovery/v2/events?apikey=${apikey}&keyword=${band}&locale=*"

    // request with city
    var url3 : String = "https://app.ticketmaster.com/discovery/v2/events?apikey=${apikey}&locale=*&city=${city}"

    //request with geopoint(lat,lon) and search radius
    var url4 : String = "https://app.ticketmaster.com/discovery/v2/events?apikey=${apikey}&radius=${radius}&locale=*&geoPoint=${latlonString}"

    //request with keyword, geopoint(lat,lon) and search radius
    var url5 :String = "https://app.ticketmaster.com/discovery/v2/events?apikey=${apikey}&keyword=${band}&radius=${radius}&locale=*&geoPoint=${latlonString}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //this.text = findViewById(R.id.text)
        /* Test-buttons
        this.button1 = findViewById(R.id.button1)
        this.button1.setOnClickListener(){
            this.buttonFunc(this.button1, url1)
        }
        this.button2 = findViewById(R.id.button2)
        this.button2.setOnClickListener(){
            this.buttonFunc(this.button2, url2)
        }
        this.button3 = findViewById(R.id.button3)
        this.button3.setOnClickListener(){
            this.buttonFunc(this.button3, url3)
        }
        this.button4 = findViewById(R.id.button4)
        this.button4.setOnClickListener(){
            this.buttonFunc(this.button4, url4)
        }
        this.button5 = findViewById(R.id.button5)
        this.button5.setOnClickListener(){
            this.buttonFunc(this.button5, url5)
        }
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
            val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,this.locationsArray)
            this.locationSpinner.adapter = adapter
        }

        this.keywordSpinner = findViewById(R.id.keywordSpinner)
        if (this.keywordSpinner != null) {
            val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,this.keywordsArray)
            this.keywordSpinner.adapter = adapter
        }

    }

    fun preferencesOnClick(button:View){
        val preferencesIntent = Intent(this, PreferencesActivity::class.java)
        preferencesIntent.putExtra("locationsArray",this.locationsArray)
        preferencesIntent.putExtra("keywordsArray",this.keywordsArray)
        startActivityForResult(preferencesIntent, 1)
    }

    fun searchOnClick(button:View) {
        val resultsIntent = Intent(this, ResultsActivity::class.java)
        resultsIntent.putExtra("searchUrl", this.url1)
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
            //val temp = data?.extras?.getString("second")
                //this.text.text=temp
            }
        }
    }

    fun buttonFunc(button : View, url: String){
        downloadUrlAsync(this, url){
            //var resultti : String? = it
            //val mp = ObjectMapper()
            //val myObject: StarWarsJsonObject = mp.readValue(it, StarWarsJsonObject::class.java)
            //val persons: MutableList<Person>? = myObject.results
            //persons?.sortByDescending { it.getBmi(it.mass,it.height) }
            //adapter = ArrayAdapter<Person>(this,R.layout.item,R.id.myTextView,persons!!)
            //myListView.setAdapter(adapter)
            //recyclerView.adapter = CustomAdapter(persons!!)

            //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://...."))
            //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=jotain"))

            Log.d("shit", it.toString())
            //this.text.text = it.toString()
        }
    }

    fun downloadUrlAsync(context: Activity, url:String, callback:(result:String?)->Unit):Unit{
        thread{
            var data = getUrl(url)
            context.runOnUiThread{
                callback(data)
            }
        }
    }
    fun getUrl(url : String?) : String {
        val myUrl = URL(url)
        val conn = myUrl.openConnection() as HttpURLConnection
        var result = ""
        val inputstream = conn.getInputStream()
        inputstream.use{
            val reader = BufferedReader(InputStreamReader(inputstream))
            result += reader.readLine()
        }
        return result
    }
}