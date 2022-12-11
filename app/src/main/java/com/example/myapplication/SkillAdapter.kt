package com.example.myapplication

import android.location.GnssAntennaInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class SkillAdapter(private val skillList:ArrayList<String>):
    RecyclerView.Adapter<SkillAdapter.MyViewHolder>(){

    private lateinit var mListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickListener){
        mListener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.skills,parent,false)
        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder:MyViewHolder, position:Int) {
        val currentitem=skillList[position]
        holder.skill.text=currentitem
    }

    override fun getItemCount(): Int {
        return skillList.size
    }


    class MyViewHolder(itemView: View,listener:onItemClickListener):RecyclerView.ViewHolder(itemView){
        val skill:TextView=itemView.findViewById(R.id.allSkill)
        init {

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    }

