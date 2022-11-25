const express = require('express');
const app = express();
var http = require('http').createServer(app);
const port = 8080; 
const ip = require('ip');
const mongoose = require('mongoose');


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
for(var  i =0; i<1000;i++)
{
    var Species = 1
    var Weight = SelectRandomWeight(Species).toFixed(2);
    console.log("무게: " + Weight);
    var Age = SelectRandomAge(Species);
    console.log("나이: " + Age);
    var Amount = SelectRandomAmount(Species,Weight,Age).toFixed(2);
    console.log("일일 섭취량: " + Amount + "g")

    const user = new User({
        DBAnimal : true,
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
//품종의 최대 최소 무게에 따른 Random 무게 결정
function SelectRandomWeight(species)
{
    switch(species)
    {
        case 1: //말티즈
        return Math.random() * (4.0 - 2.7) + 2.7;
    }

}
//품종의 종에 따른 나이 결정
function SelectRandomAge(species)
{

    min = Math.ceil(1);
    max = Math.floor(15);
    switch(species)
    {
        case 1: //말티즈
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }
}

//품종의 일일 섭취 사료량 결정 
function SelectRandomAmount(species,weight,age)
{
    var RER = 0.0
    var DER = 0.0;
    switch(species)
    {
        case 1: //말티즈
        RER = 70 * weight ^0.75;
        if(age>=10)
        {
            PER = 1.2 * RER;
        }
        else
            PER = 1.8 * RER;
        
        
        return PER / 3.7 ; //1g당 3.7 kcal다. 필요한 칼로리 / 3.7kcal 하면 필요한 그램수가 나온다.
    }
}

/* 
강아지의 범위 내 Random 무게 결정 -> random old에 무게에 맞는 사료량 Data 삽입
-> 노령견 시점부터 노령견에 맞는 사료량 Data 삽입

품종별로 1000개의 Random 데이터 삽입 (즉, 무게가 다른 1000개의 강아지가 들어간다.)

품종별 평균 무게를 파악해서 제일 많이 키우는 반려견 부터 기입한다.

성견 도달 시기 <그냥 다 12개월로 잡자> 중소형견 10~15년 대형견 수명 10~12년
노령견의 기준 12~13세

4kg      생후 8개월     초소형견
5~10kg   생후 10개월    소형견
11~25kg  생후 12개월    중형견
26~44kg  생후 15개월    대형견  
45kg이상 생후 18~24개월 초대형견

개의 생일이 지나면, 나이를 올리기.
생일을 모른다 하면 1월1일이 생일.


노령견
소형견 10살
중대형 7살
초대형 6살
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






