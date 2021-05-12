package fi.tuni.lippuluukku

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        animateBackground()
        val extras = intent.extras
        if(extras != null) {
            // no need to make val
            val url = extras.getString("url")

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
    /*
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
    * */
}