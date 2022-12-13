package com.example.schoolproject


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolproject.databinding.CatdataBinding

import kotlin.properties.Delegates

class CatAdapter() : RecyclerView.Adapter<CatAdapter.CatHolder>(){

    private lateinit var context:Context
    private lateinit var activity: Activity
    private lateinit var myCatItem: ArrayList<MyCatItem>
    private lateinit var searchIndex:ArrayList<Int>
    private var size by Delegates.notNull<Int>()
    private lateinit var word:String
    private var mListener: CatAdapter.OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(v:View,position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    constructor(context: Context,activity: Activity,myCatItem : ArrayList<MyCatItem>,size:Int,word:String) : this(){
        this.context = context
        this.activity = activity
        this.myCatItem = myCatItem
        this.size = size
        this.word =word
    }
    constructor(context: Context,activity: Activity,myCatItem : ArrayList<MyCatItem>,searchIndex : ArrayList<Int>,size:Int,word:String):
            this(){
        this.context =context
        this.activity = activity
        this.myCatItem = myCatItem
        this.searchIndex = searchIndex
        this.size = size
        this.word = word
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.catdata,parent,false)
        return CatHolder(view);
    }

    override fun onBindViewHolder(holder: CatHolder, position: Int) {
        if(word=="")
            wordEmpty(holder,position)
        else
            notWordEmpty(holder,position)

    }

    override fun getItemCount(): Int {
        return size
    }



    inner class CatHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var mBinding : CatdataBinding? = null
        val binding get() = mBinding!!

        init {
            initBinding()
            itemClick(itemView)
        }
        private fun itemClick(itemView: View)
        {
            itemView.setOnClickListener {
                val pos = bindingAdapterPosition
                if(pos!=RecyclerView.NO_POSITION)
                    mListener!!.onItemClick(it!!,pos)
            }
        }
        private fun initBinding(){
            mBinding = CatdataBinding.bind(itemView)
        }
    }

    private fun wordEmpty(holder:CatHolder,position: Int)
    {
        holder.binding.catname.text = myCatItem[position].catlist[0].catname
    }
    private fun notWordEmpty(holder : CatHolder,position: Int)
    {
        holder.binding.catname.text = myCatItem[searchIndex[position]].catlist[0].catname
    }
}

