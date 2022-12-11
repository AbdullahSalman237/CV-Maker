//package com.example.myapplication
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class InterestAdapter(private val InterestList:ArrayList<String>):
//    RecyclerView.Adapter<InterestAdapter.MyViewHolder>()
//{
//    private lateinit var mListener:onItemClickListener
//    interface onItemClickListener{
//        fun onItemClick(position: Int)
//    }
//
//    fun setOnItemClickListener(listener:onItemClickListener){
//        mListener=listener
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestAdapter.MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.interest,parent,false)
//        return MyViewHolder(itemView,mListener)
//    }
//
//    override fun onBindViewHolder(holder: InterestAdapter.MyViewHolder, position:Int) {
//        val currentitem=InterestList[position]
//        holder.interest.text=currentitem.toString()
//
//    }
//    override fun getItemCount(): Int {
//        return InterestList.size
//    }
//
//    class MyViewHolder(itemView: View,listener: SkillAdapter.onItemClickListener):RecyclerView.ViewHolder(itemView) {
//
//        val interest:TextView=itemView.findViewById(R.id.allInterests)
//        init {
//
//            itemView.setOnClickListener {
//                listener.onItemClick(adapterPosition)
//            }
//        }
//    }
//
//
//}