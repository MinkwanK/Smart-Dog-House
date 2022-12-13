/**
   PostHTTPClient.ino

    Created on: 21.11.2016

*/

#include <DFRobot_DHT11.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>


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
#define DHT11_PIN 4
#endif

DFRobot_DHT11 DHT;

//온습도 핀

int FanA = 16;
int FanB = 5;
bool automode = true;
bool fan = false;
int AA = 14;
int AB = 12;
int sensor = 0;
int value = 0;






//////////////////////////////////////////////////////////////////////////////////////
void setup() {

  Serial.begin(115200);

  WiFi.begin(STASSID, STAPSK);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected! IP address: ");
  //Serial.println(WiFi.localIP());


   pinMode(FanA,OUTPUT);
   pinMode(FanB,OUTPUT);
   pinMode(AA, OUTPUT);  // AA를 출력 핀으로 설정
   pinMode(AB, OUTPUT);  // AB를 출력 핀으로 설정

   digitalWrite(FanA,HIGH);
   digitalWrite(FanB,HIGH);
      

}

void loop() {
  // wait for WiFi connection
  value = analogRead(sensor);
  int water_level =map(value,0,1024,0,300);
  
    if(water_level<100){
  digitalWrite(AA, HIGH);  // 정방향으로 모터 회전
  digitalWrite(AB, LOW); // 5초동안 상태 유지(1000ms = 5s)
    }
    else
    {
      digitalWrite(AA, LOW);  // 정방향으로 모터 회전
      digitalWrite(AB, LOW); // 5초동안 상태 유지(1000ms = 5s)
    }
    
  if ((WiFi.status() == WL_CONNECTED)) {

    WiFiClient client;
    HTTPClient http;
    
    Serial.print("[HTTP] begin...\n");

//http.get() 시작
    if (http.begin(client, "http://192.168.150.84:8080")) {  // HTTP


      Serial.print("[HTTP] GET...\n");
      // start connection and send HTTP header
      int httpCode = http.GET();

      String result = http.getString();

      DynamicJsonDocument doc(1024);
      deserializeJson(doc, result);
      const char* jsondata;
      fan = doc["Fan"];
      automode = doc["automode"];
      

      //수동모드에서는 꼭 팬을 끄고 자동모드로 변환해야한다.
  
      // httpCode will be negative on error
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

//http.get()끝!
    

    // Node.js 로 구축한 웹서버의 주소 입력
    http.begin(client, "http://" SERVER_IP "/");
    //헤더에 보내고자 하는 데이터의 ContentType 명시
    http.addHeader("Content-Type", "application/json");
  
    Serial.print("[HTTP] POST...\n");
    // start connection and send HTTP header and body

    //온습도 값 읽기
    DHT.read(DHT11_PIN);
    
 

    int temp = DHT.temperature;
    int humi = DHT.humidity;
    Serial.println(temp);
    Serial.println(humi); 

    //fan이 true이면 무조건 선풍기 작동
    //fan이 false이면 무조건 선풍기 끔.

      if(automode == false)
      {
       if(fan == true)
       {
       digitalWrite(FanA,LOW);
      digitalWrite(FanB,HIGH);
      }
      else
      {
      digitalWrite(FanA,HIGH);
      digitalWrite(FanB,HIGH);
      }
    }
   
  if(automode==true)
 {
    if(temp >30)
    {
      digitalWrite(FanA,LOW);
      digitalWrite(FanB,HIGH);
    }
    else if(temp <=30)
    {
          digitalWrite(FanA,HIGH);
      digitalWrite(FanB,HIGH);
    }
}
 

    
    //웹서버에 Post 전송
   int httpCode = http.POST("&temp=" + String(temp) + "&humi=" + String(humi));

  
  



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
