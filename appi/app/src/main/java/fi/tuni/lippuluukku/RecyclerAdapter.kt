package fi.tuni.lippuluukku

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.LinearLayout.LayoutParams.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.tuni.lippuluukku.model.Event
import java.io.InputStream
import java.net.URL
import kotlin.concurrent.thread

//Adapter for RecyclerView listing Events from Event data-class
class RecyclerAdapter(val dataSet: MutableList<Event>?, val context: Activity) :
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    // Introduce all the needed variables
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topLayout : LinearLayout
        val bottomLayout : LinearLayout
        val eventName: TextView
        val eventType: TextView

        val eventMap : ImageButton
        val eventCity : TextView
        val eventAddress : TextView
        val eventDate : TextView
        val eventTime : TextView

        val eventCart: ImageButton
        var eventImage: ImageView
        var eventPrice : TextView

        var attributesSet = false
        var showInfo = false

        init {
            topLayout = view.findViewById(R.id.event_top_layout)
            bottomLayout = view.findViewById(R.id.event_bottom_layout)
            eventName = view.findViewById(R.id.event_name)
            eventType = view.findViewById(R.id.event_type)
            eventMap = view.findViewById(R.id.event_map)
            eventCity = view.findViewById(R.id.event_city)
            eventAddress = view.findViewById(R.id.event_address)
            eventDate = view.findViewById(R.id.event_date)
            eventTime = view.findViewById(R.id.event_time)
            eventCart = view.findViewById(R.id.event_cart)
            eventImage = view.findViewById(R.id.event_image)
            eventPrice = view.findViewById(R.id.event_price)

        }
    }

    // Set backgrounds for all the views and return a ViewHolder for them
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.event_item, viewGroup, false)
        view.setBackgroundColor(0x00000000)
        return ViewHolder(view)
    }

    // Set up all the attributes for each View
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        //Set up values only on first call of this function (attributesSet Boolean)
        //No need for re-sets because list will not be modified
        if (!viewHolder.attributesSet) {
            // set name
            viewHolder.eventName.text = dataSet!![position].name

            // set type
            viewHolder.eventType.text = dataSet!![position].classifications?.first()?.segment?.name

            // set city
            viewHolder.eventCity.text = dataSet!![position]._embedded?.venues?.first()?.city?.name

            //set address
            viewHolder.eventAddress.text = dataSet!![position]._embedded?.venues?.first()?.address?.line1

            // set date
            viewHolder.eventDate.text = dataSet!![position].dates?.start?.localDate

            //set time
            viewHolder.eventTime.text = dataSet!![position].dates?.start?.localTime

            // set price shown
            // some String modification based on if Min and/or/not Max values are found
            if (dataSet[position].priceRanges?.first()?.min != null && dataSet[position].priceRanges?.first()?.max != null) {
                if (dataSet!![position].priceRanges?.first()?.min == dataSet!![position].priceRanges?.first()?.max) {
                    viewHolder.eventPrice.text = "${dataSet!![position].priceRanges?.first()?.max}\n${dataSet!![position].priceRanges?.first()?.currency}"
                } else {
                    viewHolder.eventPrice.text = "${dataSet!![position].priceRanges?.first()?.min} - ${dataSet!![position].priceRanges?.first()?.max}\n${dataSet!![position].priceRanges?.first()?.currency}"
                }
            } else if (dataSet!![position].priceRanges?.first()?.min != null && dataSet!![position].priceRanges?.first()?.min == null) {
                viewHolder.eventPrice.text = "${dataSet!![position].priceRanges?.first()?.min}\n${dataSet!![position].priceRanges?.first()?.currency}"
            } else if (dataSet!![position].priceRanges?.first()?.min == null && dataSet!![position].priceRanges?.first()?.min != null) {
                viewHolder.eventPrice.text = "${dataSet!![position].priceRanges?.first()?.max}\n${dataSet!![position].priceRanges?.first()?.currency}"
            }

            //set dropdown(show or hide another layout) actions for top layout on views
            viewHolder.topLayout.setOnClickListener(){
                //Programmatically some parameters can only be accessed through LayoutParams
                val params: LayoutParams = viewHolder.bottomLayout.layoutParams as LayoutParams
                if (viewHolder.showInfo) {
                    viewHolder.bottomLayout.visibility = View.INVISIBLE
                    params.height = 0
                    viewHolder.bottomLayout.layoutParams = params
                    viewHolder.showInfo = false

                } else {
                    viewHolder.bottomLayout.visibility = View.VISIBLE
                    params.height = WRAP_CONTENT
                    viewHolder.bottomLayout.layoutParams = params
                    viewHolder.showInfo = true
                }
            }

            //Set function when clicking the shopping cart ImageButton
            //Opens browser with url directing to tickets sales
            viewHolder.eventCart.setOnClickListener() {
                val uri = Uri.parse(dataSet!![position].url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }

            //Set function when clicking the map ImageButton
            //Opens GoogleMaps on event venue coordinates
            viewHolder.eventMap.setOnClickListener() {
                val uri = Uri.parse("geo:${dataSet[position]._embedded?.venues?.first()?.location?.latitude},${dataSet[position]._embedded?.venues?.first()?.location?.longitude}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }

            //Load images on seperate Thread to prevent app freezing
            thread{
                //Choose urls for smallest image for faster download speed
                var smallestWidth = dataSet!![position].images!!.first()!!.width
                var smallestIndex = 0
                for (i in 0..dataSet!![position].images!!.count() - 1) {
                    if (dataSet!![position].images!![i].width!! < smallestWidth!!){
                        smallestWidth = dataSet!![position].images!![i].width!!
                        smallestIndex = i
                    }
                }
                //stream image data from url, decode into image and set on ImageView
                val imageStream: InputStream = URL(dataSet!![position].images?.get(smallestIndex)?.url).getContent() as InputStream
                val mirage = BitmapFactory.decodeStream(imageStream)
                context.runOnUiThread(Runnable {
                    viewHolder.eventImage.setImageBitmap(mirage)
                })
            }

            viewHolder.attributesSet = true
        }
    }

    //Returns 0 if dataSet is null
    override fun getItemCount() : Int {
        if (dataSet != null) {
            return dataSet.size
        } else {
            return 0
        }
    }
}
