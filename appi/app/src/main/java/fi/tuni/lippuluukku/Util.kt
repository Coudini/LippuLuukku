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
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&keyword=${keyWord}&radius=${radius}&geoPoint=${location}&size=50"//&size=50&page=0"
        } else {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&radius=${radius}&geoPoint=${location}"//&size=50&page=0"
        }
    }

    fun getUrl(location: String?, keyWord: String?):String{
        if (location != null && keyWord == null) {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&city=${location}"
        } else if (location == null && keyWord != null) {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&keyword=${keyWord}"
        } else {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&keyword=${keyWord}&locale=*&page=0&city=${location}"
        }
    }

    fun setUpEvents(){

    }

    /*



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