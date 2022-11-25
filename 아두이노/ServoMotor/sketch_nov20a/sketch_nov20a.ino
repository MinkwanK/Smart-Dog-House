#include<Servo.h>

Servo servo; 

int mortor = 4;
int angle = 0; 
void setup() {
  servo.attach(mortor);

  Serial.begin(115200);

}

void loop() {
    servo.write(150);
    delay(2000);
    servo.write(-180);
    delay(2000);


}
