package com.example.schoolproject

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolproject.databinding.ActivityDogSpeciesBinding
import java.util.*
import kotlin.collections.ArrayList


class DogSpeciesActivity : AppCompatActivity() {

    private var mBinding: ActivityDogSpeciesBinding? = null
    private val binding get() = mBinding!!
    private lateinit var myDogItems: ArrayList<MyDogItem>
    private lateinit var dogadapter: DogAdatper
    private lateinit var searchIndexList: ArrayList<Int>

    private var PetName:String = ""
    private var PetAge:Int = 1
    private var Pet:Boolean = false
    private var PetWeight:Float = 0.0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_species)

        PetName = intent.getStringExtra("PetName").toString()
        PetAge = intent.getIntExtra("PetAge", 1)
        Pet = intent.getBooleanExtra("Pet", true)
        PetWeight = intent.getFloatExtra("PetWeight",0.0f)

        viewBinding()
        initialize()
        listAdd()
        recyclerViewConnection()
        wordInput()
        itemClick()


        binding.buttontogomain.setOnClickListener {
            intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }

    //뷰 바인딩
    private fun viewBinding() {
        mBinding = ActivityDogSpeciesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //초기화
    private fun initialize() {
        myDogItems = ArrayList()
        searchIndexList = ArrayList()
        binding.recyclerlist.layoutManager = LinearLayoutManager(this)
    }

    //리스트 추가
    private fun listAdd() {
        addItem("말티즈")
        addItem("시추")
        addItem("페키니즈")
        addItem("닥스훈트")
        addItem("치와와")
        addItem("요크셔테리어")
        addItem("토이푸들")
        addItem("미니어처푸들")
        addItem("미디엄푸들")
        addItem("스탠다드푸들")
        addItem("포메라니안")
        addItem("시바이누")
        addItem("미니어쳐슈나우져")
        addItem("제페니즈스피치")
        addItem("불독")
        addItem("웰시코기")
        addItem("보더콜리")
        addItem("코커스파니엘")
        addItem("비글")
        addItem("말라뮤트")
        addItem("자이언트말라뮤트")
        addItem("롯트와일러")
        addItem("러프콜리")
        addItem("사모예드")
        addItem("셰퍼드")
        addItem("허스키")
        addItem("골든리트리버")
        addItem("진돗개")
        addItem("삽살개")
        addItem("풍산개")
        addItem("경주개동경이")
        addItem("도베르만")




    }

    private fun addItem(dogname: String) {
        val listItemModel = ListDog()
        listItemModel.dogname = dogname

        val myDogItem = MyDogItem()
        val items = ArrayList<ListDog>()

        items.add(listItemModel)
        myDogItem.doglist = items

        myDogItems.add(myDogItem)
    }

        private fun recyclerViewConnection() {
            dogadapter = DogAdatper(this,this,myDogItems,myDogItems.size,binding.DogSeachEditText.text.toString())
            binding.recyclerlist.adapter = dogadapter
        }

    private fun wordInput() {
        binding.DogSeachEditText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val str = binding.DogSeachEditText.text.toString().lowercase()
                filterWord(str)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun filterWord(word:String){
        searchIndexList.clear()

        if(binding.DogSeachEditText.text.toString() == "")
        {
           dogadapter = DogAdatper(this,this,myDogItems,myDogItems.size,word)
            binding.recyclerlist.adapter = dogadapter
            itemClick()
        }else{
            for(i in 0 until myDogItems.size)
            {
                if(myDogItems.get(i).doglist[0].dogname.lowercase().contains(word)){
                    searchIndexList.add(i)
                }
            }
            dogadapter = DogAdatper(this,this,myDogItems,searchIndexList,searchIndexList.size,word)

            binding.recyclerlist.adapter  = dogadapter

            filterWordClick()
        }
    }
    private fun itemClick(){
        dogadapter.setOnItemClickListener(object : DogAdatper.OnItemClickListener{
            override fun onItemClick(v: View, position: Int) {
                val builder = AlertDialog.Builder(this@DogSpeciesActivity)
                builder
                    .setTitle("강아지 품종 선택")
                    .setMessage(myDogItems[position].doglist[0].dogname + "를 선택하셨습니다.")
                    .setPositiveButton("결정",DialogInterface.OnClickListener { dialog, i ->
                        intent = Intent(this@DogSpeciesActivity,DatePickerActivity::class.java)
                        intent.putExtra("PetName", PetName)
                        intent.putExtra("PetAge", PetAge)
                        intent.putExtra("Pet", Pet)
                        intent.putExtra("SpeciesName",myDogItems[position].doglist[0].dogname)
                        intent.putExtra("PetWeight",PetWeight)
                        startActivity(intent)
                    })
                    .setNegativeButton("취소",DialogInterface.OnClickListener { dialog, i ->
                        dialog.dismiss()
                    })
                builder.create()
                builder.show()


            }
        })
    }

    private fun filterWordClick(){
        dogadapter.setOnItemClickListener(object : DogAdatper.OnItemClickListener{
            override fun onItemClick(v: View, position: Int) {
                Toast.makeText(this@DogSpeciesActivity,"Click",Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(this@DogSpeciesActivity)
                builder
                    .setTitle("강아지 품종 선택")
                    .setMessage(myDogItems[searchIndexList[position]].doglist[0].dogname + "를 선택하셨습니다.")
                    .setPositiveButton("결정",DialogInterface.OnClickListener { dialog, i ->
                        intent = Intent(this@DogSpeciesActivity,DatePickerActivity::class.java)
                        intent.putExtra("PetName", PetName)
                        intent.putExtra("PetAge", PetAge)
                        intent.putExtra("Pet", Pet)
                        intent.putExtra("SpeciesName",myDogItems[position].doglist[0].dogname)
                        intent.putExtra("PetWeight",PetWeight)
                        startActivity(intent)
                    })
                    .setNegativeButton("취소",DialogInterface.OnClickListener { dialog, i ->
                            dialog.dismiss()
                    })
                builder.create()
                builder.show()


            }
        })
    }





}