int pin = 5;
int state = 0;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  pinMode(pin,INPUT);
}

void loop() {
  // put your main code here, to run repeatedly:

  state = digitalRead(pin);
  Serial.print(state);

    delay(1000);
  }
