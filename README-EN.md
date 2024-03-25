![logo](https://github.com/Illaise/AndroidTV-Automated/blob/master/Phts/logo.jpg)
# **Android TV 9/11 Automated testing Project**
+ [Project Description](#chapter-0)
+ [External Analysis Device / Hardware - Software Part](#chapter-1)
+ [Reading information from TV and control (Appium + Java), test script communication with Arduino](#chapter-2)
+ [Principle of external characteristics analysis](#chapter-3)
+ [Description of the case and connections](#chapter-4)

<a id="chapter-0"></a>
## Project Description
![first](https://github.com/Illaise/AndroidTV-Automated/blob/master/Phts/First.jpg)
1. Automatic TV testing begins with running the test script using the Jenkins build automation platform. Setting up and debugging will not be mentioned here.
2. The test script is created in Java with the connection of the Appium and Selenium clients. This allows direct connection to the TV, simulate remote control actions, navigate through most parts of the TV, and read various screen elements to check the functionality of all menu/app transitions on the TV.
3. To test external TV parameters (image/sound settings) and connected devices (HDMI/CEC), 2 Arduino boards with connected modules are used. Communication between them is based on the jSerialComm library, and information is transmitted in JSON format.
4. Testing of external parameters is based on comparing reference values with real ones:

<a id="chapter-1"></a>
## External Analysis Device / Hardware - Software Part
[Sketch for image analysis + sending IR commands](https://github.com/Illaise/AndroidTV-Automated/blob/master/arduino-sketches/IR_Image.ino)

[Sketch for sound analysis + sending IR commands](https://github.com/Illaise/AndroidTV-Automated/blob/master/arduino-sketches/Volume_Spectral_IR.ino)
### Image
An Arduino Uno R3 is used as the computing controller (a Nano can also be used). Two BH1750 light sensors and 2 MAXX44009 light sensors are connected to it via the I2C interface. A colored glass filter - RGBW (I use red and white for BH1750, green and blue for MAXX44009) is installed in front of each sensor. This allows passing light of a certain spectrum (in our case, 3 main colors and white) and determining its weight at the right moment.

Each of the sensors has a unique address when connected to the I2C bus. For BH1750, if the ADDR pin is not connected to power (3.3V), the address is 0x23; if connected, it is 0x5C. For MAXX44009, if the VCC pin is not connected to power (3.3V), the address is 0x4A; if connected, it is 0x4B.

![i2c](https://github.com/Illaise/AndroidTV-Automated/blob/master/Phts/sjZNV.png)

The code for BH1750 is based on the reading sketch from [Wolles](https://wolles-elektronikkiste.de/en/bh1750fvi-gy-30-302-ambient-light-sensor), for MAXX44009, the [Max44009](https://github.com/RobTillaart/Max44009) library is used.

Part of MAXX44009:
```
uint32_t lastDisplay = 0;
uint32_t interval = 1000;
  float luminanceGreen;
  float luminanceBlue;
  if (millis() - lastDisplay >= interval)
  {
    lastDisplay += interval;
    luminanceGreen = Max4009S.getLux();
    luminanceBlue = Max4009F.getLux();
  }
  ```
Other functions belong to BH1750.
Sending a numerical value to the serial port is the trigger for requesting a value from the sensor.
```
  void loop() {
  if (Serial.available () > 0) {
    Button = Serial.parseInt();
    if (Button == 0) {
      getLux(1);
    }
    else if (Button == 10) {
      getLux(2);
    }
  ```
Data from the sensors is transmitted in JSON format using the [ArduinoJson](https://arduinojson.org/) library.
```
if (jsonStyle == 1) {
  float lumAll[] = {lumR, lumG, lumB, lumW};
  delay(500);
  // Create JSON and send to the serial port
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
```
jsonStyle == 1 - JSON is transmitted as an array. (not used for images, available for testing purposes)
jsonStyle == 2 - JSON is transmitted with four key-value pairs.

For emulating the external remote control, the library version 1.8.0 is used, and the signal output should be connected to the third digital pin.
```
  #define SEND_NEC_STANDARD
  IRsend IrSender;
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
  
void loop() {
  if (Serial.available () > 0) {
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
```
For this, you will need an IR receiver and an example sketch for reading from more recent [lib versions](https://github.com/Arduino-IRremote/Arduino-IRremote/tree/master/examples/ReceiveDemo). 
To send key codes (emulate a remote control for a specific TV model), you need to know the standards for sending IR signals:
![Standards](https://github.com/Illaise/AndroidTV-Automated/blob/master/arduino-sketches/IR_Manufacture%20standard.png) 

IrSender.sendNECStandard(OnOff, 28);

.sendNECStandard - NEC standard;

OnOff - hexadecimal value of the key code;

28 - sCommand, the identifier of the key code.

### Sound
[Sketch for sound analysis](https://github.com/Illaise/AndroidTV-Automated/blob/master/arduino-sketches/Volume_Spectral_IR.ino) 
A sound sensor with MAX4466 microphone is connected to Arduino. WiFi Rev2 is used as the computational controller. 
To use UNO/NANO, you need to modify the code to increase the frequency of the analog input polling. Increasing the frequency is necessary to analyze a wider spectrum of frequencies.
Function for WiFi Rev2:
```
int analogReadFast(int CHANNEL) {
  byte ADCregOriginal = ADC0_CTRLC;
  ADC0_CTRLC = 0x54; // reduced cap, Vdd ref, 32 prescaler. See page 408 in the datasheet to change register value
  int adc = analogRead(CHANNEL);
  ADC0_CTRLC = ADCregOriginal;
  return adc;
}
```
For UNO/NANO, use regular analogRead(CHANNEL), but set the register parameters:
```
// global variables
#define cbi(sfr, bit) (_SFR_BYTE(sfr) &= ~_BV(bit))
#define sbi(sfr, bit) (_SFR_BYTE(sfr) |= _BV(bit))
void setup() {
  sbi(ADCSRA, ADPS2);
  cbi(ADCSRA, ADPS1);
  sbi(ADCSRA, ADPS0);
}
```
If using UNO/NANO, don't forget to change the reading functions in the sketch from analogReadFast(CHANNEL) to analogRead(CHANNEL).

Two methods are used for testing - measuring the volume level and digitizing the analog signal plus spectral sound analysis.

Function for measuring the volume level:
```
void soundVolumeVolt() {
  unsigned long startMillis = millis();
  unsigned int peakToPeak = 0;   // peak-to-peak oscillations

  unsigned int signalMax = 0;
  unsigned int signalMin = 1024;

  // gather data for 50 ms
  while (millis() - startMillis < sampleWindow)
  {
    sample = analogReadFast(CHANNEL);
    if (sample < 1024)
    {
      if (sample > signalMax)
      {
        signalMax = sample;  // save maximum value
      }
      else if (sample < signalMin)
      {
        signalMin = sample;  // save minimum value
      }
    }
  }
  peakToPeak = signalMax - signalMin;  // max - min = peak-to-peak oscillations
  doc["volumeLevel"] = peakToPeak;
  serializeJson(doc, Serial);
  Serial.println();
}
```
Data is sent as JSON in numerical values.
The digitization and spectral analysis function is based on the  [arduinoFFT](https://www.arduino.cc/reference/en/libraries/arduinofft/) library, described in the sketch. 
It breaks down the analog signal into harmonics (secondary sound waves at frequencies different from the fundamental tone) and the fundamental tone, then calculates the volume of each.
```
const uint16_t samples = 64; // number of analyzed harmonics
const double samplingFrequency = 38000; // sampling frequency (frequency spectrum in which harmonics are selected). By increasing the analog port polling frequency function, we can increase the number of polls to 40000. Therefore, we can analyze frequencies up to 20 kHz.
```
```
 numbers[i] = {vData[i]}; //save calcuated magnnitudes data to array
 doc["Harmonic"][i] = map(numbers[i], 0, 1000, 0, 100); // creating json based on array
 serializeJson(doc, Serial);
 Serial.println();

 ```
<a id="chapter-2"></a>

## Reading information from the TV and control (Appium + Java), communication of test script with Arduino
### Script Operation Principle
There are 2 types of test scripts:
1. Training script - partially repeats the actions of the working script, modifies sound settings, images, etc., and records reference values from the analyzing device into a CSV table. 
This allows easy adjustment of the script for different screens. It is used on software with correct sound/image settings.
2. Working script - simulates manual actions with the remote control according to the built script, checks the TV functionality, and if external TV parameters (sound, image) are being tested, 
it takes readings from the analyzing device and compares them with reference values from the CSV table.
### Script Communication with Arduino
The [jSerialComm](https://fazecast.github.io/jSerialComm/) library is used for communication.
Port number declaration in the class:
```
private static final String COMPORT = "/dev/ttyACM0"; //"COM4", Linux
private static final String COMPORT = "COM4"; //Windows
```
Port number can be found through the terminal in the Arduino IDE.
Declaration of the COM port in the class:
```
private static SerialPort chosenPort;
```
Initialization of the COM port:
```
chosenPort = SerialPort.getCommPort(COMPORT);
chosenPort.openPort();
chosenPort.setBaudRate(9600); // Setting the baud rate
chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0); // Transmission timeouts
```
Closing the port:
```
chosenPort.closePort();
```
### Writing and Reading CSV
Function for writing four values to the table:
```
public static void writeToCSVObj(String name) throws InterruptedException {
    try {
        String PathTillProject = System.getProperty("user.dir");
        FileWriter csvWriter = new FileWriter(PathTillProject + "/src/main/SoundEthalon.csv", true); // Declare FileWriter class and specify the path for creating and writing the table
        JSONObject obj = jserialOutputObj(); // Request data from Arduino
        TimeUnit.SECONDS.sleep(2);
        double luminanceR = obj.getDouble("lumR");
        double luminanceG = obj.getDouble("lumG");
        double luminanceB = obj.getDouble("lumB");
        double luminanceW = obj.getDouble("lumW");
        String lumR = Double.toString(luminanceR); // Convert numerical values to strings and write them to variables
        String lumG = Double.toString(luminanceG);
        String lumB = Double.toString(luminanceB);
        String lumW = Double.toString(luminanceW);
        csvWriter.append(name); // Setting name
        csvWriter.append(",");
        csvWriter.append(lumR);
        csvWriter.append(",");
        csvWriter.append(lumG);
        csvWriter.append(",");
        csvWriter.append(lumB);
        csvWriter.append(",");
        csvWriter.append(lumW);
        csvWriter.append("\n");
        csvWriter.flush();
        csvWriter.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```
Function for writing an array of values to the table:
```
public static void writeToCSVArray(String name) throws InterruptedException {
    try {
        String PathTillProject = System.getProperty("user.dir");
        FileWriter csvWriter = new FileWriter(PathTillProject + "/src/main/SoundEthalon.csv", true); // Declare FileWriter class and specify the path for creating and writing the table
        TimeUnit.SECONDS.sleep(4);
        double[] real = jserialOutputArray(); // Request data from Arduino
        String[] sendData = new String[real.length];
        String separator = ",";
        csvWriter.append(name); // Setting name
        csvWriter.append(separator); // Separator
        for (int i = 0; i < real.length; i++) {
            sendData[i] = String.valueOf(real[i]); // Convert numerical values to strings and write them to a string array
            csvWriter.append(sendData[i]); // Write array element to the table
            csvWriter.append(separator); // Separator after each iteration
        }
        csvWriter.append("0");
        csvWriter.append("\n"); // Next line
        csvWriter.flush(); // Clear input
        csvWriter.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```
Function for reading variables from the table:
```
public static List<String[]> getCVSFileContents(String path) { //path - path to the table
    List<String[]> content = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(path))) { // Initialize reading from the table
        String line = "";
        while ((line = br.readLine()) != null) { // As long as there are values in the line
            content.add(line.split(",")); // Write data to the list separated by commas
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return content;
}
```
Functions for reading arrays from the table:
```
// get excel array from arduino
private static double[] exelOutputArray(int rowCount, List<String[]> contents) {
    double[] arrayDataSheet = new double[17];
    String[] row = contents.get(rowCount);
    for (int i = 1; i < arrayDataSheet.length; ++i) {
        arrayDataSheet[i] = Double.parseDouble(row[i]);
        //System.out.println(arrayDataSheet[i]);
    }
    return arrayDataSheet;
}

//  write exel output to array
private static double[] exelVal(int rowCount, List<String[]> contents) {
    double[] arrayDataSheet = exelOutputArray(rowCount, contents);
    double[] arrayDataOutput = new double[16];
    for (int i = 0; i < arrayDataOutput.length; i++) {
        for (int j = 1; j < arrayDataSheet.length; j++) {
            if (j == i + 1) {
                arrayDataOutput[i] = arrayDataSheet[j];
                //System.out.println(arrayDataOutput[i]);
            }
        }
    }
    return arrayDataOutput;
}
```

### Main Writing/Reading Functions
Sending a request to Arduino:
```
private static void jserialInput(int in) {
    PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
    try {
        Thread.sleep(100);
    } catch (InterruptedException e) {
        System.out.println(e.getMessage());
    }
    output.print(in);
    output.flush();
}
```
Receiving data from Arduino (variables):
```
private static JSONObject jserialOutputObj() {
    for (int i = 0; i < 3; i++) {
        String genuino = null; // Variable for storing Arduino output
        jserialInput(10); // Request for data
        try {
            TimeUnit.SECONDS.sleep(4);
            input = new BufferedReader(new InputStreamReader(chosenPort.getInputStream())); // Initialize data reception
            genuino = input.readLine(); // Write to string
            System.out.println(genuino);
            InputStream comPortInput = chosenPort.getInputStream();
            comPortInput.skip(comPortInput.available()); // Skip if data not received
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        try {
            JSONObject obj = new JSONObject(genuino); // Convert to JSON object for further processing
            return obj;
        } catch (org.json.JSONException exception) {
            if (jserialOutputObj().has("lumR") && jserialOutputObj().has("lumG") &&
                    jserialOutputObj().has("lumB") && jserialOutputObj().has("lumW")) {
                continue; // If data is complete, exit loop
            }
        }
    }
    return null;
}
```
Receiving data from Arduino (array):
```
private static double[] jserialOutputArray() {
    for (int r = 0; r < 5; r++) {
        String arduino = "";
        jserialInput(2);
        try {
            TimeUnit.SECONDS.sleep(3);
            BufferedReader input = new BufferedReader(new InputStreamReader(chosenPort.getInputStream()));
            arduino = input.readLine();
            InputStream comPortInput = chosenPort.getInputStream();
            comPortInput.skip(comPortInput.available());
            Thread.sleep(100);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        try {
            JSONObject obj = new JSONObject(arduino);
            JSONArray arr = obj.getJSONArray("Harmonic");
            if (arr == null) { /*.....*/ }
            double[] numbers = new double[arr.length()];
            for (int i = 0; i < arr.length(); ++i) {
                numbers[i] = arr.optDouble(i);
            }
            return numbers;
        } catch (org.json.JSONException exception) {
            continue;
        }
    }
    return null;
}
```
Comparison of actual and reference parameters (variables):
```
    private static boolean objScreenVal(int rowCount, List<String[]> content) throws InterruptedException {
        JSONObject obj;
        boolean result = false;
        int rep = 1;
        String[] row = content.get(rowCount);

        double luminanceRTrue = Double.parseDouble(row[1]);
        double luminanceGTrue = Double.parseDouble(row[2]);
        double luminanceBTrue = Double.parseDouble(row[3]);
        double luminanceWTrue = Double.parseDouble(row[4]);
        obj = jserialOutputObj();
        TimeUnit.SECONDS.sleep(2);
        double luminanceR = obj.getDouble("lumR");
        double luminanceG = obj.getDouble("lumG");
        double luminanceB = obj.getDouble("lumB");
        double luminanceW = obj.getDouble("lumW");
        if (luminanceRTrue - luminanceR <= 1 && luminanceRTrue - luminanceR >= -1 && luminanceGTrue - luminanceG <= 1 && luminanceGTrue - luminanceG >= -1 && luminanceBTrue - luminanceB <= 1 && luminanceBTrue - luminanceB >= -1 && luminanceWTrue - luminanceW <= 1 && luminanceWTrue - luminanceW >= -1) {
            result = true;
        }
        return result;
    }
```
Comparison of actual and reference values (variables):
```
private static boolean objScreenVal(int rowCount, List<String[]> content) throws InterruptedException {
    JSONObject obj;
    boolean result = false;
    int rep = 1;
    String[] row = content.get(rowCount);

    double luminanceRTrue = Double.parseDouble(row[1]);
    double luminanceGTrue = Double.parseDouble(row[2]);
    double luminanceBTrue = Double.parseDouble(row[3]);
    double luminanceWTrue = Double.parseDouble(row[4]);
    obj = jserialOutputObj();
    TimeUnit.SECONDS.sleep(2);
    double luminanceR = obj.getDouble("lumR");
    double luminanceG = obj.getDouble("lumG");
    double luminanceB = obj.getDouble("lumB");
    double luminanceW = obj.getDouble("lumW");
    if (luminanceRTrue - luminanceR <= 1 && luminanceRTrue - luminanceR >= -1 && luminanceGTrue - luminanceG <= 1 && luminanceGTrue - luminanceG >= -1 && luminanceBTrue - luminanceB <= 1 && luminanceBTrue - luminanceB >= -1 && luminanceWTrue - luminanceW <= 1 && luminanceWTrue - luminanceW >= -1) {
        result = true;
    }
    return result;
}
```
Comparison of actual and reference values (array):
```
private static boolean ArrayScreenVaL(int rowCount) {
    double diff;
    double[] ethalon = exelVal(rowCount, soundEthalonVal);
    double[] real = jserialOutputArray();
    if (real == null) return false;
    boolean avl = false;
    for (int i = 0; i < real.length; i++) {
        for (int j = 0; j < ethalon.length; j++) {
            if (j == i) {
                diff = (ethalon[j] - real[i]);
                System.out.println(diff);
                if (diff <= 100 && diff >= -100) {
                    avl = true;
                }
            }
        }
    }
    return avl;
}
```
Outputting the result:
```
private static void LegendsFallObj(String Passed, String Failed, int rowCount, List<String> report) throws InterruptedException {
    boolean passFlag = objScreenVal(rowCount, soundEthalonVal);
    if (passFlag) {
        System.out.println(Passed);
        report.add("(+) " + Passed);
    } else {
        System.out.println(Failed);
        report.add("(-) " + Failed);
    }
}

public static void LegendsFallArray(String Passed, String Failed, int rowCount, List<String> report) throws InterruptedException {
    boolean passFlag = ArrayScreenVaL(rowCount);
    if (passFlag) {
        System.out.println(Passed);
        report.add("(+) " + Passed);
    } else {
        System.out.println(Failed);
        report.add("(-) " + Failed);
    }
}
```

### Navigation and Connection Functions
Connect to the TV:
```
public static void connect() throws MalformedURLException {
    DesiredCapabilities dc = new DesiredCapabilities();
    dc.setCapability("deviceName", TVName);
    dc.setCapability("noReset", "true");
    dc.setCapability("platformName", "Android");
    dc.setCapability("platformVersion", "9");
    URL url = new URL("http://127.0.0.1:4723/wd/hub");
    driver = new AndroidDriver<AndroidElement>(url, dc);
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    System.out.println("Application started....");
}
```
Opening test videos on YouTube:
```
private static void equalizerFifth() {
    driver_args.put("command", "am start -a ");
    driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/ZjtKR9uQvqs?t=11\""));
    try {
        driver.executeScript("mobile:shell", driver_args);
    } catch (NullPointerException e) {
        System.out.println("(!) Something went wrong with the script...");
        System.exit(1);
        dpad(AndroidKey.DPAD_DOWN);
    }
}
```
Simplifying remote control button presses:
```
private static void dpad(AndroidKey key) {
    driver.pressKey(new KeyEvent(key));
}
```
Opening settings through activity:
```
private static void startSettings() {
    driver_args.put("command", "am start -n ");
    driver_args.put("args", Lists.newArrayList("com.android.tv.settings/com.android.tv.settings.MainSettings"));
    try {
        driver.executeScript("mobile:shell", driver_args);
    } catch (NullPointerException e) {
        System.out.println("(!) Something went wrong with the script...");
        System.exit(1);
        dpad(AndroidKey.DPAD_DOWN);
    }
}
```
Searching for an element by image:
```
private static MobileElement findElementByImage(String using) {
    try {
        return driver.findElementByImage(using);
    } catch (NoSuchElementException e) {
        return null;
    }
}
```
Searching for an element by ID:
```
private static AndroidElement findElementWrapper(String using) {
    try {
        return driver.findElement(By.id(using));
    } catch (NoSuchElementException e) {
        return null;
    }
}
```
Searching for an element by text:
```
private static AndroidElement findElementByXPathWrapper(String using) {
    try {
        return driver.findElementByXPath(using);
    } catch (NoSuchElementException e) {
        return null;
    }
}
```

<a id="chapter-3"></a>
## The principle of characteristics analysis
The device operates in two modes - training mode and testing mode. In training mode, the test script repeats all actions performed in testing mode. The main difference is that in training mode, the data is recorded in a CSV database, which serves as the reference values for the characteristics captured from a specific TV model. This allows testing TVs with different factory settings. In testing mode, the real-time measurements are compared with the reference values from the database. The main goal of the device is to verify the correctness of the factory settings after updating the firmware (essentially, it is an automated colorimeter + noise meter with additional functions). The device must be placed in a strictly fixed position and be adjacent to the screen. The results of visual tests will be influenced by factors such as the working voltage of the TV's connection network, uniformity of the TV screen backlighting, factory defects, etc. The results of sound testing will be influenced by factors such as the noise insulation of the testing environment, external noises from the TV (factory defects). The results of connecting external devices and their visual characteristics will be influenced by the quality of the connection of external devices.
<a id="chapter-4"></a>
## Description of the Case and Connections
![Front view](https://github.com/Illaise/AndroidTV-Automated/blob/master/Phts/1.png)

The front part consists of four tunnels containing light sensors. The tunnels are covered with glass (light filters) of specific colors - red, green, blue, and white. 
Each sensor receives light of one of the primary colors. As a result, we have a full-fledged colorimeter with the ability to request data when needed.

![Bottom view](https://github.com/Illaise/AndroidTV-Automated/blob/master/Phts/2.png)

Inside, there are stands for fixing Arduino boards, wires, and other device components.

![Rear View](https://github.com/Illaise/AndroidTV-Automated/blob/master/Phts/4.png)

At the rear of the case, there are openings for connecting USB cables and power supply. Inside, there are stands for fixing Arduino boards, wires, and other device components.
