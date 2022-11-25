#include "HX711.h"

// HX711 circuit wiring
const int LOADCELL_DOUT_PIN = 5;
const int LOADCELL_SCK_PIN = 16;
float calibration_factor = 370000;


HX711 scale;

void setup() {
  Serial.begin(115200);
  scale.begin(LOADCELL_DOUT_PIN, LOADCELL_SCK_PIN);

  scale.set_scale();

scale.tare(); //Reset the scale to 0
}

//13000
void loop() {

scale.set_scale(calibration_factor); 
Serial.print("Reading: ");

Serial.print(scale.get_units(), 3); // 뒤에 있는 숫자는 몇 자리까지 보여줄지입니다.

Serial.print(" kg"); 

Serial.print(" calibration_factor: ");

Serial.print(calibration_factor);

Serial.println();


  if(Serial.available())

{

char temp = Serial.read(); //시리얼 모니터상에서 캘리를 조정할 수 있다 대신 10씩 움직여지기 때문에 캘리가 크게 변해야 하는 경우는 코드에서 직접 숫자를 변경하고 재실행하는 게 훨씬 빠릅니다.

if(temp == '+' || temp == 'a')

calibration_factor += 10;

else if(temp == '-' || temp == 'z')

calibration_factor -= 10;

}



}


/*
#include "HX711.h"
#define calibration_factor -7050.0
#define dout 5
#define clk 16

HX711 scale(5,16);

void setup() {
  Serial.begin(115200);
  scale.set_scale(calibration_factor); //스케일 지정
  scale.tare(); //스케일 설정
}

void loop() {

  Serial.print(scale.get_units(),1);//무게출력
  Serial.print("lbs");
  Serial.println();
  
}
*/
