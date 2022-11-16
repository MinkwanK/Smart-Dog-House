package com.example.schoolproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale.filter

class DogSpeciesActivity : AppCompatActivity() {

    lateinit var buttontogomain:Button

    lateinit var rvdog : RecyclerView
    lateinit var DogAdapter : DogAdapter

    val dataArray:ArrayList<String> = ArrayList() //빈 배열 목록 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_species)

        var PetName = intent.getStringExtra("PetName")
        var PetAge: Int = intent.getIntExtra("PetAge", 1)
        var Pet: Boolean = intent.getBooleanExtra("Pet", true)
        rvdog = findViewById(R.id.rv_dog)
        addDataArray()

        rvdog.layoutManager = LinearLayoutManager(this) //레이아웃 매니저 생성
        rvdog.adapter = DogAdapter(dataArray,this)

        buttontogomain = findViewById(R.id.buttontogomain)

        buttontogomain.setOnClickListener {
            intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }



    }

    private fun addDataArray() {
        dataArray.add("말티즈")
        dataArray.add("치와와")
        dataArray.add("푸들")
        dataArray.add("진돗개")
        dataArray.add("골든리트리버")
        dataArray.add("레보라도리트리버")
        dataArray.add("시바견")
    }

}