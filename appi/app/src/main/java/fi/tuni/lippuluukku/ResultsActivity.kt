package fi.tuni.lippuluukku

import android.app.Activity
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.JSONWrappedObject
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import kotlinx.serialization.*
import org.json.JSONObject

import fi.tuni.lippuluukku.model.ResponseData
import fi.tuni.lippuluukku.model.JsonObject
import kotlinx.serialization.json.JsonConfiguration
import org.json.JSONArray
import org.json.*

import java.io.FileReader
import com.google.gson.Gson

class ResultsActivity : AppCompatActivity() {

    //test button
    lateinit var testButton : Button
    lateinit var dataTest : TextView
    lateinit var testi : TextView
    lateinit var gsonTest: ResponseData

    var res: MutableList<ResponseData>? = null

    fun testFunc (button: View) {
    //this.dataTest.text = res.toString()
    //this.dataTest.text = res?.first()?._embedded?.events?.first()?.name
    // this.dataTest.text = res?.first()?.page?.size.toString()
    this.dataTest.text = "firstName: ${gsonTest.events.first().name}, size: ${gsonTest.events.count()}"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        animateBackground()
        this.testi = findViewById(R.id.testi)
        this.testButton = findViewById(R.id.dataButton)
        this.testButton.setOnClickListener(){
            this.testFunc(this.testButton)
        }
        this.dataTest =  findViewById(R.id.dataTest)

        val extras = intent.extras
        if(extras != null) {
            // no need to make val
            val url = extras.getString("url")
            urlFunc(extras.getString("url").toString())
            //urlFunc("https://app.ticketmaster.com/discovery/v2/events?apikey=wl5A0tEYNyQIQ9cTVA9VGVWlB3R8NgfO&locale=*&size=1&page=0&city=Tampere")

        }
        Log.d("test","")
    }
    fun animateBackground(){
        val linearLayout : LinearLayout = findViewById(R.id.layout)
        val animationDrawable : AnimationDrawable = linearLayout.getBackground() as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    fun urlFunc(url: String){
        downloadUrlAsync(this, url){

            this.testi.text = it
            Log.d("test", it.toString())

            var jsonData = JSONObject(it)
            Log.d("test", "jsonData: ${jsonData.toString()}")
            //var jsonArray = jsonData.getJSONArray("_embedded")
            var jsonObject = jsonData.getJSONObject("_embedded")
            Log.d("test", "jsonObject: ${jsonObject.toString()}")
            //var tempArray : JSONArray = jsonObject.getJSONArray("events")
            //Log.d("test", "tempArray: ${tempArray.toString()}")

            val gson = Gson()
            this.gsonTest = gson.fromJson(jsonObject.toString(), ResponseData::class.java)
            Log.d("test", gsonTest.events.first().name)

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