package fi.tuni.lippuluukku


import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.LinearLayout.LayoutParams.*
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import fi.tuni.lippuluukku.model.Event
import java.io.InputStream
import java.net.URL
import kotlin.concurrent.thread
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool


//import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(val dataSet: MutableList<Event>?, val context: Activity) :
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topLayout : LinearLayout
        val bottomLayout : LinearLayout
        val eventName: TextView
        val eventType: TextView
        val eventCart: ImageButton
        //val eventDescription : TextView
        var eventImage: ImageView
        var eventPrice : TextView
        var attributesSet = false
        var showInfo = false

        init {
            topLayout = view.findViewById(R.id.event_top_layout)
            bottomLayout = view.findViewById(R.id.event_bottom_layout)
            eventName = view.findViewById(R.id.event_name)
            eventType = view.findViewById(R.id.event_type)
            eventCart = view.findViewById(R.id.event_cart)
            //eventDescription = view.findViewById(R.id.event_description)
            eventImage = view.findViewById(R.id.event_image)
            eventPrice = view.findViewById(R.id.event_price)

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.event_item, viewGroup, false)
        view.setBackgroundColor(0x26000000)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        println(dataSet?.size)
        Log.d("test", "dataset size : ${dataSet?.size}")

        //set up values only on first call of this function
        if (!viewHolder.attributesSet) {
            // set name and type of event
            viewHolder.eventName.text = dataSet!![position].name
            viewHolder.eventType.text = dataSet!![position].classifications?.first()?.segment?.name

            // set price shown
            if (dataSet!![position].priceRanges?.first()?.min != null && dataSet!![position].priceRanges?.first()?.max != null) {
                if (dataSet!![position].priceRanges?.first()?.min == dataSet!![position].priceRanges?.first()?.max) {
                    viewHolder.eventPrice.text = "${dataSet!![position].priceRanges?.first()?.max}  ${dataSet!![position].priceRanges?.first()?.currency}"
                } else {
                    viewHolder.eventPrice.text = "${dataSet!![position].priceRanges?.first()?.min} - ${dataSet!![position].priceRanges?.first()?.max}  ${dataSet!![position].priceRanges?.first()?.currency}"
                }
            } else if (dataSet!![position].priceRanges?.first()?.min != null && dataSet!![position].priceRanges?.first()?.min == null) {
                viewHolder.eventPrice.text = "${dataSet!![position].priceRanges?.first()?.min} ${dataSet!![position].priceRanges?.first()?.currency}"
            } else if (dataSet!![position].priceRanges?.first()?.min == null && dataSet!![position].priceRanges?.first()?.min != null) {
                viewHolder.eventPrice.text = "${dataSet!![position].priceRanges?.first()?.max} ${dataSet!![position].priceRanges?.first()?.currency}"
            }

            //set dropdown actions for top layout on views
            viewHolder.topLayout.setOnClickListener(){
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

            //set function when clicking the shopping cart ImageButton
            viewHolder.eventCart.setOnClickListener() {
                val webIntent: Intent = Uri.parse(dataSet!![position].url).let { destination ->
                    Intent(Intent.ACTION_VIEW, destination)
                }
                context.startActivity(webIntent)
            }

            //load images
            thread{
                var smallestWidth = dataSet!![position].images!!.first()!!.width
                var smallestIndex = 0
                for (i in 0..dataSet!![position].images!!.count() - 1) {
                    if (dataSet!![position].images!![i].width!! < smallestWidth!!){
                        smallestWidth = dataSet!![position].images!![i].width!!
                        smallestIndex = i
                    }
                }

                val imageStream: InputStream = URL(dataSet!![position].images?.get(smallestIndex)?.url).getContent() as InputStream
                val mirage = BitmapFactory.decodeStream(imageStream)
                context.runOnUiThread(Runnable {
                    viewHolder.eventImage.setImageBitmap(mirage)
                })
            }

            viewHolder.attributesSet = true
        }



    }

    override fun getItemCount() = dataSet!!.size

}

