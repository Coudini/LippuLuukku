package fi.tuni.lippuluukku

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import org.json.JSONObject

import fi.tuni.lippuluukku.model.ResponseData
import com.google.gson.Gson
import java.io.InputStream

class ResultsActivity : AppCompatActivity() {

    //test button
    lateinit var testButton : Button
    lateinit var dataTest : TextView
    lateinit var testi : TextView

    lateinit var url : String

    lateinit var results: ResponseData

    lateinit var recyclerView : RecyclerView
    lateinit var linearLayoutManager : LinearLayoutManager

    var extras : Bundle? = null

    //var res: MutableList<ResponseData>? = null

    fun testFunc (button: View) {
        this.dataTest.text = "firstName: ${results.events?.get(2)?.name}, size: ${results.events?.count()}"
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

        extras = intent.extras
        if(extras != null) {
            url = extras!!.getString("url").toString()
        //urlFunc(extras!!.getString("url").toString())
        }

        linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = linearLayoutManager
    }

    override fun onResume() {
        super.onResume()
        // change to its own class to hold images? functions etc..
        //var data : String?

        thread {
            urlFunc(url)

            //recyclerView.adapter = RecyclerAdapter(this.results.events)
        }
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

           //val mp = ObjectMapper()
            //val myObject: ResponseData = mp.readValue(jsonObject.toString(), ResponseData::class.java)
            //val events: MutableList<fi.tuni.lippuluukku.model.Event>? = myObject.events
            //recyclerView.adapter = RecyclerAdapter(events!!)


            val gson = Gson()
            this.results = gson.fromJson(jsonObject.toString(), ResponseData::class.java)
            Log.d("test", "${results?.events?.first()?.name?.toString()}")
            recyclerView.adapter = RecyclerAdapter(this.results?.events, this)
            //recyclerView.adapter = RecyclerAdapter(this?.results)

        }
    }



        // SET IMAGES HERE
    fun setImages(url:String, ){
            //this.runOnUiThread()
    }

    fun downloadUrlAsync(context: Activity, url:String, callback:(result:String?)->Unit):Unit{
        thread{
            //var data = getUrl(url)
            var data = getUrl("https://app.ticketmaster.com/discovery/v2/events?apikey=wl5A0tEYNyQIQ9cTVA9VGVWlB3R8NgfO&locale=*&page=0&city=Tampere")
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