package fi.tuni.lippuluukku


import android.app.Activity
import android.graphics.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        val eventName: TextView
        val eventType: TextView
        //val eventDescription : TextView
        var eventImage: ImageView

        init {
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


    fun eventOnClick(index: Int){
        Log.d("test", "Event Clicked. url: ${dataSet!![index].url}")
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        println(dataSet?.size)
        Log.d("test", "dataset size : ${dataSet?.size}")
        viewHolder.eventName.text = dataSet!![position].name
        viewHolder.eventType.text = dataSet!![position].classifications?.first()?.segment?.name
        //viewHolder.eventDescription.text = dataSet!![position].classifications?.first()?.genre?.name


        fun handleImage () {
            thread{
                val imageStream: InputStream = URL(dataSet!![position].images?.first()?.url).getContent() as InputStream
                val mirage = BitmapFactory.decodeStream(imageStream)
                context.runOnUiThread(Runnable{
                    viewHolder.eventImage.setImageBitmap(mirage)
                })
            }
        }
        handleImage()
        viewHolder.itemView.setOnClickListener(){
            eventOnClick(position)
            viewHolder.eventName.text = "Name: ${dataSet!![position].url}"
        }
    }
    override fun getItemCount() = dataSet!!.size

}

