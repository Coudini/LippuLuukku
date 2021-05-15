package fi.tuni.lippuluukku


import androidx.recyclerview.widget.RecyclerView
import fi.tuni.lippuluukku.model.ResponseData
import fi.tuni.lippuluukku.model.Event




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(private val dataSet: MutableList<Event>) :
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventName: TextView

        init {
            eventName = view.findViewById(R.id.event_name)
            //textView2 = view.findViewById(R.id.bmi)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.event_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        //viewHolder.textView1.text = "Name: ${dataSet[position].name}"
        //viewHolder.textView2.text = "Bmi: ${dataSet[position].getBmi(dataSet[position].mass, dataSet[position].height).toString()}"
    }

    override fun getItemCount() = dataSet.size

}
