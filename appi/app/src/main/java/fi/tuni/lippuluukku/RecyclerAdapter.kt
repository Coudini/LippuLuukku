package fi.tuni.lippuluukku


import android.app.Activity
import android.graphics.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


//import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(val dataSet: MutableList<Event>?, val context: Activity) :
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topLayout : LinearLayout
        val bottomLayout : LinearLayout
        val eventName: TextView
        val eventType: TextView
        //val eventDescription : TextView
        var eventImage: ImageView
        var imageSet = false
        var showInfo = false

        init {
            topLayout = view.findViewById(R.id.event_top_layout)
            bottomLayout = view.findViewById(R.id.event_bottom_layout)
            eventName = view.findViewById(R.id.event_name)
            eventType = view.findViewById(R.id.event_type)
            //eventDescription = view.findViewById(R.id.event_description)
            eventImage = view.findViewById(R.id.event_image)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.event_item, viewGroup, false)
        //view.setBackgroundColor(Color.GREEN)
        view.setBackgroundColor(0x26000000)

        //val root: View = view.getRootView()
        //root.setBackgroundColor(getResources().getColor(color.white))

        return ViewHolder(view)
    }



    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        println(dataSet?.size)
        Log.d("test", "dataset size : ${dataSet?.size}")
        viewHolder.eventName.text = dataSet!![position].name
        viewHolder.eventType.text = dataSet!![position].classifications?.first()?.segment?.name
        //viewHolder.eventDescription.text = dataSet!![position].classifications?.first()?.genre?.name


        fun handleImage () {
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
        }
        if (!viewHolder.imageSet){
            handleImage()
            viewHolder.imageSet = true
        }
        //handleImage()
        viewHolder.topLayout.setOnClickListener(){
            eventOnClick(position)
            val params: LayoutParams = viewHolder.bottomLayout.layoutParams as LayoutParams
            if (viewHolder.showInfo) {
                viewHolder.bottomLayout.visibility = View.INVISIBLE
                viewHolder.showInfo = false
                params.height = 0
                viewHolder.bottomLayout.layoutParams = params

            } else {
                viewHolder.bottomLayout.visibility = View.VISIBLE
                viewHolder.showInfo = true
                params.height = MATCH_PARENT
                viewHolder.bottomLayout.layoutParams = params
            }
        }
    }
    fun eventOnClick(index: Int){
        Log.d("test", "Event Clicked. url: ${dataSet!![index].url}")
    }
    override fun getItemCount() = dataSet!!.size

}

