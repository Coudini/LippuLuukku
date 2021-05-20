package fi.tuni.lippuluukku

import android.app.Activity
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import org.json.JSONObject
import com.google.gson.Gson
import fi.tuni.lippuluukku.model.ResponseDataHolder

class ResultsActivity : AppCompatActivity() {

    lateinit var loadingAnimation : ImageView
    lateinit var resultStatus : TextView
    lateinit var resultImage : ImageView

    lateinit var url : String

    lateinit var recyclerView : RecyclerView
    lateinit var linearLayoutManager : LinearLayoutManager

    var extras : Bundle? = null

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
            loadingAnimation.startAnimation(rotation)
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

            val jsonData = JSONObject(it)
            val gson = Gson()
            val responseResults = gson.fromJson(jsonData.toString(), ResponseDataHolder::class.java)

            // Stop result-images from popping while scrolling list
            recyclerView.recycledViewPool.setMaxRecycledViews(0,1000)
            recyclerView.setItemViewCacheSize(1000)
            recyclerView.adapter = RecyclerAdapter(responseResults._embedded?.events, this)

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