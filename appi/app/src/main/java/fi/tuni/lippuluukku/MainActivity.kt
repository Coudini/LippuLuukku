package fi.tuni.lippuluukku

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import com.fasterxml.jackson.databind.ObjectMapper

class MainActivity : AppCompatActivity() {
    lateinit var text : TextView
    lateinit var button : Button
    lateinit var activityButton : Button

    var apikey : String = "wl5A0tEYNyQIQ9cTVA9VGVWlB3R8NgfO"
    var band = "metallica"
    var city = "tampere"
    var radius = 30
    var lat = 61.49911
    var lon = 23.78712
    var latlonString : String = "${lat},${lon}"

    //request with keyword and city
    var url1 : String = "https://app.ticketmaster.com/discovery/v2/events?apikey=${apikey}&keyword=${band}&locale=*&city=${city}"

    //request with keyword
    var url2 : String = "https://app.ticketmaster.com/discovery/v2/events?apikey=${apikey}&keyword=${band}&locale=*"

    // request with city
    var url3 : String = "https://app.ticketmaster.com/discovery/v2/events?apikey=${apikey}&locale=*&city=${city}"

    //request with geopoint(lat,lon) and search radius
    var url4 : String = "https://app.ticketmaster.com/discovery/v2/events?apikey=${apikey}&radius=${radius}&locale=*&geoPoint=${latlonString}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.text = findViewById(R.id.text)
        this.button = findViewById(R.id.button)
        this.button.setOnClickListener(){
            this.buttonFunc(this.button)
        }
        this.activityButton = findViewById(R.id.activityButton)
        this.activityButton.setOnClickListener(){
            this.activityButtonClick(this.activityButton)
        }
    }

    fun activityButtonClick(button : View) {
        val intent = Intent(this, PreferencesActivity::class.java)
        intent.putExtra("testKey","testData")
        startActivityForResult(intent,666)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 666) {
            if(resultCode == RESULT_OK) {
                val temp = data?.extras?.getString("second")
                this.text.text=temp
            }
        }
    }

    fun buttonFunc(button : View){
        downloadUrlAsync(this, this.url1){
            //var resultti : String? = it
            //val mp = ObjectMapper()
            //val myObject: StarWarsJsonObject = mp.readValue(it, StarWarsJsonObject::class.java)
            //val persons: MutableList<Person>? = myObject.results
            //persons?.sortByDescending { it.getBmi(it.mass,it.height) }
            //adapter = ArrayAdapter<Person>(this,R.layout.item,R.id.myTextView,persons!!)
            //myListView.setAdapter(adapter)
            //recyclerView.adapter = CustomAdapter(persons!!)
            //Log.d("shit", resultti.toString())

            //val intent = Intent(this, SecondActivity::class.java)

            //intent.putExtra
                // -> (in second activity) get extras by
                    // val extras = intent.extras

            //startActivity(intent)
                //to get data back from second activity to mainactivity when pressing android back
                //instead of startactivity use :
            //startActivityForResult(intent, requestcode: 12312312)

            //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://...."))
            //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=jotain"))

            Log.d("shit", it.toString())
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