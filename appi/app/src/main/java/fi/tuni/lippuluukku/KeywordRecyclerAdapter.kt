package fi.tuni.lippuluukku

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.tuni.lippuluukku.listModel.Keyword
import fi.tuni.lippuluukku.listModel.UserList

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

            viewHolder.listItemName.setOnClickListener(){
                val util = Util()
                var temp : UserList? = util.loadUserData(context)

                dataSet.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,dataSet.size)

                var test = util.loadUserData(context)
                var newData = UserList(test?.locations,dataSet)
                util.saveUserData(context,newData)
                println("UserData after removal: " + util.loadUserData(context).toString())

                /*
                var testi = temp?.keywords?.remove(dataSet[position])
                println("testitestifunkkari"+ testi.toString())


                var filtered = util.loadUserData(context)?.keywords?.filter{it.name == dataSet[position].name}
                println("testifunkkari: " + filtered.toString())
                var newData = UserList(temp!!.locations, filtered as MutableList<Keyword>?)
                println("NEWDATA AFTER CLICK: "+newData.toString())
                //util.saveUserData(context,newData)
*/
            }


        }

        override fun getItemCount() : Int {
                return dataSet!!.size
        }
    }


