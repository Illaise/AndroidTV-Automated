#include "arduinoFFT.h"
#include <ArduinoJson.h>
#include <IRremote.h>
#define SEND_NEC_STANDARD
// IR Signal PIN 3 - for UNO, 6 - for REV2
DynamicJsonDocument doc(1024);
const int sampleWindow = 50; // время выборки значений mS (50 mS = 20Hz)
unsigned int sample;
arduinoFFT FFT = arduinoFFT(); /* Create FFT object */
/*
  These values can be changed in order to evaluate the functions
*/
#define CHANNEL A0
const uint16_t samples = 64; //This value MUST ALWAYS be a power of 2 - the number of analyzed frequencies
const double samplingFrequency = 38000; //Hz, must be less than 40000 due to register - frequency of discritization

unsigned int sampling_period_us;
unsigned long microseconds;

/*
  These are the input and output vectors
  Input vectors receive computed results from FFT
*/
double vReal[samples];
double vImag[samples];


#define SCL_INDEX 0x00
#define SCL_TIME 0x01
#define SCL_FREQUENCY 0x02
#define SCL_PLOT 0x03

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

void setup()
{
  sampling_period_us = round(1000000 * (1.0 / samplingFrequency));
  Serial.begin(9600);
}

void loop()
{
  if (Serial.available () > 0) {
    int Button = Serial.parseInt();
    if (Button == 1) {
      soundVolumeVolt();
    }
    else if (Button == 2) {
      FFTsound();
    }
        else if (Button == 3) {
          IrSender.sendNECStandard(OnOff, 28);
        }
        else if (Button == 4) {
          IrSender.sendNECStandard(Up, 89);
        }
        else if (Button == 5) {
          IrSender.sendNECStandard(Down, 81);
        }
        else if (Button == 6) {
          IrSender.sendNECStandard(Left, 86);
        }
        else if (Button == 7) {
          IrSender.sendNECStandard(Right, 20);
        }
        else if (Button == 8) {
          IrSender.sendNECStandard(Center, 85);
        }
        else if (Button == 9) {
          IrSender.sendNECStandard(Back, 16);
        }
        else if (Button == 10) {
          IrSender.sendNECStandard(Home, 19);
        }
    delay(100);
  }
}

void FFTsound() {
  /*SAMPLING*/
  microseconds = micros();
  for (int i = 0; i < samples; i++)
  {
    vReal[i] = analogReadFast(CHANNEL);
    vImag[i] = 0;
    while (micros() - microseconds < sampling_period_us) {
      //empty loop
    }
    microseconds += sampling_period_us;
  }
  /* Print the results of the sampling according to time */
  //   Serial.println("Data:");
  //   PrintVector(vReal, samples, SCL_TIME);
  FFT.Windowing(vReal, samples, FFT_WIN_TYP_HAMMING, FFT_FORWARD);  /* Weigh data */
  //   Serial.println("Weighed data:");
  //   PrintVector(vReal, samples, SCL_TIME);
  FFT.Compute(vReal, vImag, samples, FFT_FORWARD); /* Compute FFT */
  //   Serial.println("Computed Real values:");
  //   PrintVector(vReal, samples, SCL_INDEX);
  //   Serial.println("Computed Imaginary values:");
  //   PrintVector(vImag, samples, SCL_INDEX);
  FFT.ComplexToMagnitude(vReal, vImag, samples); /* Compute magnitudes */
  // Serial.println("Computed magnitudes:");
  PrintVector(vReal, (samples >> 1), SCL_FREQUENCY);
  double x = FFT.MajorPeak(vReal, samples, samplingFrequency);
  // Serial.println(x, 6); //Print out what frequency is the most dominant.
  //  while(1); /* Run Once */
  delay(2000); /* Repeat after delay */
}

// changing the register value of prescaler, for increase max samplingFreq, you can use it instead of analogRead
int analogReadFast(int CHANNEL) {
  byte ADCregOriginal = ADC0_CTRLC;
  ADC0_CTRLC = 0x54; // reduced cap, Vdd ref, 32 prescaler. See page 408 in the datasheet to change register value
  int adc = analogRead(CHANNEL);
  ADC0_CTRLC = ADCregOriginal;
  return adc;
}

void PrintVector(double *vData, uint16_t bufferSize, uint8_t scaleType)
{
  double numbers[32];
  for (uint16_t i = 0; i < bufferSize; i++)
  {
    double abscissa;
    /* Print abscissa value */
    switch (scaleType)
    {
      case SCL_INDEX:
        abscissa = (i * 1.0);
        break;
      case SCL_TIME:
        abscissa = ((i * 1.0) / samplingFrequency);
        break;
      case SCL_FREQUENCY:
        abscissa = ((i * 1.0 * samplingFrequency) / samples);
        break;
    }
    //  Serial.print(abscissa, 6);
    if (scaleType == SCL_FREQUENCY)
      //   Serial.print("Hz");
      numbers[i] = {vData[i]}; //save calcuated magnnitudes data to array
    doc["Harmonic"][i] = map(numbers[i], 0, 1000, 0, 100); // creating json based on array
    //   Serial.print(" ");
    //   Serial.println(vData[i], 4);
  }
  serializeJson(doc, Serial);
  Serial.println();
}

void soundVolumeVolt() {
  unsigned long startMillis = millis();
  unsigned int peakToPeak = 0;   // размах колебаний

  unsigned int signalMax = 0;
  unsigned int signalMin = 1024;

  // собираем данные на протяжении 50 mS
  while (millis() - startMillis < sampleWindow)
  {
    sample = analogReadFast(CHANNEL);
    if (sample < 1024)
    {
      if (sample > signalMax)
      {
        signalMax = sample;  // сохранение максимального значения
      }
      else if (sample < signalMin)
      {
        signalMin = sample;  // сохранение минимального значения
      }
    }
  }
  peakToPeak = signalMax - signalMin;  // max - min = размах колебаний
  doc["volumeLevel"] = peakToPeak;
  serializeJson(doc, Serial);
  Serial.println();
}
