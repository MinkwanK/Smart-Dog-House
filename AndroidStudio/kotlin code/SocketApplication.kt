package com.example.schoolproject


import java.net.URISyntaxException
import io.socket.client.IO
import io.socket.client.Socket
class SocketApplication {
    companion object{
        private lateinit var socket : Socket
        fun get():Socket{
            try{
                socket = IO.socket("http://192.168.150.84:8080")
            } catch(e:URISyntaxException){
                e.printStackTrace()
            }
            return socket
        }
    }
}