package fi.tuni.lippuluukku

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.tuni.lippuluukku.listModel.Keyword
import fi.tuni.lippuluukku.listModel.UserList

class KeywordRecyclerAdapter(val dataSet: MutableList<Keyword>?, val context: Activity, val preferences: Boolean) :
            RecyclerView.Adapter<KeywordRecyclerAdapter.ViewHolder>() {




        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val listItemName : TextView
            val listItemButton : ImageButton
            var attributesSet = false
            var pos = -1
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

            val actualPosition = dataSet?.get(viewHolder.layoutPosition)
            //println("KEYWORDRECYCLERADAPTER SIZE:" + dataSet?.size + ", POSITION: " + position + ", ADAPTERPOSITION: " + viewHolder.adapterPosition)
            viewHolder.listItemName.setText(dataSet!![position].name)// = dataSet!![position].name
            //Log.d("lol", "size: ${dataSet?.size}, position: ${position}")

            //if(!viewHolder.attributesSet) {
                if (context is PreferencesActivity) {
                    when (actualPosition!!.name) {

                        "Anything" -> viewHolder.listItemButton.visibility = INVISIBLE


                        else -> {
                            viewHolder.listItemButton.visibility = VISIBLE
                            viewHolder.listItemButton.setOnClickListener() {
                                val util = Util()
                                dataSet!!.removeAt(viewHolder.layoutPosition)
                                notifyItemRemoved(viewHolder.layoutPosition)
                                notifyItemRangeChanged(viewHolder.layoutPosition, dataSet.size)

                                util.saveUserData(context, UserList(util.loadUserData(context)!!.locations, dataSet))
                                notifyDataSetChanged()
                            }
                        }
                    }

                } else if (context is MainActivity) {
                    viewHolder.listItemButton.visibility = GONE
                    viewHolder.listItemName.setOnClickListener() {
                        //context.keywordSelected = dataSet[position].name!!
                        context.setKeyword(dataSet!![viewHolder.layoutPosition].name!!)
                    }
                }
            }
            //viewHolder.attributesSet = true



        override fun getItemCount() : Int {
                return dataSet!!.size
        }
    }



