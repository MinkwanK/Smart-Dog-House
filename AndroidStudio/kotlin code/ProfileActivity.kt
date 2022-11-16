package com.example.schoolproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var petnameEditText = findViewById<EditText>(R.id.PetName_editText)
        var petageEditText = findViewById<EditText>(R.id.PetAge_editText)
        var RadioGroup = findViewById<RadioGroup>(R.id.RadioGroup)
        var button = findViewById<Button>(R.id.button)

        //개는 true 고양이는 false
        var pet : Boolean = true;

        RadioGroup.setOnCheckedChangeListener { radioGroup, i ->
                when(i)
                {
                    R.id.DogButton -> pet = true;
                    R.id.CatButton -> pet = false;
                }

        }

        //이름,나이,종류 넘겨주기
        button.setOnClickListener {
            if( petnameEditText.text.toString().isNotEmpty() && petageEditText.text.toString().isNotEmpty()) {
                var intent = Intent(this, DogSpeciesActivity::class.java)

                intent.putExtra("PetName", petnameEditText.text.toString())
                intent.putExtra("PetAge", petageEditText.text.toString().toInt())
                intent.putExtra("Pet", pet)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this,"이름과 나이를 입력해주세요.",Toast.LENGTH_SHORT).show()
            }

        }
    }
}