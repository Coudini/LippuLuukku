package fi.tuni.lippuluukku

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import fi.tuni.lippuluukku.listModel.Keyword
import fi.tuni.lippuluukku.listModel.Location
import fi.tuni.lippuluukku.listModel.UserList


class Util {

    val apiKey = "wl5A0tEYNyQIQ9cTVA9VGVWlB3R8NgfO"
    val radius = 35

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

    fun saveUserData(context: Activity, userList: UserList){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("userData", MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        val gson = Gson()

        val json = gson.toJson(userList)

        editor.putString("userData", json)

        editor.apply()
    }

    fun loadUserData(context: Activity) : UserList? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("userData", MODE_PRIVATE)

        val gson = Gson()

        val json = sharedPreferences.getString("userData", null)

        var tempUserList : UserList? = gson.fromJson(json, UserList::class.java)

        if (tempUserList == null) {
            var initKeywords : MutableList<Keyword> = mutableListOf(Keyword("Anything"),Keyword("Music"),Keyword("Art"),Keyword("Sports"))
            var initLocations : MutableList<Location> = mutableListOf(Location("Here"),Location("Anywhere"),Location("Helsinki"))
            var initUserList = UserList(initLocations,initKeywords)
            tempUserList = initUserList
        }

        return tempUserList
    }
}