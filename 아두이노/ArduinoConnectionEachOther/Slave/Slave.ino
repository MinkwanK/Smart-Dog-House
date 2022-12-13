#include <ThreeWire.h>  
#include <RtcDS1302.h>

const int RST = 4;
const int DATA = 3;
const int CLOCK = 2;
ThreeWire myWire(3,2,4); // IO, SCLK, CE
RtcDS1302<ThreeWire> Rtc(myWire);


void setup(){
  Rtc.Begin();
  RtcDateTime now = Rtc.GetDateTime();
  
   Serial.begin(115200);
}

void loop(){

  RtcDateTime now = Rtc.GetDateTime();
  Serial.println(now.Second());   // LED on
  delay(1000);
}
