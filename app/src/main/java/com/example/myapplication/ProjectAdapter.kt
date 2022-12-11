package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProjectAdapter(private val ProjectList:ArrayList<Project>):
RecyclerView.Adapter<ProjectAdapter.MyViewHolder>()
{

    private lateinit var mListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickListener){
        mListener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectAdapter.MyViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.projects,parent,false)
    return ProjectAdapter.MyViewHolder(itemView,mListener)
}

    override fun onBindViewHolder(holder:MyViewHolder, position:Int) {
        val currentitem=ProjectList[position]
        holder.title.text=currentitem.title
        holder.desc.text=currentitem.description

    }

        override fun getItemCount(): Int {
        return ProjectList.size
    }


    class MyViewHolder(itemView: View, listener: ProjectAdapter.onItemClickListener):
        RecyclerView.ViewHolder(itemView){
        val title:TextView=itemView.findViewById(R.id.allTitle)
        val desc:TextView=itemView.findViewById(R.id.allDescription)
        init {

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        }


}