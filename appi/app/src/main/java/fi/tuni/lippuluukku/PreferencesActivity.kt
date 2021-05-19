package fi.tuni.lippuluukku

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fi.tuni.lippuluukku.listModel.Keyword
import fi.tuni.lippuluukku.listModel.Location
import fi.tuni.lippuluukku.listModel.UserList



class PreferencesActivity : AppCompatActivity() {



    lateinit var testArray1 : Array<String>
    lateinit var testArray2 : Array<String>
    lateinit var keywordsRecyclerView : RecyclerView
    lateinit var locationsRecyclerView : RecyclerView
    lateinit var keywordsLinearLayoutManager : LinearLayoutManager
    lateinit var locationsLinearLayoutManager : LinearLayoutManager

    lateinit var editKeyword : EditText
    lateinit var editLocation : EditText
    lateinit var addKeyword : ImageButton
    lateinit var addLocation : ImageButton

    var keyword : String? = null
    var location : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        val extras = intent.extras
        if(extras != null) {
            testArray1 = extras.getStringArray("locationsArray")!!
            testArray2 = extras.getStringArray("keywordsArray")!!
            Log.d("extras", testArray1?.count().toString())
            Log.d("extras", testArray2?.count().toString())
        }
        keywordsRecyclerView = findViewById(R.id.keywords_recyclerView)
        locationsRecyclerView = findViewById(R.id.locations_recyclerView)

        keywordsRecyclerView.recycledViewPool.setMaxRecycledViews(0,1000)
        locationsRecyclerView.setItemViewCacheSize(1000)

        keywordsRecyclerView.recycledViewPool.setMaxRecycledViews(0,1000)
        locationsRecyclerView.setItemViewCacheSize(1000)

        keywordsLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        locationsLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        keywordsRecyclerView.layoutManager = keywordsLinearLayoutManager
        locationsRecyclerView.layoutManager = locationsLinearLayoutManager



        keywordsRecyclerView.adapter = KeywordRecyclerAdapter(util.loadUserData(this)?.keywords, this, true)
        locationsRecyclerView.adapter = LocationRecyclerAdapter(util.loadUserData(this)?.locations, this)

        editKeyword = findViewById(R.id.preferences_add_keyword)
        editLocation = findViewById(R.id.preferences_add_location)
        addKeyword = findViewById(R.id.preferences_add_keyword_button)
        addLocation = findViewById(R.id.preferences_add_location_button)

        editKeyword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                //setLocation(s.toString())
                keyword = s.toString()
            }
        })

        editLocation.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                //setLocation(s.toString())
                location = s.toString()
            }
        })

        addKeyword.setOnClickListener(){
            if(keyword!=null && keyword.toString().length > 0){
                var tempArray = util.loadUserData(this)
                tempArray?.keywords?.add(Keyword((keyword)))
                util.saveUserData(this,UserList(tempArray?.locations,tempArray?.keywords))
                keywordsRecyclerView.adapter = KeywordRecyclerAdapter(util.loadUserData(this)?.keywords, this, true)
            } else {
                Toast.makeText(this, "No info provided", Toast.LENGTH_SHORT).show()
            }
        }

        addLocation.setOnClickListener(){
            if(location!=null && location.toString().length > 0){
                var tempArray = util.loadUserData(this)
                tempArray?.locations?.add(Location((location)))
                util.saveUserData(this,UserList(tempArray?.locations,tempArray?.keywords))
                locationsRecyclerView.adapter = LocationRecyclerAdapter(util.loadUserData(this)?.locations, this)
            } else {
                Toast.makeText(this, "No info provided", Toast.LENGTH_SHORT).show()
            }
        }

        animateBackground()
        //testLoad()
    }
    fun animateBackground(){
        val linearLayout : LinearLayout = findViewById(R.id.layout)
        val animationDrawable : AnimationDrawable = linearLayout.getBackground() as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }
    fun test () {
        println("TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST")
    }
    var util = Util()

    fun testSave (ul: UserList) {
        util.saveUserData(this, ul)
    }
    fun testLoad () {
        val tempArray = util.loadUserData(this)
        println(tempArray)
        //testSave(tempArray!!)
    }
    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("second","secondTest")
        setResult(RESULT_OK, intent)
        super.onBackPressed()
    }
}