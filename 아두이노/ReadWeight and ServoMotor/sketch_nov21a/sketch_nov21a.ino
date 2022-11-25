#include "HX711.h"
#include <ThreeWire.h>  
#include <RtcDS1302.h>
#include<Servo.h>
Servo servo; 
const int LOADCELL_DOUT_PIN = 5;
const int LOADCELL_SCK_PIN = 16;
const int RST = 13;
const int DATA = 12;
const int CLOCK = 14;
ThreeWire myWire(12,14,13); // IO, SCLK, CE
RtcDS1302<ThreeWire> Rtc(myWire);
float calibration_factor = 370000;
int mortor = 4;
int angle = 0; 
HX711 scale;
float amount = 0.0f;



void setup() {
  servo.attach(mortor);
  scale.begin(LOADCELL_DOUT_PIN, LOADCELL_SCK_PIN);
    scale.set_scale();
scale.tare(); //Reset the scale to 0

Rtc.Begin();
RtcDateTime compiled = RtcDateTime(__DATE__, __TIME__);
 if (!Rtc.IsDateTimeValid()) 
    {
        // Common Causes:
        //    1) first time you ran and the device wasn't running yet
        //    2) the battery on the device is low or even missing

        Serial.println("RTC lost confidence in the DateTime!");
        Rtc.SetDateTime(compiled);
    }
      if (Rtc.GetIsWriteProtected())
    {
        Serial.println("RTC was write protected, enabling writing now");
        Rtc.SetIsWriteProtected(false);
    }

    if (!Rtc.GetIsRunning())
    {
        Serial.println("RTC was not actively running, starting now");
        Rtc.SetIsRunning(true);
    }
    
RtcDateTime now = Rtc.GetDateTime();
if (now < compiled) 
    {
        Serial.println("RTC is older than compile time!  (Updating DateTime)");
        Rtc.SetDateTime(compiled);
    }
    else if (now > compiled) 
    {
        Serial.println("RTC is newer than compile time. (this is expected)");
    }
    else if (now == compiled) 
    {
        Serial.println("RTC is the same as compile time! (not expected but all is fine)");
    }





  servo.write(180);
  Serial.begin(115200);


}

void loop() {
 scale.set_scale(calibration_factor); 
Serial.print("Reading: ");
amount = scale.get_units();
amount -= 0.250; //그릇 무게 빼기
Serial.print(amount,3); // 뒤에 있는 숫자는 몇 자리까지 보여줄지입니다.

Serial.print(" kg"); 

Serial.print(" calibration_factor: ");

Serial.print(calibration_factor);

Serial.println();

RtcDateTime now = Rtc.GetDateTime();
 printDateTime(now);

if(now.Second() % 30 == 0 && now.Second() >=5 && amount <0.080) //개방!
{
  angle = -180;
  servo.write(angle);
}

if(amount >=0.080) //닫기!
{
  
    angle = 180;

    servo.write(angle);
    //delay(10);
      Serial.print("\t\t");
      Serial.println(angle);  // 현재 각도 출력
  
  
  
}


}

#define countof(a) (sizeof(a) / sizeof(a[0]))

void printDateTime(const RtcDateTime& dt)
{
    char datestring[20];

    snprintf_P(datestring, 
            countof(datestring),
            PSTR("%02u/%02u/%04u %02u:%02u:%02u"),
            dt.Month(),
            dt.Day(),
            dt.Year(),
            dt.Hour(),
            dt.Minute(),
            dt.Second() );
    Serial.print(datestring);
}
