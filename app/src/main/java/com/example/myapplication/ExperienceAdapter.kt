package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ExperienceAdapter(private val ExperienceList:ArrayList<Experience>):
RecyclerView.Adapter<ExperienceAdapter.MyViewHolder>() {


    private lateinit var mListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickListener){
        mListener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.experience,parent,false)
        return ExperienceAdapter.MyViewHolder(itemView,mListener)
    }
    override fun onBindViewHolder(holder: ExperienceAdapter.MyViewHolder, position:Int) {
        val currentitem = ExperienceList[position]
        holder.JOB.text=currentitem.JobTitle
        holder.COMPANY.text=currentitem.company
        holder.TIME.text=currentitem.time

    }




    override fun getItemCount(): Int {
        return ExperienceList.size
    }

    class MyViewHolder(itemView: View,listener: ExperienceAdapter.onItemClickListener):
            RecyclerView.ViewHolder(itemView){
                val JOB:TextView=itemView.findViewById(R.id.job_title)
                val COMPANY:TextView=itemView.findViewById(R.id.company_name)
                val TIME:TextView=itemView.findViewById(R.id.time_worked)
        init {

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

            }

}