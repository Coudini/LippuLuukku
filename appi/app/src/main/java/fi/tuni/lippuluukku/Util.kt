package fi.tuni.lippuluukku

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

class Util {

    val apiKey = "wl5A0tEYNyQIQ9cTVA9VGVWlB3R8NgfO"
    val radius = 30

    fun getUrlWithGps(location: String, keyWord: String?):String{
        if (keyWord != null) {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&keyword=${keyWord}&radius=${radius}&locale=*&geoPoint=${location}"
        } else {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&radius=${radius}&locale=*&geoPoint=${location}"
        }
    }

    fun getUrl(location: String?, keyWord: String?):String{
        if (location != null && keyWord == null) {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&locale=*&city=${location}"
        } else if (location == null && keyWord != null) {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&keyword=${keyWord}&locale=*"
        } else {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&keyword=${keyWord}&locale=*&city=${location}"
        }
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


    var apikey : String = "wl5A0tEYNyQIQ9cTVA9VGVWlB3R8NgfO"
    var band = "metallica"
    var city = "tampere"
    var radius = 30

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

     */
}