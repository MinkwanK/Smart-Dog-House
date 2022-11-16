package com.example.schoolproject

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
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
    lateinit var infrared:ImageView
    lateinit var infraredtxt : String

    private val CHANNEL_ID = "testChannel01"   // Channel for notification
    private var notificationManager: NotificationManager? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        temptextview = findViewById<TextView>(R.id.temptextview)
        humitextview = findViewById<TextView>(R.id.humitextview)
        infrared = findViewById(R.id.infraredimage)
        infraredtxt = "출입 기록이 없습니다."
        msocket = SocketApplication.get()


        createNotifcationChannel(CHANNEL_ID,"testChannel","this is test channel")

        infrared.setOnClickListener {
            Toast.makeText(this,infraredtxt,Toast.LENGTH_SHORT)
                .show()
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
                sec2 = 0;
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


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayNotification()
    {
        val notificationID = 66

        val notification = Notification.Builder(applicationContext,CHANNEL_ID)
            .setSmallIcon(R.drawable.pawprint)
            .setContentTitle("멍멍!")
            .setContentText(infraredtxt)
            .build()

        notificationManager?.notify(notificationID,notification)
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





}