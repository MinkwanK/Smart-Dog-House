/*!
 * @file readDHT11.ino
 * @brief DHT11 is used to read the temperature and humidity of the current environment. 
 *
 * @copyright   Copyright (c) 2010 DFRobot Co.Ltd (http://www.dfrobot.com)
 * @license     The MIT License (MIT)
 * @author [Wuxiao](xiao.wu@dfrobot.com)
 * @version  V1.0
 * @date  2018-09-14
 * @url https://github.com/DFRobot/DFRobot_DHT11
 */

#include <DFRobot_DHT11.h>
DFRobot_DHT11 DHT;
#define DHT11_PIN 4

int A = 16;
int B = 5;


void setup(){
  Serial.begin(115200);
    pinMode(A,OUTPUT);
   pinMode(B,OUTPUT);

    digitalWrite(A,HIGH);
   digitalWrite(B,HIGH);
}

void loop(){
  DHT.read(DHT11_PIN);
  Serial.print("temp:");
  Serial.print(DHT.temperature);
  Serial.print("  humi:");
  Serial.println(DHT.humidity);

  if(DHT.temperature>25)
  {
     digitalWrite(A,LOW);
   digitalWrite(B,HIGH);
  }
  else
  {
   digitalWrite(A,HIGH);
   digitalWrite(B,HIGH);
  }
  delay(1000);
}
