package fi.tuni.lippuluukku

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.tuni.lippuluukku.listModel.Keyword

class KeywordRecyclerAdapter(val dataSet: MutableList<Keyword>?, val context: Activity) :
            RecyclerView.Adapter<KeywordRecyclerAdapter.ViewHolder>() {


        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val listItemName : TextView
            init {
                listItemName = view.findViewById(R.id.list_item_name)

            }
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.list_item, viewGroup, false)
            view.setBackgroundColor(0x26000000)
            return ViewHolder(view)
        }



        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            println(dataSet?.size)
            viewHolder.listItemName.text = dataSet!![position].name
            Log.d("test", "dataset size : ${dataSet?.size}")


        }

        override fun getItemCount() : Int {
                return dataSet!!.size
        }
    }


