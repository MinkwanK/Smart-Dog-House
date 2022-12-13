package com.example.schoolproject

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.RequiresApi
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.timer
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.example.schoolproject.databinding.ActivityMainBinding
import kotlin.math.pow

/*
문제점:    자주 바뀌는 웹서버 ip
         사용자 정보를 어떻게 업데이트할지?
         사용자 정보를 어떻게 저장할지?
         품종 선택 액티비티 만들기


*/

class MainActivity : AppCompatActivity() {

    lateinit var msocket: Socket
    lateinit var temptextview:TextView
    lateinit var humitextview:TextView
    lateinit var infraredtxt : String
    lateinit var petnametxt : TextView
    lateinit var petagetxt : TextView
    lateinit var needcal : TextView
    lateinit var eatcal : TextView
    lateinit var petweighttxt : TextView
    lateinit var fanimage : ImageView
    lateinit var autoMode : Switch
    lateinit var fanstate : TextView
    lateinit var feedtimebtn : Button
    lateinit var feed_edtext : EditText

    private var Pet : Boolean = true
    private var dogspecies:String = ""
    private var weight : Double = 0.0
    private var age : Int = 0


    private var BirthMonth : Int = 0
    private var BirthDay : Int = 0
    private var FeedTime : Int = 0


    private lateinit var  binding : ActivityMainBinding
    private val CHANNEL_ID = "testChannel01"   // Channel for notification
    private var notificationManager: NotificationManager? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.activity = this

        temptextview = findViewById<TextView>(R.id.temptextview)
        humitextview = findViewById<TextView>(R.id.humitextview)
        petnametxt = findViewById<TextView>(R.id.dognametxt)
        petagetxt = findViewById<TextView>(R.id.dogagetxt)
        eatcal = findViewById(R.id.eatcaltxt)
        needcal = findViewById<TextView>(R.id.needcaltxt)
        petweighttxt = findViewById(R.id.dogweighttxt)
        fanimage = findViewById(R.id.FanImage)
        autoMode = findViewById<Switch>(R.id.automode)
        fanstate = findViewById(R.id.fanstate)
        feedtimebtn = findViewById<Button>(R.id.feedtimebtn)
        feed_edtext = findViewById(R.id.feedtime_edittext)

        infraredtxt = "출입 기록이 없습니다."
        msocket = SocketApplication.get()
        val current = LocalDateTime.now()
        if(current.monthValue == BirthMonth && (current.dayOfMonth + 1) == BirthDay) {
            App.prefs.myPetAge += 1;
        }
        //Load
        petnametxt.text = App.prefs.myPetName
        petagetxt.text = App.prefs.myPetAge.toString() + "살"
        petweighttxt.text = App.prefs.myPetWeight.toString() + "kg"
        Pet = App.prefs.myPet
        BirthMonth = App.prefs.myPetBirthMonth
        BirthDay = App.prefs.myPetBirthDay
        feed_edtext.setText(App.prefs.myPetFeedTime.toString())
        eatcal.text = App.prefs.myPetFeed.toString() + "kcal"

        FeedTime = App.prefs.myPetFeedTime
        weight = App.prefs.myPetWeight.toDouble()
        age = App.prefs.myPetAge


        var RER =  70 * (weight).pow(0.75)
        var PER  = 0.0
        //필요 칼로리

        PER = makekcal()

        //식사량 칼로리로 나타내기
        needcal.text = PER.toInt().toString() + "kcal"


        createNotifcationChannel(CHANNEL_ID,"testChannel","this is test channel")


        binding.infraredimage.setOnClickListener {
            Toast.makeText(this,infraredtxt,Toast.LENGTH_SHORT).show()
        }




        msocket.connect()

        msocket.emit("connection")

        var sec:Int = 0
        var sec2:Int = 0



        timer(period = 1000)
        {
            sec++
            sec2++;



            if(sec==5) //5초가 지날때마다 온습도 값 최신화
            {
                //Server에 이벤트 송신
                msocket.emit("request temp and humi")
                sec = 0;
            }
            if(sec2==1)
            {
                msocket.emit("request infrared")
                msocket.emit("request feed")
                sec2 = 0;
            }
        }



        //식사시간 설정

        msocket.emit("request feedtime",FeedTime)
        feedtimebtn.setOnClickListener {
            FeedTime = feed_edtext.text.toString().toInt()
            App.prefs.myPetFeedTime = FeedTime
            Toast.makeText(this,"사료 투여 시간 설정 완료 $FeedTime ",Toast.LENGTH_SHORT).show()
            msocket.emit("request feedtime",FeedTime)
        }


        var needdata = 0.0
        if(Pet)
            needdata = PER/3.7/1000
        else
            needdata = PER/4.0/1000

        if(Pet)
            msocket.emit("request need",needdata)
        else
            msocket.emit("request need",needdata)



