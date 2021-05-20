package fi.tuni.lippuluukku

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.tuni.lippuluukku.listModel.Location
import fi.tuni.lippuluukku.listModel.UserList

class LocationRecyclerAdapter(val dataSet: MutableList<Location>?, val context: Activity) :
            RecyclerView.Adapter<LocationRecyclerAdapter.ViewHolder>() {

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

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.list_item, viewGroup, false)
        view.setBackgroundColor(0x0D000000)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val actualPosition = dataSet?.get(viewHolder.layoutPosition)
        viewHolder.listItemName.text = dataSet!![position].name

        if (context is PreferencesActivity) {

            when (actualPosition!!.name) {

                "Here" -> viewHolder.listItemButton.visibility = INVISIBLE
                "Anywhere" -> viewHolder.listItemButton.visibility = INVISIBLE

                else -> {

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
            viewHolder.listLayout.setOnClickListener() {
                context.setLocation(dataSet[viewHolder.layoutPosition].name!!)
            }
        }
    }

    override fun getItemCount() : Int {
                return dataSet!!.size
    }
}