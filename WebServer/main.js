//express는 웹 애플리케이션 개발하는데 가장 많이 사용되는 프레임워크
const express = require('express');
const app = express();
const port = 8080; 
var http = require('http').createServer(app);
const ip = require('ip');
const querystring = require('querystring');
const mongoose = require('mongoose');

//브라우저와 서버 간의 실기간,양방향, 이벤트 기반 통신을 가능하게 해주는 Socket 통신 라이브러리
//소켓 통신을 위한 모듈
var io = require('socket.io')(http);


var temp = 0;
var humi = 0;
var state = 0;

http.listen(port, () => {
    console.log("Server is Running...");
    console.dir(ip.address());
});


mongoose.connect("mongodb+srv://dog:101401@smartdoghouse.uqibovh.mongodb.net/?retryWrites=true&w=majority",{

}).then(()=> console.log("DB Connected..."))
.catch((err)=>console.log(err));

const UserSchema = {
    ModelID : Number,
    Animal : Boolean,
    Species : Number,
    Age : Number,
    Amount : Number,
};

const DateSchema = 
{
    ModelID : Number,
    Birthday : Date,
    RegisterDay : Date
}

//스키마를 모델로 감싸주기
const User = mongoose.model('User',UserSchema);
//User 모델을 외부에서 쓸 수 있도록 export
//module.exports = {User};


app.get('/',(req,res)=> {
    res.writeHead(200,{'Content-Type' : 'text/html'});
    res.end(result);
});

app.post('/',(req,res)=> {

    console.log('Receive');
    res.end("I got a  Message");
   
    req.on('data',function(chunk){

        var data = querystring.parse(chunk.toString());
       
     
        //아두이노로부터 수신한 Data 변수에 저장.
        temp = data.temp;
        humi = data.humi;
        state = data.state;

        console.log(temp + " " + humi + " " + state);
/*
        const user = new User({
            ModelID : 0,
            Animal : true,
            Species : 0,
            Age : 1,
            Amount : 20.4
        });
        
        //나이가 바뀔때까지 섭취량을 계속해서 누적시킨다.
        user.save(function (err){
            if(err){
                console.log(err);
            }
            else{
                console.log("Stored data in docoument");
            }
        })

  */      

    });
    
});

//모바일 앱으로부터 connection 메세지를 받으면 이벤트 실행
io.on("connection",(socket)=>{
    console.log('Connencted');

    //request temp and humi 메세지를 받으면 이벤트 실행
    socket.on("request temp and humi",(obj)=>{
        
        console.log("Someone want temp");
        //모바일 앱으로 메세지 발송
        socket.emit('Send Temp',temp);
        socket.emit('Send Humi',humi);
        
    });
    //모바일 앱으로부터 메세지 수신
    socket.on("request infrared", (obj)=>{
        //모바일 앱에 메세지 발송
        socket.emit('Send Infrared',state);
    });

    

});  

