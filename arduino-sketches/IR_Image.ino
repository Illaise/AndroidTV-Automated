#include <Wire.h>
#include <ArduinoJson.h>
#include "Max44009.h"
#include <IRremote.h>
#define SEND_NEC_STANDARD
// IR Signal PIN 3 - for UNO, 6 - for REV2
DynamicJsonDocument doc(1024);
#define BH_1750F 0x23
#define BH_1750S 0x5C
Max44009 Max4009F(0x4A); //blue
Max44009 Max4009S(0x4B); //green
#define DATA_REG_RESET 0b00000111
#define POWER_DOWN 0b00000000
#define POWER_ON 0b00000001
enum BH1750Mode {
  CHM = 0b00010000,     //CHM: Continuously H-Resolution Mode
  CHM_2 = 0b00010001,   //CHM_2: Continuously H-Resolution Mode2
  CLM = 0b00010011,     //CLM: Continuously L-Resolution Mode
  OTH = 0b00100000,     //OTH: One Time H-Resolution Mode
  OTH_2 = 0b00100001,   //OTH_2: One Time H-Resolution Mode2
  OTL = 0b00100011      //OTL: One Time L-Resolution Mode
} mode;
float measuringTimeFactor;
uint32_t lastDisplay = 0;
uint32_t count = 0;
IRsend IrSender;
int Button;
uint8_t sRepeats = 5;
int OnOff = 0xE31C5FA0;
int Up = 0xA6595FA0;
int Down = 0xAE515FA0;
int Left = 0xA9565FA0;
int Right = 0xEB145FA0;
int Center = 0xAA555FA0;
int Back = 0xEF105FA0;
int Home = 0xEC135FA0;
int Aspect = 0xA45B5FA0;
int Menu = 0xA55A5FA0;
int volumeUp = 0xBF405FA0;
int volumeDown = 0xA25D5FA0;
int Mute = 0xA05F5FA0;
int KiviTv = 0x946B5FA0;
void setup() {
  Serial.begin(9600);
  Wire.setClock(100000);
  Wire.begin();
  mode = CHM;
  measuringTimeFactor = 1;
  setMode();
  setMeasuringTime();
  delay(200);
}
void loop() {
  if (Serial.available () > 0) {
    Button = Serial.parseInt();
    if (Button == 0) {
      getLux(1);
    }
    else if (Button == 10) {
      getLux(2);
    }
    else if (Button == 1) {
      IrSender.sendNECStandard(OnOff, 28);
    }
    else if (Button == 2) {
      IrSender.sendNECStandard(Up, 89);
    }
    else if (Button == 3) {
      IrSender.sendNECStandard(Down, 81);
    }
    else if (Button == 4) {
      IrSender.sendNECStandard(Left, 86);
    }
    else if (Button == 5) {
      IrSender.sendNECStandard(Right, 20);
    }
    else if (Button == 6) {
      IrSender.sendNECStandard(Center, 85);
    }
    else if (Button == 8) {
      IrSender.sendNECStandard(Back, 16);
    }
    else if (Button == 9) {
      IrSender.sendNECStandard(Home, 19);
    }
    else if (Button == 11) {
      for (int i = 0; i < 45; i++) {
        delay(1000);
        IrSender.sendNECStandard(Down, 81);
      }
    }
    delay(100);
  }
}
void getLux(int jsonStyle) {
  uint16_t rawLux;
  float luminanceRed;
  uint16_t rawLuxS;
  float luminanceWhite;
  rawLux = readBH1750();
  if ((mode == CHM_2) || (mode == OTH_2)) {
    luminanceWhite = (rawLux / 2.4) / measuringTimeFactor;
  }
  else {
    luminanceWhite = (rawLux / 1.2) / measuringTimeFactor;
  }
  rawLuxS = readBH1750S();
  if ((mode == CHM_2) || (mode == OTH_2)) {
    luminanceRed = (rawLuxS / 2.4) / measuringTimeFactor;
  }
  else {
    luminanceRed = (rawLuxS / 1.2) / measuringTimeFactor;
  }
uint32_t interval = 1000;
  float luminanceGreen;
  float luminanceBlue;
  if (millis() - lastDisplay >= interval)
  {
    lastDisplay += interval;
    luminanceGreen = Max4009S.getLux();
    luminanceBlue = Max4009F.getLux();
  }
  float lumR = (luminanceRed);
  float lumG = (luminanceGreen);
  float lumB = (luminanceBlue);
  float lumW = (luminanceWhite);
  if (jsonStyle == 1) {
    float lumAll[] = {lumR, lumG, lumB, lumW};
    delay(500);
    //Создаем json и отправляем в последовательный порт
    for (int i = 0; i < 4; i++)
    {
      doc["lumin"][i] = lumAll[i];
    }
    serializeJson(doc, Serial);
    Serial.println();
  }
  if (jsonStyle == 2) {
    doc["lumR"] = lumR;
    doc["lumG"] = lumG;
    doc["lumB"] = lumB;
    doc["lumW"] = lumW;
    serializeJson(doc, Serial);
    Serial.println();
  }
}

void powerDown() {
  writeBH1750(POWER_DOWN);
  writeBH1750S(POWER_DOWN);
}
void powerOn() {
  writeBH1750(POWER_ON);
  writeBH1750S(POWER_ON);
  setMode();
}
void dataRegReset() {
  writeBH1750(DATA_REG_RESET);
  writeBH1750S(DATA_REG_RESET);
}
void setMode() {
  writeBH1750(mode);
  writeBH1750S(mode);
}
void setMeasuringTime() {
  byte mt = round(measuringTimeFactor * 69);
  byte highByteMT = ((mt >> 5) | 0b01000000);
  byte lowByteMT = (mt & 0b01111111);
  lowByteMT |= 0b01100000;
  writeBH1750(highByteMT);
  writeBH1750(lowByteMT);
  writeBH1750S(highByteMT);
  writeBH1750S(lowByteMT);
}
uint16_t readBH1750() {
  uint8_t MSbyte, LSbyte;
  Wire.requestFrom(BH_1750F, 2);
  if (Wire.available()) {
    MSbyte = Wire.read();
    LSbyte = Wire.read();
  }
  return ((MSbyte << 8) + LSbyte);
}
void writeBH1750(byte val) {
  Wire.beginTransmission(BH_1750F);
  Wire.write(val);
  Wire.endTransmission();
}
uint16_t readBH1750S() {
  uint8_t MSbyte, LSbyte;
  Wire.requestFrom(BH_1750S, 2);
  if (Wire.available()) {
    MSbyte = Wire.read();
    LSbyte = Wire.read();
  }
  return ((MSbyte << 8) + LSbyte);
}
void writeBH1750S(byte val) {
  Wire.beginTransmission(BH_1750S);
  Wire.write(val);
  Wire.endTransmission();
}
