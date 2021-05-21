package fi.tuni.lippuluukku

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fi.tuni.lippuluukku.listModel.Keyword
import fi.tuni.lippuluukku.listModel.Location
import fi.tuni.lippuluukku.listModel.UserList

// Handles list editing for lists found in UserList data-class
class PreferencesActivity : AppCompatActivity() {

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

    // Sets up everyting needed for all the functions in this class
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        // Animate background
        animateBackground()

        keywordsRecyclerView = findViewById(R.id.keywords_recyclerView)
        locationsRecyclerView = findViewById(R.id.locations_recyclerView)

        // Set up layouts for RecyclerViews
        keywordsLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        locationsLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        keywordsRecyclerView.layoutManager = keywordsLinearLayoutManager
        locationsRecyclerView.layoutManager = locationsLinearLayoutManager

        // Set up keywords and locations RecyclerViews
        keywordsRecyclerView.adapter = KeywordRecyclerAdapter(util.loadUserData(this)?.keywords, this)
        locationsRecyclerView.adapter = LocationRecyclerAdapter(util.loadUserData(this)?.locations, this)

        editKeyword = findViewById(R.id.preferences_add_keyword)
        editLocation = findViewById(R.id.preferences_add_location)

        addKeyword = findViewById(R.id.preferences_add_keyword_button)
        addLocation = findViewById(R.id.preferences_add_location_button)

        // Add listeners to EditTexts for adding new values for tracking state of arguments to be added to lists
        editKeyword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
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
                location = s.toString()
            }
        })

        // Add onClickListeners to buttons for adding Locations and Keywords lists
        addKeyword.setOnClickListener(){
            if(keyword!=null && keyword.toString().length > 0){
                var tempArray = util.loadUserData(this)
                tempArray?.keywords?.add(Keyword((keyword)))
                util.saveUserData(this,UserList(tempArray?.locations,tempArray?.keywords))
                keywordsRecyclerView.adapter = KeywordRecyclerAdapter(util.loadUserData(this)?.keywords, this)
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
    }

    //Sets up and starts the background animation
    fun animateBackground(){
        val linearLayout : LinearLayout = findViewById(R.id.layout)
        val animationDrawable : AnimationDrawable = linearLayout.getBackground() as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    //Util class holding helper functions for getting urls and manipulating data on User device
    var util = Util()

    // Informs the user data has been saved
    //      (in reality data is saved after every modification to list, but displaying a message every time would be annoying to user)
    // On RESUT_OK MainActivity updates lists holding user-data
    // Intent.extras does not hold the lists because the needed functions are already accessible from the Util class, no need for extra Json-parinsg
    override fun onBackPressed() {
        Toast.makeText(this,"Data saved",Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK, Intent())
        super.onBackPressed()
    }
}