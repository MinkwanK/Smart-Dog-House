void setup(){
  Serial.begin(115200);
  pinMode(13, OUTPUT);
}

void loop(){
  if(Serial.available()){
   char c = (char)Serial.read();
    Serial.println(c);
  }  
}
