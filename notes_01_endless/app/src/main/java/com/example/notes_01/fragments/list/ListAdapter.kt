package com.example.notes_01.fragments.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_01.R

import com.example.notes_01.model.User
import com.example.notes_01.databinding.CustomRowBinding



class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private  var user_list = emptyList<User>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }


    override fun getItemCount(): Int {
        return user_list.size
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = user_list[position]

        val dateView: TextView = holder.itemView.findViewById(R.id.date_view)
        val timeView: TextView = holder.itemView.findViewById(R.id.time_view)
        val titleView: TextView = holder.itemView.findViewById(R.id.title_view)
        val FirstLineView: TextView = holder.itemView.findViewById(R.id.FirstLine_view)
        val RowLayout: View = holder.itemView.findViewById(R.id.rowLayout)

        if ((currentItem.note_update_day != "Пусто")&&(currentItem.note_update_time != "Пусто")){
            dateView.text = currentItem.note_update_day
            timeView.text = currentItem.note_update_time
        }else{
            dateView.text = currentItem.note_date
            timeView.text = currentItem.note_time
        }

        titleView.text = currentItem.note_title
        FirstLineView.text = currentItem.note_text
        RowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }



    }
    fun setData(user:List<User>){
        this.user_list = user
        notifyDataSetChanged()
    }



}