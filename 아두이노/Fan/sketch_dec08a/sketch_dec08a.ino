int FanA = 16;
int FanB = 5;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
   pinMode(FanA,OUTPUT);
   pinMode(FanB,OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  digitalWrite(FanA,HIGH);
  digitalWrite(FanB,LOW);

  delay(5000);

  digitalWrite(FanA,LOW);
  digitalWrite(FanB,LOW);
  
}
