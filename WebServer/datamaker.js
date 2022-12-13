const express = require('express');
const app = express();
var http = require('http').createServer(app);
const port = 8080; 
const ip = require('ip');
const mongoose = require('mongoose')


http.listen(port, () => {
    console.log("Server is Running...");
    console.dir(ip.address());
});


mongoose.connect("mongodb+srv://dog:101401@smartdoghouse.uqibovh.mongodb.net/?retryWrites=true&w=majority",{

}).then(()=> console.log("DB Connected..."))
.catch((err)=>console.log(err));



const UserSchema = {
    DBAnimal : Boolean,
    DBSpecies : Number,
    DBAge : Number,
    DBAmount : Number,
};

const DateSchema = 
{
    ModelID : Number,
    Birthday : Date,
    RegisterDay : Date
}

//스키마를 모델로 감싸주기 첫번째는 Collection 이름
const User = mongoose.model('User',UserSchema);

//User 모델을 외부에서 쓸 수 있도록 export
//module.exports = {User};
var Animal = true;
if(Animal == true)
{
    for(var  i =1; i<=40;i++) //개 품종 저장
    {       

        for(var j =0;j<1000;j++) //품종마다 2000개의 data 넣기
        {
                
                var Species = i;
                console.log("품종: " + Species);
                var Weight = SelectRandomWeight(Animal,Species).toFixed(2);
                console.log("무게: " + Weight);
                var Age = SelectRandomAge(Weight);
                console.log("나이: " + Age);

                var Amount = SelectAmount(Weight,Age,Animal).toFixed(2);
                console.log("일일 섭취량: " + Amount + "g")

                const user = new User({
                    DBAnimal : Animal,
                    DBSpecies : Species,
                    DBAge : Age,
                    DBAmount : Amount
                });


                user.save(function (err){
                    if(err){
                        console.log(err);
                    }
                    else{
                        console.log("Stored data in docoument");
                    }
                })
            }
        }
}
else
{
    for(var  i =1; i<=20;i++) //고양이 품종 저장
    {       

        for(var j =0;j<1000;j++) //품종마다 1000개의 data 넣기
        {
                
                var Species = i;
                console.log("품종: " + Species);
                var Weight = SelectRandomWeight(Animal,Species).toFixed(2);
                console.log("무게: " + Weight);
                var Age = SelectRandomAge(Weight);
                console.log("나이: " + Age);

                var Amount = SelectAmount(Weight,Age,Animal).toFixed(2);
                console.log("일일 섭취량: " + Amount + "g")

                const user = new User({
                    DBAnimal : Animal,
                    DBSpecies : Species,
                    DBAge : Age,
                    DBAmount : Amount
                });


                user.save(function (err){
                    if(err){
                        console.log(err);
                    }
                    else{
                        console.log("Stored data in docoument");
                    }
                })
            }
        }
}
    


/*
    밑에서부터 데이터 저장 함수
*/

