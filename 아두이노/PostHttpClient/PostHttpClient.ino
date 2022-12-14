/**
   PostHTTPClient.ino

    Created on: 21.11.2016

*/
#include "HX711.h"
#include <DFRobot_DHT11.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ThreeWire.h>  
#include <RtcDS1302.h>
#include<Servo.h>
#include <ArduinoJson.h>
Servo servo; 

/* this can be run with an emulated server on host:
        cd esp8266-core-root-dir
        cd tests/host
        make ../../libraries/ESP8266WebServer/examples/PostServer/PostServer
        bin/PostServer/PostServer
   then put your PC's IP address in SERVER_IP below, port 9080 (instead of default 80):
*/
//#define SERVER_IP "192.168.25.27:8080" // PC address with emulation on host
#define SERVER_IP "192.168.150.84:8080"

#ifndef STASSID
#define STASSID "Kmk"
#define STAPSK  "alsrhks0805"
#define DHT11_PIN 16
#endif

DFRobot_DHT11 DHT;

//온습도 핀
int pin = 0;

int state = 0;

// 무게센서
const int LOADCELL_DOUT_PIN = 5;
const int LOADCELL_SCK_PIN = 16;
//시간 
const int RST = 13;
const int DATA = 12;
const int CLOCK = 14;
ThreeWire myWire(12,14,13); // IO, SCLK, CE
RtcDS1302<ThreeWire> Rtc(myWire);
float calibration_factor = 270000;
int mortor = 4;
int angle = 0; 
HX711 scale;


float need = 0.0f; //get()으로 받아올 거다.
bool dish = false;
float amount = 0.0f;
int SelectedTime = 30;
boolean FoodCheck = false;



//////////////////////////////////////////////////////////////////////////////////////
void setup() {

  servo.attach(mortor);

  servo.write(180);
  
  Rtc.Begin();

  Serial.begin(115200);

  WiFi.begin(STASSID, STAPSK);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected! IP address: ");
  //Serial.println(WiFi.localIP());

  pinMode(pin,INPUT);
    scale.begin(LOADCELL_DOUT_PIN, LOADCELL_SCK_PIN);

  scale.set_scale();

  scale.tare(); //Reset the scale to 0
}




void loop() {
  // wait for WiFi connection
  if ((WiFi.status() == WL_CONNECTED)) {

    WiFiClient client;
    HTTPClient http;

     scale.set_scale(calibration_factor); 
    Serial.print("Reading: ");
    amount = scale.get_units();
     Serial.println(amount,3);

     if (http.begin(client, "http://192.168.150.84:8080/")) {  // HTTP


      Serial.print("[HTTP] GET...\n");
      // start connection and send HTTP header
      int httpCode = http.GET();
      String result = http.getString();
      Serial.println(result);

    DynamicJsonDocument doc(1024);
    deserializeJson(doc, result);
    need = doc["need"];
    SelectedTime = doc["feedtime"];
    
      if (httpCode > 0) {
        // HTTP header has been send and Server response header has been handled
        Serial.printf("[HTTP] GET... code: %d\n", httpCode);

        // file found at server
        if (httpCode == HTTP_CODE_OK || httpCode == HTTP_CODE_MOVED_PERMANENTLY) {
          String payload = http.getString();
          Serial.println(payload);
        }
      } else {
        Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
      }

      http.end();
    } else {
      Serial.printf("[HTTP} Unable to connect\n");
    }


    
    Serial.print("[HTTP] begin...\n");
    
   
    RtcDateTime now = Rtc.GetDateTime();
       
    Serial.println(now.Second());
    
   
    if( SelectedTime <= now.Second() &&  now.Second() < SelectedTime + 10  ) //사료 지급 주기가 됐을 때! (원래는 시간 단위인데 시연이어서 초단위)
    {

      
      if(amount >= need-0.005) //사료 지급 주기가 됐는데 사료가 가득 차있을대
      {
        //dish = true; //밥을 줄 필요가 없다.
      }
      else if(0.010 < amount <need-0.005) //사료 지급 주기가 됐는데 사료가 좀 남았을 때
      {
        //dish = false; //밥을 줘야한다.
        //rest = need - amount;
        angle = -180;
        servo.write(angle);
      }
      else //사료통이 아예 비었을 때 
      {
        //dish = false;
        //rest = 0.0; 
        angle = -180;
        servo.write(angle);
      }
    }

    
  
    if(amount >= need - 0.005) //사료통 닫기
    {
       //dish = true;
       angle = 180;
       servo.write(angle);
    }
      
    
    //먹은 양 
    float Feed = 0.000f;
    Feed = need-amount;
    //Serial.println(amount,3);
    //Serial.println(Feed,3);
    
    // Node.js 로 구축한 웹서버의 주소 입력
    http.begin(client, "http://" SERVER_IP "/");
    //헤더에 보내고자 하는 데이터의 ContentType 명시
    http.addHeader("Content-Type", "application/json");
  
    Serial.print("[HTTP] POST...\n");
    // start connection and send HTTP header and body

    //온습도 값 읽기
    //DHT.read(DHT11_PIN);

    //적외선 센서 값 읽기
    state = digitalRead(pin);
    Serial.println(state);

    //int temp = DHT.temperature;
    //int humi = DHT.humidity;
    
    //웹서버에 Post 전송
   // int httpCode = http.POST("&temp=" + String(temp) + "&humi=" + String(humi) +  "&state=" + String(state));
    int httpCode;
    if(Feed>=0)
    httpCode = http.POST("&Feed=" + String(Feed,3) + "&state=" + String(state)  );


    // httpCode will be negative on error
    if (httpCode > 0) {
      // HTTP header has been send and Server response header has been handled
    //  Serial.printf("[HTTP] POST... code: %d\n", httpCode);

      // file found at server
      if (httpCode == HTTP_CODE_OK) {
        const String& payload = http.getString();
       // Serial.println("received payload:\n<<");
       // Serial.println(payload);
       // Serial.println(">>");
      }
    } else {
     // Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
    }

    http.end();
  }

  delay(1000);
}
