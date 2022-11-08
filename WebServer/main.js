//express는 웹 애플리케이션 개발하는데 가장 많이 사용되는 프레임워크
const express = require('express');
const app = express();
const port = 8080; 
var http = require('http').createServer(app);
const ip = require('ip');
const querystring = require('querystring');
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

app.get('/',(req,res)=> {
    res.send('Hi');
});

app.post('/',(req,res)=> {

    console.log('Receive');
    /*
    
    req.on('data',function(chunk){

        var data = querystring.parse(chunk.toString());
        //JSON 문자열의 구문을 분석하고, 그 결과에서 값이나 객체 생성
     
        res.end("I got a message");

         //키의 data를 받는다.
        temp = data.temp;
        humi = data.humi;
        state = data.state;

        console.log(temp + " " + humi + " " + state);

    });
    */
});

io.on("connection",(socket)=>{
    console.log('Connencted');


    socket.on("request temp and humi",(obj)=>{
        
        console.log("Someone want temp");
        socket.emit('Send Temp',temp);
        socket.emit('Send Humi',humi);
    });

    

});  

/*
app.listen(port,() => {
    console.log("Server is running...");
    console.dir ( ip.address() ); 

});
*/