//품종의 최대 최소 무게에 따른 Random 무게 결정
function SelectRandomWeight(animal,species)
{
  if(animal == true)
  {
    switch(species)
    {
        case 1: //말티즈
        return Math.random() * (4.0 - 2.7) + 2.7;
        
        case 2: //시추
        return Math.random() * (8.1-4.5) + 4.5;

        case 3: //페키니즈
        return Math.random() * (6.4-3.2) + 3.2;

        case 4: //닥스훈트
        return Math.random() * (15.0-7.0) + 7.0;

        case 5: //치와와
        return Math.random() * (2.7-1.8) + 1.8;

        case 6: //요크셔테리어
        return Math.random() * (3.2-2.0) + 2.0;
        
        case 7: //토이푸들
        return Math.random() * (3.0-2.0) + 2.0;

        case 8: //미니어처푸들
        return Math.random() * (6.0-3.0) + 3.0;

        case 9: //미디엄푸들
        return Math.random() * (20.0-6.0) + 6.0;

        case 10: //스탠다드 푸들
        return Math.random() * (27.0-20.0) + 20.0;

        case 11: //포메라니안
        return Math.random() * (3.2-1.3) + 1.3;

        case 12: //시바이누
        return Math.random() * (14.0-7.0) + 7.0;

        case 13: //슈나우저
        return Math.random() * (8.0-4.5) + 4.5;

        case 14: //제페니즈스피치
        return Math.random() * (10.0-5.0) + 5.0 ;

        case 15: //불독
        return Math.random() * (25.0-20.0) + 20.0;

        case 16: //웰시코기
        return Math.random() * (17.0-10.0) + 10.0;

        case 17: //보더콜리
        return Math.random() * (23.0-16.0) + 16.0;

        case 18: //코커스파니엘
        return Math.random() * (16.0-9.0) + 9.0;

        case 19: //비글
        return Math.random() * (16.0-9.0) + 9.0;

        case 20: //말라뮤트
        return Math.random() * (50.0-30.0) + 30.0;

        case 21: //자이언트 말라뮤트
        return Math.random() * (73.0-50.0) + 50.0;

        case 22: //롯트와일러
        return Math.random() * (60.0-35.0) + 35.0;

        case 23: //러프콜리
        return Math.random() * (35.0-25.0) + 25.0;

        case 24: //사모예드
        return Math.random() * (30.0-16.0) + 16.0;

        case 25: //저먼셰퍼드
        return Math.random() * (43.0-23.0) + 23.0;

        case 26: //허스키
        return Math.random() * (28.0-16.0) + 16.0;

        case 27: //골든 리트리버
        return Math.random() * (40.0-25.0) + 40.0;

        case 28: //진돗개
        return Math.random() * (20.0-15.0) + 15.0;

        case 29: //삽살개
        return Math.random() * (21.0-17.0) + 17.0;

        case 30: //풍산개
        return Math.random() * (60.0-20.0) + 20.0;    
        
        case 31: //동경이
        return Math.random() * (18.0-16.0) + 16.0;
        
        case 32: // 도베르만
        return Math.random() * (45.0-30.0) + 30.0;   

        case 33: //아키타견
        return Math.random() * (50.0-32.0) + 32.0;   

        case 34: //복서
        return Math.random() * (35.0-25.0) + 25.0;   

        case 35: //비숑프리제
        return Math.random() * (8.2-5.4) + 5.4;  

        case 36: //도사견
        return Math.random() * (100.0-30.0) + 30.0;  

        case 37: //캉갈
        return Math.random() * (65-40) + 40;  

        case 38: //그레이하운드
        return Math.random() * (32-27) + 27;  

        case 39: //브뤼셀 그리펀
        return Math.random() * (5-3.5) + 3.5;
        
        case 40: //불테리어
        return Math.random() * (28-23) + 23;  


        
    }
}
else
{
    switch(species)
    {
        case 1: //노르웨이 숲 고양이
        return Math.random() * (7.0-5.5) + 5.5;  

        case 2: //랙돌
        return Math.random() * (11.0-2.5) + 2.5;

        case 3: //러시안블루
        return Math.random() * (4.5-3.5) + 3.5;

        case 4: //먼치킨
        return Math.random() * (4.9-3.2) + 3.2;

        case 5: //뱅갈
        return Math.random() * (6.8-3.6) + 3.6;

        case 6: //브리티시쇼트헤어
        return Math.random() * (10.0-5.0) + 5.0;

        case 7: //샴
        return Math.random() * (5.5-3.5) + 3.5;

        case 8: //스코티시폴드
        return Math.random() * (6.0-4.0) + 4.0;

        case 9: //스핑크스
        return Math.random() * (6.5-2.5) + 2.5;

        case 10: //아메리칸쇼트헤어
        return Math.random() * (5.5-4.0) + 4.0;

        case 11: //아비시니안
        return Math.random() * (6.0-4.0) + 4.0;

        case 12: //코리안쇼트헤어
        return Math.random() * (5.0-3.5) + 3.5;

        case 13: //터키시앙고라
        return Math.random() * (5.5-3.5) + 3.5;

        case 14: //페르시안
        return Math.random() * (5.0-4.0) + 4.0;

        case 15: //메인쿤
        return Math.random() * (10.0-4.0) + 4.0;

        case 16: //네벨룽
        return Math.random() * (6.5-3.5) + 3.5;

        case 17: //네바 마스커레이드
        return Math.random() * (9-5) + 5;

        case 18: //도메스틱 숏헤어
        return Math.random() * (5-3.5) + 3.5;   

        case 19: //라가머핀
        return Math.random() * (9.1-3.6) + 3.6;   

        case 20: //사바나
        return Math.random() * (18.0-4.0) + 4.0;   
    }
}

}
//무게에 따라 수명을 결정
function SelectRandomAge(weight)
{
    if(weight<10)
    {
        min = Math.ceil(1);
        max = Math.floor(15);
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }
    else if(10<=weight<25)
    {
        min = Math.ceil(1);
        max = Math.floor(13);
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }
    else{
        min = Math.ceil(1);
        max = Math.floor(12);
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }
}

function SelectAmount(weight,age,animal)
{
    RER = 70 * (weight ^0.75);
    if(animal == true) //개의 PER 계산
    {
        if(weight<10)
        {
            if(age>=10)
            {
                PER = 1.2 * RER;
            }
            else
                PER = 1.8 * RER;
        }
        else if(10<=weight<25)
        {
            if(age>=7)
            {
                PER = 1.2 * RER;
            }
            else
                PER = 1.8 * RER;
        }
        else
        {
            if(age>=6)
            {
                PER = 1.2 * RER;
            }
            else
            PER = 1.8 * RER;
        }
        PER = PER/3.7;
        return PER;
    }
    else //고양이의 PER 계산
    {
        if(age>=10)
            {
                PER = 0.7* RER;
            }
            else
                PER = 1.4 * RER;
        
        PER = PER/4.0;
        return PER;
    }
       
}



/* 
강아지의 범위 내 Random 무게 결정 -> random old에 무게에 맞는 사료량 Data 삽입
-> 노령견 시점부터 노령견에 맞는 사료량 Data 삽입

품종별로 1000개의 Random 데이터 삽입 (즉, 무게가 다른 1000개의 강아지가 들어간다.)

품종별 평균 무게를 파악해서 제일 많이 키우는 반려견 부터 기입한다.

성견 도달 시기 <그냥 다 12개월로 잡자>
중소형견 10~15년 대형견 수명 10~12년

노령견의 기준 12~13세

4kg      생후 8개월     초소형견
5~10kg   생후 10개월    소형견
11~25kg  생후 12개월    중형견
26~44kg  생후 15개월    대형견  
45kg이상 생후 18~24개월 초대형견

개의 생일이 지나면, 나이를 올리기.
생일을 모른다 하면 1월1일이 생일.


노령견
소형견 10살 (10kg미만)
중대형 7살  (10kg~25kg미만)
초대형 6살  (25kg이상)
*서울특별시수의사회*

개는 하루에 두끼 먹는다.

휴식 시 에너지 요구량 
RER(Resting Energy Requirements)
70 * 체중kg^0.75

1일 에너지 요구량
DER(Daily Energy Requirements)
중성화한 성견 1.6 * RER
중성화 x 성견 1.8 * RER
노령견       1.2~1.3 * RER



지금으로선 DateSchema가 필요없다. 개념적으로 필요하다.
개 사료, 고양이 사료 1kg 당 3700 칼로리다. 1g에  3.7kcal


1 말티즈 2.7~4kg

*/






