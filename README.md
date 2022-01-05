![logo](https://github.com/Illaise/AndroidTV-Automated/blob/master/Phts/logo.jpg)
# **Проект тестирования телевизоров на базе Android TV 9/11**
+ [Описание проекта](#chapter-0)
+ [Внешнее анализирующее устройство / аппаратно - программная часть](#chapter-1)
+ [Считывание информации с ТВ и управление (Appium + Java), коммуникация тестового скрипта с Arduino](#chapter-2)
+ [Принцип анализа внешних характеристик](#chapter-3)
+ [Описание корпуса и подключений](#chapter-4)

<a id="chapter-0"></a>
## Описание проекта
![first](https://github.com/Illaise/AndroidTV-Automated/blob/master/Phts/First.jpg)
1. Автоматическое тестирование ТВ начинается с запуска тестового скрипта с помощью платформы автоматизации сборок Jenkins. О настройке и отладке здесь упоминаться не будет.
2. Тестовый скрипт создан на Java с подключением клиента Appium и Selenium. Это позволяет напрямую подключится к телевизору, имитировать действия пультом, производить навигацию по большинству частей телевизора и считывать различные элементы экрана для проверки работоспособности переходов всех меню / приложений на ТВ.
3. Для тестирования внешних параметров ТВ (настройки изображения / звука) и подключенных устройств (HDMI/CEC) используються 2 платы Arduino с подключенными к ним модулями. Коммуникация между ними основана на библиотеке jSerialComm, информация передается в формате json. 
4. Тестирование внешних параметров происходит на основе сравнения эталонных показателей с реальными:

<a id="chapter-1"></a>
## Внешнее анализирующее устройство / аппаратно - программная часть
[Скетч для анализа изображения + отправка IR команд](https://github.com/Illaise/AndroidTV-Automated/blob/master/arduino-sketches/IR_Image.ino)

[Скетч для анализа звука + отправка IR команд](https://github.com/Illaise/AndroidTV-Automated/blob/master/arduino-sketches/Volume_Spectral_IR.ino)
### Изображение 
В качестве вычислительного контроллера используется Arduino Uno R3 (так же можно использовать Nano). К нему подключены два датчика света BH1750 и 2 датчика света MAXX44009 через I2C интерфейс. На перед каждым датчиком устанавливается цветное стекло - RGBW (я использую красную и белую стекляшку для BH1750, зеленую и синюю для MAXX44009). Это позволяет пропускать свет определенного спектра (в нашем случае 3х основных цветов и белого) и определять его вес в нужный момент.

Каждый из датчиков имеет уникальный адрес при подключении к шине I2C. Для BH1750 если пин ADDR не подключен к питанию (3.3В) адрес 0x23, если подключен - 0x5C. Для MAXX44009 если пин VCC не подключен к питанию (3.3В) адрес 0x4A, если подключен - 0x4B.

![i2c](https://github.com/Illaise/AndroidTV-Automated/blob/master/Phts/sjZNV.png)

Для BH1750 код написан без использования библиотек, для MAXX44009 используеться библиотека https://github.com/RobTillaart/Max44009. 

Часть MAXX44009:
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
  Остальные функции относяться к BH1750.
  Отправка числового значения в последовательный порт и есть триггер запроса значения с датчика
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
  Данные с датчиков передаються в формате json, с помощью библиотеки https://arduinojson.org/
  ```
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
  ```
  jsonStyle == 1 - Передатся json массивом. (не используется для изображения, доступен в тестовых целях)
  jsonStyle == 2 - Передается json с четырьмя парами ключ-значение.
  
  Для имитации внешнего пульта используется [библиотека](https://github.com/Arduino-IRremote/Arduino-IRremote) версии 1.8.0, вывод сигнала необходимо подключать к третьему цифровому пину.
 
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

Для того что бы отправлять кейкоды (имитировать пульт под конкретную модель ТВ) необходимо знать стандарты отправки ИК сигнала:

![Стандарты](https://github.com/Illaise/AndroidTV-Automated/blob/master/arduino-sketches/IR_Manufacture%20standard.png) 

Для этого необходим ИК приемник и пример скетча считывания из более высоких [версий библиотеки](https://github.com/Arduino-IRremote/Arduino-IRremote/tree/master/examples/ReceiveDemo). 

IrSender.sendNECStandard(OnOff, 28);

.sendNECStandard - стандарт NEC;

OnOff - шестнадцатиричное значение кейкода;

28 - sCommand, идентификатор кейкода;

### Звук 
[Скетч для анализа звука](https://github.com/Illaise/AndroidTV-Automated/blob/master/arduino-sketches/Volume_Spectral_IR.ino) 
К Arduino подключена датчик звука с микрофоном MAX4466. В качестве вычислительного контроллера используется Wi-Fi Rev2 для того, что бы использовать UNO /NANO нужно изменить код повышения частоты опроса аналогового входа. Повышение частоты необходимо для того, что бы анализировать больший спектр частот.
Функция для Wi-Fi Rev2:
```
int analogReadFast(int CHANNEL) {
  byte ADCregOriginal = ADC0_CTRLC;
  ADC0_CTRLC = 0x54; // reduced cap, Vdd ref, 32 prescaler. See page 408 in the datasheet to change register value
  int adc = analogRead(CHANNEL);
  ADC0_CTRLC = ADCregOriginal;
  return adc;
}
```
Для UNO/NANO используется обычная analogRead(CHANNEL), но необходимо задать параметры регистра:
```
// глобальные переменные
#define cbi(sfr, bit) (_SFR_BYTE(sfr) &= ~_BV(bit))
#define sbi(sfr, bit) (_SFR_BYTE(sfr) |= _BV(bit))
void setup() {
  sbi(ADCSRA, ADPS2);
  cbi(ADCSRA, ADPS1);
  sbi(ADCSRA, ADPS0);
```
Если используеться UNO/NANO, не забудьте изменить считывающие функции по скетчу analogReadFast(CHANNEL) на analogRead(CHANNEL)
Для тестирования используются два метода - измерение уровня громкости и оцифровка аналогового сигнала + спектральный анализ звука.
Функция измерения уровня громкости 
```
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
```
Данные отправляются json'ом в виде числовых значений.
Функция оцифровки и спектрального анализа основана на библиотеке [arduinoFFT](https://www.arduino.cc/reference/en/libraries/arduinofft/), описана в скетче. Разбивает аналоговый сигнал на гармоники (второстепенные звуковые волны на частотах отличных от основного тона) и основной тон, затем расчитывает значение громкости каждой. 

```
const uint16_t samples = 64; //количество анализируемых гармоник
const double samplingFrequency = 38000; // частота сэмплирования (спектр частот в котором выбираються гармоники). Функцией повышения частоты опроса аналогового порта можем увеличить кол-во опросов до 40000. Следовательно, сможем анализировать частоты до 20кГЦ.
```
Вывод записывается в массив и отправляется json'ом
```
 numbers[i] = {vData[i]}; //save calcuated magnnitudes data to array
 doc["Harmonic"][i] = map(numbers[i], 0, 1000, 0, 100); // creating json based on array
 serializeJson(doc, Serial);
 Serial.println();

 ```
<a id="chapter-2"></a>
## Считывание информации с ТВ и управление (Appium + Java), коммуникация тестового скрипта с Arduino
### Принцип работы скрипта
Существует 2 вида тестовых скриптов:
1. Тренировочный - частично повторяет действия рабочего скрипта, изменяет настройки звука, изображения и т.д и записывает эталонные показания с анализирующего устройства в CSV таблицу. Это позволяет легко подстраивать скрипт под разные экраны. Используется на ПО с правильными настройками звука / изображения.
2. Рабочий - имитирует мануальные действия с пультом по построенному скрипту, проверяет функционал ТВ, если тестируются внешние параметры ТВ (звук, изображение) снимает показатели с анализирующего устройства и сравнивает их с эталонными показателями из CSV таблицы.
### Коммуникация скрипта с Arduino
Используется библиотека [jSerialComm](https://fazecast.github.io/jSerialComm/)
Объявление номера порта в классе:
```
private static final String COMPORT = "/dev/ttyACM0"; //"COM4", Linux
private static final String COMPORT = "COM4"; //Windows
```
Номер порта можно узнать через терминал в Arduino IDE.
Объявление COM порта в классе:
```
private static SerialPort chosenPort;
```
Инициализация COM порта 
```
chosenPort = SerialPort.getCommPort(COMPORT);
chosenPort.openPort();
chosenPort.setBaudRate(9600); // установка символьной скорости передачи
chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0); // задержки передачи
```
Закрытие порта
```
chosenPort.closePort();
```
### Запись и чтение CSV
Функция записи четырех значений в таблицу
```
    public static void writeToCSVObj(String name) throws InterruptedException {
        try {
            String PathTillProject = System.getProperty("user.dir");
            FileWriter csvWriter = new FileWriter(PathTillProject + "/src/main/SoundEthalon.csv", true); //объявляем класс FileWriter и указываем путь создания и записи таблицы
            JSONObject obj = jserialOutputObj(); // запрос данных с ардуино
            TimeUnit.SECONDS.sleep(2);
            double luminanceR = obj.getDouble("lumR");
            double luminanceG = obj.getDouble("lumG");
            double luminanceB = obj.getDouble("lumB");
            double luminanceW = obj.getDouble("lumW");
            String lumR = Double.toString(luminanceR); // превращаем числовые значения в стринги и записываем в переменные
            String lumG = Double.toString(luminanceG);
            String lumB = Double.toString(luminanceB);
            String lumW = Double.toString(luminanceW);
            csvWriter.append(name); //название настройки
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
Функция записи массива значений в таблицу
```
    public static void writeToCSVArray(String name) throws InterruptedException {
        try {
            String PathTillProject = System.getProperty("user.dir");
            FileWriter csvWriter = new FileWriter(PathTillProject + "/src/main/SoundEthalon.csv", true); //объявляем класс FileWriter и указываем путь создания и записи таблицы
            TimeUnit.SECONDS.sleep(4);
            double[] real = jserialOutputArray(); // запрос данных с ардуино
            String[] sendData = new String[real.length];
            String separator = ","; 
            csvWriter.append(name); //название настройки
            csvWriter.append(separator); //разделитель
            for (int i = 0; i < real.length; i++) {
                sendData[i] = String.valueOf(real[i]); // превращаем числовые значения в стринги и записываем в массив стрингов
                csvWriter.append(sendData[i]); // запись эелемента массива в таблицу
                csvWriter.append(separator); // разделитель после каждой итерации
            } 
            csvWriter.append("0");
            csvWriter.append("\n"); //следующая строка
            csvWriter.flush(); //очистка ввода
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```
Функция чтения переменных из таблицы
```
    public static List<String[]> getCVSFileContents(String path) { //path - путь к таблице
        List<String[]> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) { //инициализируем чтение из таблицы
            String line = "";
            while ((line = br.readLine()) != null) { //пока значения в строке на закончатся
                content.add(line.split(",")); // записываем данные в список через запятую
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
```
Функции чтения массивов из таблицы
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
### Основные функции записи/считывания
Отправка запроса на Arduino
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
Принятие данных с Arduino (переменные)
```
    private static JSONObject jserialOutputObj() {
        for (int i = 0; i < 3; i++) {
            String genuino = null; //переменная для записи вывода с ардуино
            jserialInput(10); // запрос на данные
            try {
                TimeUnit.SECONDS.sleep(4);
                input = new BufferedReader(new InputStreamReader(chosenPort.getInputStream())); //инициалиируем получение данных
                genuino = input.readLine(); //записываем в строку
                System.out.println(genuino);
                InputStream comPortInput = chosenPort.getInputStream();
                comPortInput.skip(comPortInput.available()); //скипаем, если данные не пришли
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            try {
                JSONObject obj = new JSONObject(genuino); //записывем в жсон объект для дальнейшей обработки
                return obj;
            } catch (org.json.JSONException exception) {
                if (jserialOutputObj().has("lumR") && jserialOutputObj().has("lumG") &&
                        jserialOutputObj().has("lumB") && jserialOutputObj().has("lumW")) {
                    continue; //если данные полные, закрываем цикл
                }
            }
        }
        return null;
    }
```
Принятие данных с Arduino (массив)
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
                if (arr == null) {/*.....*/ }
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
Сравнение реальных и эталонных показателей (переменные)
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
Сравнение реальных и эталонных показателей (массив)
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
Вывод результата
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
### Функции навигации и подключения
Коннект к ТВ
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
Открытие тестовых видео в ютубе
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
Упрощение нажатия кнопок пульта
```
    private static void dpad(AndroidKey key) {
        driver.pressKey(new KeyEvent(key));
    }
```
Открытие настроек через активити
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
Поиск элемента по изображению
```
    private static MobileElement findElementByImage(String using) {
        try {
            return driver.findElementByImage(using);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
```
Поиск элемента по ID
```
    private static AndroidElement findElementWrapper(String using) {
        try {
            return driver.findElement(By.id(using));
        } catch (NoSuchElementException e) {
            return null;
        }
    }
```
Поиск элемента по тексту
```
    private static AndroidElement findElementByXPathWrapper(String using) {
        try {
            return driver.findElementByXPath(using);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
```
<a id="#chapter-4"></a>
## Описание корпуса и подключений
![Вид спереди](https://github.com/Illaise/AndroidTV-Automated/blob/master/Phts/1.png)
Передняя часть состоит из 4х тоннелей в которых находятся сенсоры света. Тоннели накрываются стеклами (светофильтрами) определенного цвета — красный, зеленый, синий, белый. На каждый сенсор падает свет одного из основных цветов. В итоге имеем полноценный колориметр с возможностью запроса данных в нужное время.
![Вид снизу](https://github.com/Illaise/AndroidTV-Automated/blob/master/Phts/2.png)
Внутри расположены подставки для фиксации плат Arduino, проводов и остальных компонентов устройства.
![Вид сзади](https://github.com/Illaise/AndroidTV-Automated/blob/master/Phts/4.png)
В задней части корпуса расположены отверстия для подключения USB проводов и подачи питания, внутри расположены подставки для фиксации плат Arduino, проводов и остальных компонентов устройства.