        fanimage.setOnClickListener {
            msocket.emit("request fan")
            if(fanstate.text == "OFF" && autoMode.isChecked)
                fanstate.text = "ON"
            else if(fanstate.text == "ON" && autoMode.isChecked == true)
                fanstate.text = "OFF"
        }
        autoMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                msocket.emit("request analog")
            } else {
                msocket.emit("request auto")
            }
        }

        msocket.on("Send Temp") {args->
            if(args[0] != null)
            {
                //val counter = args[0] as Int
                runOnUiThread {
                    temptextview.text = args[0].toString() + "도"
                }
            }
        }

        msocket.on("Send Humi") {args->
            if(args[0] != null)
            {
                runOnUiThread {
                    humitextview.text = args[0].toString() + "%"
                }
            }
        }
        msocket.on("Send Infrared"){args->
            if(args[0] != null)
            {
                runOnUiThread {
                    if(args[0].toString() == "1")
                    {
                        val current = Date();

                        val formatType = SimpleDateFormat("MM월dd일 HH:mm:ss 에 움직임이 감지됐습니다.")
                        infraredtxt = formatType.format(current)
                        displayNotification()

                    }
                }
            }
        }
        msocket.on("Send Feed") { args ->
            if(args[0]!=null && args[0].toString().toFloat()>=0)
            {
                runOnUiThread {
                    if(Pet)
                    {
                        App.prefs.myPetFeed = (args[0].toString().toFloat() * 1000 * 3.7).toInt()
                        var temp = App.prefs.myPetFeed.toString()
                        eatcal.text =  temp + "kcal"
                    }
                    else {

                        App.prefs.myPetFeed = (args[0].toString().toFloat() * 1000 * 4.0).toInt()
                        var temp = App.prefs.myPetFeed.toString()
                        eatcal.text =
                            temp + "kcal"

                    }
                }
            }
        }





    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayNotification()
    {
        val notificationID = 66

        if(Pet == true) {
            val notification = Notification.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.pawprint)
                .setContentTitle("멍멍!")
                .setContentText(infraredtxt)
                .build()

            notificationManager?.notify(notificationID, notification)
        }
        else
        {
            val notification = Notification.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.pawprint)
                .setContentTitle("냐옹~")
                .setContentText(infraredtxt)
                .build()

            notificationManager?.notify(notificationID, notification)
        }
    }

    //채널은 알림을 카테고리별로 묶어 중요도를 설정하거나, 상태를 제어하는 역할
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotifcationChannel(channelId: String, name: String, channelDescription: String)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId,name,importance).apply{
                description = channelDescription
            }
            //Register the channel with system
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }

    override fun onBackPressed() {
        finishAffinity() //앱종료
        //super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_option,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Change_Weight->
             makedialog()
            R.id.Profile_Recreate->
                profilerecreate()


        }
        return super.onOptionsItemSelected(item)
    }

    fun makedialog() {

        var ChangeWeight_Edt : EditText = EditText(this)
        ChangeWeight_Edt.hint = "몸무게 입력란"
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("몸무게 변경")
            .setView(ChangeWeight_Edt)
            .setPositiveButton("결정",DialogInterface. OnClickListener { dialog, i ->
                App.prefs.myPetWeight = ChangeWeight_Edt.text.toString().toFloat()
                weight = App.prefs.myPetWeight.toDouble()
                var temp = makekcal().toInt().toString()
                needcal.setText(temp + "kcal")
                petweighttxt.text = ChangeWeight_Edt.text.toString() + "kg"
            })
            .setNegativeButton("취소",DialogInterface.OnClickListener { dialog, i ->
                dialog.dismiss()
            })
        builder.create()
        builder.show()
    }

    fun profilerecreate(){
        App.prefs.myPetFeed = 0
        App.prefs.myPetWeight = 0.0f
        App.prefs.myPet = true
        App.prefs.myPetFeedTime = 30
        App.prefs.myProfileCheck = false
        App.prefs.myPetName = ""
        App.prefs.myPetAge = 1
        App.prefs.myPetBirthDay =1
        App.prefs.myPetBirthMonth = 1
        intent = Intent(this,IntroActivity::class.java)
        startActivity(intent)
    }
    fun makekcal() : Double {
        var RER =  70 * (weight).pow(0.75)
        var PER : Double = 0.0

        if(Pet)
        {

            if(weight.toInt() >= 10)
            {
                if(age >= 10)
                {
                    PER = 1.2 * RER
                }
                else
                    PER = 1.8 * RER
            }
            else if(10<= weight.toInt() &&weight.toInt() < 25)
            {
                if(age>=7)
                {
                    PER = 1.2 * RER
                }
                else
                {
                    PER = 1.8 * RER
                }
            }
            else
            {
                if(age >=6)
                {
                    PER = 1.2 * RER
                }
                else
                    PER = 1.8 * RER
            }
        }
        else
        {
            if(age >= 10)
            {
                PER = 0.7 * RER
            }
            else
                PER = 1.4 * RER

        }

        return PER
    }




}