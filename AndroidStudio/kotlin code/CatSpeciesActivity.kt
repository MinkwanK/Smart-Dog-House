package com.example.schoolproject

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schoolproject.databinding.ActivityCatSpeciesBinding


class CatSpeciesActivity : AppCompatActivity() {

    private var mBinding: ActivityCatSpeciesBinding? = null
    private val binding get() = mBinding!!
    private lateinit var myCatItems: ArrayList<MyCatItem>
    private lateinit var catadapter: CatAdapter
    private lateinit var searchIndexList: ArrayList<Int>

    private var PetName:String = ""
    private var PetAge:Int = 1
    private var Pet:Boolean = false
    private var PetWeight:Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_species)

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
        mBinding = ActivityCatSpeciesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //초기화
    private fun initialize() {
        myCatItems = ArrayList()
        searchIndexList = ArrayList()
        binding.recyclerlist.layoutManager = LinearLayoutManager(this)
    }

    //리스트 추가
    private fun listAdd() {
        addItem("노르웨이숲")
        addItem("러시안블루")
        addItem("먼치킨")
        addItem("페르시안")
        addItem("스코티시폴드")
        addItem("아메리칸쇼트헤어")
        addItem("코리안쇼트헤어")
        addItem("터키시앙고라")
        addItem("스핑크스")
        addItem("브리티시숏헤어")
        addItem("메인쿤")
        addItem("시암고양이")
        addItem("렉돌")
        addItem("아비시니안")
        addItem("벵골 ")


    }

    private fun addItem(catname: String) {
        val listItemModel = ListCat()
        listItemModel.catname = catname

        val myCatItem = MyCatItem()
        val items = ArrayList<ListCat>()

        items.add(listItemModel)
        myCatItem.catlist = items

        myCatItems.add(myCatItem)
    }

    private fun recyclerViewConnection() {
        catadapter = CatAdapter(this,this,myCatItems,myCatItems.size,binding.CatSeachEditText.text.toString())
        binding.recyclerlist.adapter = catadapter
    }

    private fun wordInput() {
        binding.CatSeachEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val str = binding.CatSeachEditText.text.toString().lowercase()
                filterWord(str)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun filterWord(word:String){
        searchIndexList.clear()

        if(binding.CatSeachEditText.text.toString() == "")
        {
            catadapter = CatAdapter(this,this,myCatItems,myCatItems.size,word)
            binding.recyclerlist.adapter = catadapter
            itemClick()
        }else{
            for(i in 0 until myCatItems.size)
            {
                if(myCatItems.get(i).catlist[0].catname.lowercase().contains(word)){
                    searchIndexList.add(i)
                }
            }
            catadapter = CatAdapter(this,this,myCatItems,searchIndexList,searchIndexList.size,word)

            binding.recyclerlist.adapter  = catadapter
            filterWordClick()
        }
    }
    private fun itemClick(){
        catadapter.setOnItemClickListener(object : CatAdapter.OnItemClickListener{
            override fun onItemClick(v: View, position: Int) {
                val builder = AlertDialog.Builder(this@CatSpeciesActivity)
                builder
                    .setTitle("강아지 품종 선택")
                    .setMessage(myCatItems[position].catlist[0].catname + "를 선택하셨습니다.")
                    .setPositiveButton("결정", DialogInterface.OnClickListener { dialog, i ->
                        intent = Intent(this@CatSpeciesActivity,DatePickerActivity::class.java)
                        intent.putExtra("PetName", PetName)
                        intent.putExtra("PetAge", PetAge)
                        intent.putExtra("Pet", Pet)
                        intent.putExtra("SpeciesName",myCatItems[position].catlist[0].catname)
                        intent.putExtra("PetWeight",PetWeight)
                        startActivity(intent)
                    })
                    .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, i ->
                        dialog.dismiss()
                    })
                builder.create()
                builder.show()


            }
        })
    }
    private fun filterWordClick(){
       catadapter.setOnItemClickListener(object : CatAdapter.OnItemClickListener{
            override fun onItemClick(v: View, position: Int) {
                Toast.makeText(this@CatSpeciesActivity,"Click", Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(this@CatSpeciesActivity)
                builder
                    .setTitle("강아지 품종 선택")
                    .setMessage(myCatItems[searchIndexList[position]].catlist[0].catname + "를 선택하셨습니다.")
                    .setPositiveButton("결정",DialogInterface.OnClickListener { dialog, i ->
                        intent = Intent(this@CatSpeciesActivity,DatePickerActivity::class.java)
                        intent.putExtra("PetName", PetName)
                        intent.putExtra("PetAge", PetAge)
                        intent.putExtra("Pet", Pet)
                        intent.putExtra("SpeciesName",myCatItems[position].catlist[0].catname)
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