package fi.tuni.lippuluukku

import android.app.Activity
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class ResultsActivity : AppCompatActivity() {
    lateinit var testi : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        animateBackground()
        this.testi = findViewById(R.id.testi)
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
            //val mp = ObjectMapper()
            //val myObject: StarWarsJsonObject = mp.readValue(it, StarWarsJsonObject::class.java)
            //val persons: MutableList<Person>? = myObject.results
            //persons?.sortByDescending { it.getBmi(it.mass,it.height) }
            //adapter = ArrayAdapter<Person>(this,R.layout.item,R.id.myTextView,persons!!)
            //myListView.setAdapter(adapter)
            //recyclerView.adapter = CustomAdapter(persons!!)

            //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://...."))
            //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=jotain"))

            //
            //val obj = Json.decodeFromString<MyJsonObject>(it.toString())


            Log.d("shit", it.toString())
            //this.text.text = it.toString()
            val mp = ObjectMapper()
            val myObject : MyJsonObject2 = mp.readValue(it, MyJsonObject2::class.java)
            val resultz : MutableList<ResponseData>? = myObject.results
            resultz?.forEach { println(it) }
            Log.d("test", "results: ${myObject.results?.get(0)?._embedded?.events?.get(0)?.name}")
            this.testi.text = it

            /*
            val mp = ObjectMapper()
            val myObject : MyJsonObject = mp.readValue(it, MyJsonObject::class.java)
            val res : MutableList<ResponseDataGps>? = myObject.results

            Log.d("test", res?.size.toString())

             */
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