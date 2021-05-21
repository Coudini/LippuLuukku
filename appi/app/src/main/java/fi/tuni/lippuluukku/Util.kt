package fi.tuni.lippuluukku

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import fi.tuni.lippuluukku.listModel.Keyword
import fi.tuni.lippuluukku.listModel.Location
import fi.tuni.lippuluukku.listModel.UserList

// Utility class for url-searchword matching and creating/loading/saving permanent data on user device
class Util {

    val apiKey = "wl5A0tEYNyQIQ9cTVA9VGVWlB3R8NgfO"
    val radius = 35

    // function to get url-String according to search parameters
    fun getUrlWithGps(location: String, keyWord: String?):String{
        //gps & keyword
        if (keyWord != null) {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&keyword=${keyWord}&radius=${radius}&locale=*&size=100&page=0&geoPoint=${location}"
        }
        //gps
        else {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&radius=${radius}&locale=*&size=100&page=0&geoPoint=${location}"
        }
    }

    // function to get url-String according to search parameters
    fun getUrl(location: String?, keyWord: String?):String{
        //location
        if (location != null && keyWord == null) {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&locale=*&size=100&page=0&city=${location}"
        }
        //keyword
        else if (location == null && keyWord != null) {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&keyword=${keyWord}&locale=*&size=100&page=0"
        }
        //location & keyword
        else {
            return "https://app.ticketmaster.com/discovery/v2/events?apikey=${apiKey}&keyword=${keyWord}&locale=*&size=100&page=0&city=${location}"
        }
    }

    //function to save a data class consisting of location and keyword lists in (String) json-form
    fun saveUserData(context: Activity, userList: UserList){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("userData", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        //turn data class into json
        val json = gson.toJson(userList)
        editor.putString("userData", json)
        editor.apply()
    }

    //function to load json-data from user device and turn it into UserList data-class
    //if no data is found from the user device, create a new list
    //returns UserList data-class
    fun loadUserData(context: Activity) : UserList? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("userData", MODE_PRIVATE)
        val gson = Gson()
        //turn String into json for turning it into UserList data-class
        val json = sharedPreferences.getString("userData", null)
        //turn json into UserList data class
        //null, if data is not found
        var tempUserList : UserList? = gson.fromJson(json, UserList::class.java)
        //create a new data class if tempUserList is null
        if (tempUserList == null) {
            var initKeywords : MutableList<Keyword> = mutableListOf(Keyword("Anything"),Keyword("Music"),Keyword("Art"),Keyword("Sports"))
            var initLocations : MutableList<Location> = mutableListOf(Location("Here"),Location("Anywhere"),Location("Helsinki"))
            var initUserList = UserList(initLocations,initKeywords)
            tempUserList = initUserList
        }
        return tempUserList
    }
}