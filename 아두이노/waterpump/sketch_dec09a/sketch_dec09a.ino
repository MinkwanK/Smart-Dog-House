int AA = 14;
int AB = 12;
int sensor = 0;
int value = 0;
void setup() {
  // put your setup code here, to run once:
  pinMode(AA, OUTPUT);  // AA를 출력 핀으로 설정
  pinMode(AB, OUTPUT);  // AB를 출력 핀으로 설정
}

void loop() {
  value = analogRead(sensor);
  int water_level =map(value,0,1024,0,300);
  
    if(water_level<200){
  digitalWrite(AA, HIGH);  // 정방향으로 모터 회전
  digitalWrite(AB, LOW); // 5초동안 상태 유지(1000ms = 5s)
 
    }
 

}
