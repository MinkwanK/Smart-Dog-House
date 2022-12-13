package com.example.schoolproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.example.schoolproject.databinding.ActivityDatePickerBinding
import com.example.schoolproject.databinding.ActivityDogSpeciesBinding
import java.util.*

class DatePickerActivity : AppCompatActivity() {

    private var PetName:String = ""
    private var PetAge:Int = 1
    private var Pet:Boolean = false
    private var SpeciesName:String = ""
    private var PetWeight:Float = 0.0f

    private var Birth_Month: Int = 0
    private var Birth_Day : Int = 0

    lateinit var today : Calendar
    lateinit var birthdaytxt : TextView
    private lateinit var binding : ActivityDatePickerBinding



    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_date_picker)
        binding.datePickeractivity= this

        PetName = intent.getStringExtra("PetName").toString()
        PetAge = intent.getIntExtra("PetAge", 1)
        PetWeight = intent.getFloatExtra("PetWeight",0.0f)
        Pet = intent.getBooleanExtra("Pet", true)
        SpeciesName = intent.getStringExtra("SpeciesName").toString()


        birthdaytxt = findViewById(R.id.Birthday_Text)
        birthdaytxt.text = PetName


        today = Calendar.getInstance()

        binding.MonthPicker.minValue = 1
        binding.MonthPicker.maxValue = 12
        binding.MonthPicker.value = today.get(Calendar.MONTH) +1

        Birth_Month = binding.MonthPicker.value
        App.prefs.myPetBirthMonth = binding.MonthPicker.value
        binding.DayPicker.minValue = 1
        binding.DayPicker.maxValue = 31

        Birth_Day = binding.DayPicker.value
        App.prefs.myPetBirthDay = binding.DayPicker.value

        binding.previousbutton.setOnClickListener {
            if(Pet==true) {
                intent = Intent(this, DogSpeciesActivity::class.java)
                startActivity(intent)
            }
            else
                intent = Intent(this,CatSpeciesActivity::class.java)
                startActivity(intent)


        }

        binding.buttontogomain2.setOnClickListener {
            intent = Intent(this,MainActivity::class.java)
            App.prefs.myPetName = PetName
            App.prefs.myPetAge = PetAge
            App.prefs.myPet = Pet
            App.prefs.myPetWeight = PetWeight
            App.prefs.myPetBirthMonth = Birth_Month
            App.prefs.myPetBirthDay = Birth_Day
            App.prefs.myProfileCheck = true
            startActivity(intent)
        }








    }

/*
    private fun saveData(){
        var pref = getSharedPreferences("pref",0)
        val petname = pref.edit()
        val petage = pref.edit()
        val pet = pref.edit()
        val speceisname = pref.edit()
        val birthmonth = pref.edit()
        val birthday = pref.edit()

        petname.putString("PetName",PetName)
        petage.putInt("PetAge",PetAge)
        pet.putBoolean("Pet",Pet)
        speceisname.putString("SpeciesName",SpeciesName)
        birthmonth.putInt("BirthMonth",today.get(Calendar.MONTH))
        birthday.putInt("BirthDay",today.get(Calendar.DAY_OF_MONTH))

    }

    override fun onDestroy() {
        super.onDestroy()
        saveData()
    }
*/

}