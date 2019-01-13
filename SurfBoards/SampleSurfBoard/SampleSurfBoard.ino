#include <WiFi.h>
#include <WiFiMulti.h>
#include <OneWire.h>
#include <DallasTemperature.h>

// WiFi Setup //
WiFiMulti WiFiMulti;
WiFiClient client;
const uint16_t port = PORT; // SurfBox Server port
const char * host = "HOST"; // SurfBox Server host
String line;

// pH Sensor //
float calibration = 0.0; // pH calibration value
const int analogInPin = A0; 
int sensorValue = 0; 
unsigned long int avgValue; 
float b;
int buf[10],temp;

// Temperature Sensor //
#define ONE_WIRE_BUS 15
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
DeviceAddress tempSensor;

void setup() {
 Serial.begin(115200);

 // Temperature Sensor //
 if (!sensors.getAddress(tempSensor, 0));
  sensors.setResolution(tempSensor, 12);
  sensors.begin();

 // Connect to WiFi //
 WiFiMulti.addAP("WIFI_USERNAME", "WIFI_PASSWORD");

  while(WiFiMulti.run() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }

  // Connect to SurfBox Server //
  if (!client.connect(host, port)) {
      Serial.println("connection failed");
      Serial.println("wait 5 sec...");
      delay(5000);
      return;
  }
}
 
void loop() {

 // pH Probe Logic //
 for(int i=0;i<10;i++) { 
  buf[i]=analogRead(analogInPin);
  delay(30);
 }
 for(int i=0;i<9;i++) {
  for(int j=i+1;j<10;j++) {
    if(buf[i]>buf[j]) {
      temp=buf[i];
      buf[i]=buf[j];
      buf[j]=temp;
    }
   }
 }
 
 avgValue=0;
 
 for(int i=2;i<8;i++)
  avgValue+=buf[i];
  
 float pHVol=(float)avgValue*1.5/4095/6;
 float phValue = -5.70 * pHVol + calibration;
 
 Serial.print("pH = ");
 Serial.println(14.0 - (phValue * -1));
 
 delay(500);

 // Temperature Sensor Logic //
 sensors.requestTemperatures();
 delay(100);
 Serial.println("Temperature = "+ String(sensors.getTempFByIndex(0)));

 delay(500);

 // Send data to SurfBox Server //
 client.println("p 0 newr " + String(sensors.getTempFByIndex(0)));
 line = client.readStringUntil('\r');
 Serial.println(line);
 delay(100);
 client.println("p 1 newr " + String(14.0 - (phValue * -1)));
 line = client.readStringUntil('\r');
 Serial.println(line);
}
