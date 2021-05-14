package fi.tuni.lippuluukku

import android.app.Activity
import android.content.Intent
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
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import org.json.JSONObject

import fi.tuni.lippuluukku.model.ResponseData
import com.google.gson.Gson

class ResultsActivity : AppCompatActivity() {

    //test button
    lateinit var testButton : Button
    lateinit var dataTest : TextView
    lateinit var testi : TextView

    lateinit var results: ResponseData

    lateinit var recyclerView : RecyclerView
    lateinit var linearLayoutManager : LinearLayoutManager

    var extras : Bundle? = null

    var res: MutableList<ResponseData>? = null

    fun testFunc (button: View) {
        this.dataTest.text = "firstName: ${results.events.first().name}, size: ${results.events.count()}"
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
            urlFunc(extras!!.getString("url").toString())
        }

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = linearLayoutManager
    }

    override fun onResume() {
        super.onResume()
        // change to its own class to hold images? functions etc..
        var data : String?
        thread {

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

            val gson = Gson()
            this.results = gson.fromJson(jsonObject.toString(), ResponseData::class.java)
            Log.d("test", results.events.first().name)
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