package fi.tuni.lippuluukku

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
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
import fi.tuni.lippuluukku.model.ResponseDataHolder
import java.io.InputStream

class ResultsActivity : AppCompatActivity() {

    lateinit var loadingAnimation : ImageView
    lateinit var resultStatus : TextView
    lateinit var resultImage : ImageView

    lateinit var url : String

    lateinit var recyclerView : RecyclerView
    lateinit var linearLayoutManager : LinearLayoutManager

    var extras : Bundle? = null

    //var res: MutableList<ResponseData>? = null

    fun testFunc (button: View) {
        //this.dataTest.text = "firstName: ${results.events?.get(2)?.name}, size: ${results.events?.count()}"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        animateBackground()

        extras = intent.extras
        if(extras != null) {
            url = extras!!.getString("url").toString()
        }
        loadingAnimation = findViewById(R.id.loading_image)
        resultStatus = findViewById(R.id.result_status)
        resultImage = findViewById(R.id.no_results_image)
        linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = linearLayoutManager
        thread {
            val rotation = AnimationUtils.loadAnimation(this, R.anim.rotation)
 //           rotation.fillAfter = true
            loadingAnimation.startAnimation(rotation)
            //Glide.with(this).load(R.drawable.giphy).into(imageView)
        }
    }

    override fun onResume() {
        super.onResume()
        thread {
            urlFunc(url)
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


        /*
            val mp = ObjectMapper()
            val myObject: ResponseDataHolder = mp.readValue(it, ResponseDataHolder::class.java)
            recyclerView.recycledViewPool.setMaxRecycledViews(0,100)
            recyclerView.setItemViewCacheSize(100)
            recyclerView.adapter = RecyclerAdapter(myObject._embedded?.events, this)
         */

            val jsonData = JSONObject(it)
            val gson = Gson()
            val responseResults = gson.fromJson(jsonData.toString(), ResponseDataHolder::class.java)
            recyclerView.recycledViewPool.setMaxRecycledViews(0,100)
            recyclerView.setItemViewCacheSize(100)
            recyclerView.adapter = RecyclerAdapter(responseResults._embedded?.events, this)
            println("results not found: " + responseResults._embedded?.events?.first())

            var params : LinearLayout.LayoutParams = loadingAnimation.layoutParams as LinearLayout.LayoutParams
            params.height = 0
            params.width = 0
            loadingAnimation.clearAnimation()
            loadingAnimation.layoutParams = params
            loadingAnimation.visibility = View.INVISIBLE

            if (responseResults._embedded?.events?.first() == null){
                resultStatus.text = "Nothing found"
                params = resultImage.layoutParams as LinearLayout.LayoutParams
                params.height = WRAP_CONTENT
                params.width = MATCH_PARENT
                resultImage.visibility = View.VISIBLE
                resultImage.layoutParams = params
            }
        }
    }


    fun downloadUrlAsync(context: Activity, url:String, callback:(result:String?)->Unit):Unit{
        thread{
            var data = getUrl(url)
            //var data = getUrl("https://app.ticketmaster.com/discovery/v2/events?apikey=wl5A0tEYNyQIQ9cTVA9VGVWlB3R8NgfO&locale=*&page=0&city=helsinki&size=100")
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