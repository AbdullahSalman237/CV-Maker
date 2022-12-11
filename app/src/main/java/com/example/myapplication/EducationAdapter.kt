package com.example.myapplication

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class EducationAdapter(private val EducationList: ArrayList<Education>):
    RecyclerView.Adapter<EducationAdapter.MyViewHolder>() {

    private lateinit var mListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickListener){
        mListener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.education,parent,false)
        return EducationAdapter.MyViewHolder(itemView,mListener)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position:Int) {
        val currentitem = EducationList[position]
        holder.degree.text=currentitem.Degree.toString()
        holder.institute.text=currentitem.Institute.toString()
        holder.compltetion_year.text=currentitem.completionTime.toString()


    }

    override fun getItemCount(): Int {
        return EducationList.size
    }

    class MyViewHolder(itemView: View,listener: EducationAdapter.onItemClickListener):
        RecyclerView.ViewHolder(itemView){
        val degree: TextView =itemView.findViewById(R.id.degree)
        val institute: TextView =itemView.findViewById(R.id.institue)
        val compltetion_year:TextView =itemView.findViewById(R.id.completion_year)

        init {

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }



}

