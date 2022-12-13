package com.example.schoolproject

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolproject.databinding.DogdataBinding
import kotlin.properties.Delegates

class DogAdatper() : RecyclerView.Adapter<DogAdatper.DogHolder>(){

    private lateinit var context:Context
    private lateinit var activity: Activity
    private lateinit var myDogItem: ArrayList<MyDogItem>
    private lateinit var searchIndex:ArrayList<Int>
    private var size by Delegates.notNull<Int>()
    private lateinit var word:String
    private var mListener: DogAdatper.OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(v:View,position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    constructor(context: Context,activity: Activity,myDogItem : ArrayList<MyDogItem>,size:Int,word:String) : this(){
        this.context = context
        this.activity = activity
        this.myDogItem =myDogItem
        this.size = size
        this.word =word
    }
    constructor(context: Context,activity: Activity,myDogItem:ArrayList<MyDogItem>,searchIndex : ArrayList<Int>,size:Int,word:String):
            this(){
                this.context =context
                this.activity = activity
                this.myDogItem = myDogItem
                this.searchIndex = searchIndex
                this.size = size
                this.word = word
            }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.dogdata,parent,false)
        return DogHolder(view);
    }

    override fun onBindViewHolder(holder: DogHolder, position: Int) {
        if(word=="")
            wordEmpty(holder,position)
        else
            notWordEmpty(holder,position)
       //holder.binding.dogname.text = myDogItem[position].doglist[0].dogname
    }

    override fun getItemCount(): Int {
        return size
    }



    inner class DogHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var mBinding : DogdataBinding? = null
        val binding get() = mBinding!!

        init {
            initBinding()
            itemClick(itemView)
        }
        private fun initBinding(){
            mBinding = DogdataBinding.bind(itemView)
        }
        private fun itemClick(itemView: View)
        {
            itemView.setOnClickListener {
                val pos = bindingAdapterPosition
                if(pos!=RecyclerView.NO_POSITION)
                    mListener!!.onItemClick(it!!,pos)
            }
        }

    }

    private fun wordEmpty(holder:DogHolder,position: Int)
    {
        holder.binding.dogname.text = myDogItem[position].doglist[0].dogname
    }
    private fun notWordEmpty(holder : DogHolder,position: Int)
    {
        holder.binding.dogname.text = myDogItem[searchIndex[position]].doglist[0].dogname
    }
}

