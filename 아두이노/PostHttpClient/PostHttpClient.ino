/**
   PostHTTPClient.ino

    Created on: 21.11.2016

*/

#include <DFRobot_DHT11.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

/* this can be run with an emulated server on host:
        cd esp8266-core-root-dir
        cd tests/host
        make ../../libraries/ESP8266WebServer/examples/PostServer/PostServer
        bin/PostServer/PostServer
   then put your PC's IP address in SERVER_IP below, port 9080 (instead of default 80):
*/
//#define SERVER_IP "192.168.25.27:8080" // PC address with emulation on host
#define SERVER_IP "192.168.25.27:8080"

#ifndef STASSID
#define STASSID "SK_WiFiA41B"
#define STAPSK  "1402004995"
#define DHT11_PIN 16
#endif

DFRobot_DHT11 DHT;
int pin = 5;
int state = 0;

void setup() {

  Serial.begin(115200);

  Serial.println();
  Serial.println();
  Serial.println();

  WiFi.begin(STASSID, STAPSK);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected! IP address: ");
  Serial.println(WiFi.localIP());

  pinMode(pin,INPUT);

}

void loop() {
  // wait for WiFi connection
  if ((WiFi.status() == WL_CONNECTED)) {

    WiFiClient client;
    HTTPClient http;

    Serial.print("[HTTP] begin...\n");
    // configure traged server and url
    http.begin(client, "http://" SERVER_IP "/"); //HTTP
    http.addHeader("Content-Type", "application/json");
  
    Serial.print("[HTTP] POST...\n");
    // start connection and send HTTP header and body

    //온습도 값 읽기
    DHT.read(DHT11_PIN);

    int temp = DHT.temperature;
    int humi = DHT.humidity;
    state = digitalRead(pin);
    int httpCode = http.POST("&temp=" + String(temp) + "&humi=" + String(humi));
  //+ "&state=" + String(state)
    // httpCode will be negative on error
    if (httpCode > 0) {
      // HTTP header has been send and Server response header has been handled
      Serial.printf("[HTTP] POST... code: %d\n", httpCode);

      // file found at server
      if (httpCode == HTTP_CODE_OK) {
        const String& payload = http.getString();
        Serial.println("received payload:\n<<");
        Serial.println(payload);
        Serial.println(">>");
      }
    } else {
      Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
    }

    http.end();
  }

  delay(10000);
}
