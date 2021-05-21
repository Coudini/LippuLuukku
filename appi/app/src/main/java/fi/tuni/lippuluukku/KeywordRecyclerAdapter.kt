package fi.tuni.lippuluukku

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.tuni.lippuluukku.listModel.Keyword
import fi.tuni.lippuluukku.listModel.UserList

//Adapter for RecyclerView listing Keywords from MutableList holding Keyword data
class KeywordRecyclerAdapter(val dataSet: MutableList<Keyword>?, val context: Activity) :
            RecyclerView.Adapter<KeywordRecyclerAdapter.ViewHolder>() {

    // Introduce all needed variables
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val listItemName : TextView
        val listItemButton : ImageButton
        val listLayout : LinearLayout

        init {
            listItemName = view.findViewById(R.id.list_item_name)
            listItemButton = view.findViewById(R.id.list_item_button)
            listLayout = view.findViewById(R.id.list_item_layout)
            }
        }

        // Set backgrounds for all the views and return a ViewHolder for them
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.list_item, viewGroup, false)
            view.setBackgroundColor(0x0D000000)
            return ViewHolder(view)
        }

        // Set up all the attributes for each View
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Actual position of the view inside RecyclerView
            //      (RecyclerView refreshes the position-Index every now and then when listing items)
            val actualPosition = dataSet?.get(viewHolder.layoutPosition)
            viewHolder.listItemName.setText(dataSet!![position].name)// = dataSet!![position].name

            // Check if context is PreferencesActivity, same adapter is used in MainActivity with different visuals
            if (context is PreferencesActivity) {

                when (actualPosition!!.name) {

                    // Prevent user from deleting the 'Anything' -Keyword
                    "Anything" -> viewHolder.listItemButton.visibility = INVISIBLE

                    // Else make delete-button visible and set OnClickListener on it
                    else -> {

                        viewHolder.listItemButton.visibility = VISIBLE
                        viewHolder.listItemButton.setOnClickListener() {

                            //Util class holding helper functions for getting urls and manipulating data on User device
                            val util = Util()

                            // Remove item from dataSet on the ACTUAL position (viewHolder.layoutPosition)
                            dataSet.removeAt(viewHolder.layoutPosition)

                            // Notify RecyclerView that data has changed
                            notifyItemRemoved(viewHolder.layoutPosition)
                            notifyItemRangeChanged(viewHolder.layoutPosition, dataSet.size)
                            notifyDataSetChanged()

                            // Update UserList after removing an item
                            util.saveUserData(context, UserList(util.loadUserData(context)!!.locations, dataSet))
                        }
                    }
                }
            }

            // Check if context is MainActivity, same adapter is used in PreferencesActivity with different visuals
            else if (context is MainActivity) {

                // Hide delete-button used in PreferencesActivity
                viewHolder.listItemButton.visibility = GONE

                // Set listener on current layout for setting the value for Keyword in MainActivity
                viewHolder.listLayout.setOnClickListener() {
                    context.setKeyword(dataSet[viewHolder.layoutPosition].name!!)
                }
            }
        }

        // dataSet should never be null as all the values cannot be deleted by user
        // In case UserList-data is not found, a new one is created with existing values
        override fun getItemCount() : Int {
            return dataSet!!.size
        }
    }



