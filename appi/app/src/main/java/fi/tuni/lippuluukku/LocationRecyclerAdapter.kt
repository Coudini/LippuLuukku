package fi.tuni.lippuluukku

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.tuni.lippuluukku.listModel.Keyword
import fi.tuni.lippuluukku.listModel.Location
import fi.tuni.lippuluukku.listModel.UserList

class LocationRecyclerAdapter(val dataSet: MutableList<Location>?, val context: Activity) :
            RecyclerView.Adapter<LocationRecyclerAdapter.ViewHolder>() {


        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val listItemName : TextView
            val listItemButton : ImageButton
            var attributesSet = false
            init {
                listItemName = view.findViewById(R.id.list_item_name)
                listItemButton = view.findViewById(R.id.list_item_button)
            }
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.list_item, viewGroup, false)
            view.setBackgroundColor(0x26000000)
            return ViewHolder(view)
        }



        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            //println(dataSet?.size)
            val actualPosition = dataSet?.get(viewHolder.layoutPosition)
            viewHolder.listItemName.text = dataSet!![position].name
            //Log.d("test", "dataset size : ${dataSet?.size}")

            //if(!viewHolder.attributesSet) {
                if (context is PreferencesActivity) {
                    when (actualPosition!!.name) {
                        "Here" -> viewHolder.listItemButton.visibility = INVISIBLE
                        "Anywhere" -> viewHolder.listItemButton.visibility = INVISIBLE
                        else -> { // Note the block
                            viewHolder.listItemButton.setOnClickListener() {
                                val util = Util()
                                dataSet.removeAt(viewHolder.layoutPosition)
                                notifyItemRemoved(viewHolder.layoutPosition)
                                notifyItemRangeChanged(viewHolder.layoutPosition, dataSet.size)
                                notifyDataSetChanged()
                                util.saveUserData(context, UserList(dataSet, util.loadUserData(context)!!.keywords))
                            }
                        }
                    }
                } else if (context is MainActivity) {
                    viewHolder.listItemButton.visibility = GONE
                    viewHolder.listItemName.setOnClickListener(){
                        context.setLocation(dataSet[viewHolder.layoutPosition].name!!)
                    }
                }
            //}
            //viewHolder.attributesSet = true

        }

        override fun getItemCount() : Int {
                return dataSet!!.size
        }
    }


