package SoundSpectrum;

import com.fazecast.jSerialComm.SerialPort;
import com.google.common.collect.Lists;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SoundSpectrum {
    //private static final String COMPORT = "/dev/ttyACM1"; // Linux COM3
    //private static final String COMPORT = "/dev/ttyACM0"; //Linux COM4
    private static final String COMPORT = "COM3"; //Windows
    private static final String COMPORT2 = "COM4"; //Windows
    private static SerialPort chosenPort;
    private static BufferedReader input;
    private static OutputStream output;
    private static String TVName;
    static AndroidDriver<AndroidElement> driver;
    static Map<String, Object> driver_args = new HashMap<>();
    static List<AndroidElement> elements;
    private static List<String[]> soundEthalonVal;

    public static void main(String[] args) throws InterruptedException {
        TVName = "komagome";
        //Initialize serial port
        chosenPort = SerialPort.getCommPort(COMPORT);
        chosenPort.openPort();
        chosenPort.setBaudRate(9600);
        chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        //Initialize ethalons
        soundEthalonVal = getCVSFileContents("../image-config/SoundEthalonVal.csv");
        //Connect to the TV
        try {
            connect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Report
        List<String> report = new ArrayList<>();
        report.add("Sound:");
        // Declare obj
        AndroidElement element;
        // Start script
        dpad(AndroidKey.DPAD_DOWN);
        jserialInput(1);
        TimeUnit.SECONDS.sleep(2);
        equalizerFourth();
        TimeUnit.SECONDS.sleep(10);
        startSettings();
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
                element.click();
            }
        }
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 13; i++) {
            dpad(AndroidKey.DPAD_DOWN);
        }
        try {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Сброс до настроек по умолчанию']");
            element.click();
            if (element == null) {
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Сброс до настроек по умолчанию']");
                element.click();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_RIGHT);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_UP);
        try {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Динамики']");
            element.click();
            if (element == null) {
                dpad(AndroidKey.DPAD_UP);
                dpad(AndroidKey.DPAD_UP);
                dpad(AndroidKey.DPAD_UP);
                dpad(AndroidKey.DPAD_UP);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Динамики']");
                element.click();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_UP);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Обработка Dolby Audio']");
        if (element != null) {
            dpad(AndroidKey.DPAD_UP);  // for TV with massage
        }
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Системные звуки']");
        if (element != null) {
            dpad(AndroidKey.DPAD_UP);  // for TV with massage
            dpad(AndroidKey.DPAD_CENTER);
            dpad(AndroidKey.DPAD_DOWN);
        }
        //volume
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        for (int i = 0; i < 30; i++) {
            dpad(AndroidKey.VOLUME_DOWN);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 0; i < 11; i++) {
            dpad(AndroidKey.VOLUME_UP);
        }
        TimeUnit.SECONDS.sleep(2);
        LegendsFallObj("VOLUME_10:PASSED", "VOLUME_10:FAILED", 0, report);
        for (int i = 0; i < 11; i++) {
            dpad(AndroidKey.VOLUME_UP);
        }
        TimeUnit.SECONDS.sleep(2);
        LegendsFallObj("VOLUME_20:PASSED", "VOLUME_20:FAILED", 1, report);
        for (int i = 0; i < 11; i++) {
            dpad(AndroidKey.VOLUME_UP);
        }
        TimeUnit.SECONDS.sleep(2);
        LegendsFallObj("VOLUME_30:PASSED", "VOLUME_30:FAILED", 2, report);
        for (int i = 0; i < 11; i++) {
            dpad(AndroidKey.VOLUME_DOWN);
        }
        TimeUnit.SECONDS.sleep(2);
        LegendsFallArray("STANDARD_PRESET:PASSED", "STANDARD_PRESET:FAILED", 3, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        LegendsFallArray("VIVID_PRESET:PASSED", "VIVID_PRESET:FAILED", 4, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        LegendsFallArray("SPORTS_PRESET:PASSED", "SPORTS_PRESET:FAILED", 5, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        LegendsFallArray("MOVIE_PRESET:PASSED", "MOVIE_PRESET:FAILED", 6, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        LegendsFallArray("MUSIC_PRESET:PASSED", "MUSIC_PRESET:FAILED", 7, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        LegendsFallArray("NEWS_PRESET:PASSED", "NEWS_PRESET:FAILED", 8, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_CENTER);
        // Balance
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        TimeUnit.SECONDS.sleep(2);
        balanceTest();
        TimeUnit.SECONDS.sleep(5);
        startSettings();
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
                element.click();
            }
        }
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Системные звуки']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Обработка Dolby Audio']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("10")) {
            LegendsFallObj("BALANCE_10:PASSED", "BALANCE_10:FAILED", 9, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("20")) {
            LegendsFallObj("BALANCE_20:PASSED", "BALANCE_20:FAILED", 10, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("30")) {
            LegendsFallObj("BALANCE_30:PASSED", "BALANCE_30:FAILED", 11, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("40")) {
            LegendsFallObj("BALANCE_40:PASSED", "BALANCE_40:FAILED", 12, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("50")) {
            LegendsFallObj("BALANCE_50:PASSED", "BALANCE_50:FAILED", 13, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-10")) {
            LegendsFallObj("BALANCE_-10:PASSED", "BALANCE_-10:FAILED", 14, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-20")) {
            LegendsFallObj("BALANCE_-20:PASSED", "BALANCE_-20:FAILED", 15, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-30")) {
            LegendsFallObj("BALANCE_-30:PASSED", "BALANCE_-30:FAILED", 16, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-40")) {
            LegendsFallObj("BALANCE_-40:PASSED", "BALANCE_-40:FAILED", 17, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-50")) {
            LegendsFallObj("BALANCE_-50:PASSED", "BALANCE_-50:FAILED", 18, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        // Surround
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        TimeUnit.SECONDS.sleep(2);
        equalizerFourth();
        TimeUnit.SECONDS.sleep(5);
        startSettings();
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
                element.click();
            }
        }
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Системные звуки']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Обработка Dolby Audio']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        LegendsFallArray("SURROUND_SOUND:PASSED", "SURROUND_SOUND:FAILED", 19, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        TimeUnit.SECONDS.sleep(2);
        equalizerFirst();
        TimeUnit.SECONDS.sleep(5);
        startSettings();
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
                element.click();
            }
        }
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Системные звуки']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Обработка Dolby Audio']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("0")) {
            LegendsFallObj("120Hz_0:PASSED", "120Hz_0:FAILED", 20, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-10")) {
            LegendsFallObj("120Hz_-10:PASSED", "120Hz_-10:FAILED", 21, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-20")) {
            LegendsFallObj("120Hz_-20:PASSED", "120Hz_-20:FAILED", 22, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-30")) {
            LegendsFallObj("120Hz_-30:PASSED", "120Hz_-30:FAILED", 23, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-40")) {
            LegendsFallObj("120Hz_-40:PASSED", "120Hz_-40:FAILED", 24, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-50")) {
            LegendsFallObj("120Hz_-50:PASSED", "120Hz_-50:FAILED", 25, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("0")) {
            LegendsFallObj("120Hz_0:PASSED", "120Hz_0:FAILED", 26, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("10")) {
            LegendsFallObj("120Hz_10:PASSED", "120Hz_10:FAILED", 27, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("20")) {
            LegendsFallObj("120Hz_20:PASSED", "120Hz_20:FAILED", 28, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("30")) {
            LegendsFallObj("120Hz_30:PASSED", "120Hz_30:FAILED", 29, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("40")) {
            LegendsFallObj("120Hz_40:PASSED", "120Hz_40:FAILED", 30, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("50")) {
            LegendsFallObj("120Hz_50:PASSED", "120Hz_50:FAILED", 31, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        // 500 hz
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        TimeUnit.SECONDS.sleep(2);
        equalizerSecond();
        TimeUnit.SECONDS.sleep(5);
        startSettings();
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
                element.click();
            }
        }
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Системные звуки']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Обработка Dolby Audio']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("0")) {
            LegendsFallObj("500Hz_0:PASSED", "500Hz_0:FAILED", 32, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("-10")) {
            LegendsFallObj("500Hz_-10:PASSED", "500Hz_-10:FAILED", 33, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("-20")) {
            LegendsFallObj("500Hz_-20:PASSED", "500Hz_-20:FAILED", 34, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("-30")) {
            LegendsFallObj("500Hz_-30:PASSED", "500Hz_-30:FAILED", 35, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("-40")) {
            LegendsFallObj("500Hz_-40:PASSED", "500Hz_-40:FAILED", 36, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("-50")) {
            LegendsFallObj("500Hz_-50:PASSED", "500Hz_-50:FAILED", 37, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("0")) {
            LegendsFallObj("500Hz_0:PASSED", "500Hz_0:FAILED", 38, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("10")) {
            LegendsFallObj("500Hz_10:PASSED", "500Hz_10:FAILED", 39, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("20")) {
            LegendsFallObj("500Hz_20:PASSED", "500Hz_20:FAILED", 40, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("30")) {
            LegendsFallObj("500Hz_30:PASSED", "500Hz_30:FAILED", 41, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("40")) {
            LegendsFallObj("500Hz_40:PASSED", "500Hz_40:FAILED", 42, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("50")) {
            LegendsFallObj("500Hz_50:PASSED", "500Hz_50:FAILED", 43, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        // 1500hz
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        TimeUnit.SECONDS.sleep(2);
        equalizerThird();
        TimeUnit.SECONDS.sleep(5);
        startSettings();
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
                element.click();
            }
        }
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Системные звуки']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Обработка Dolby Audio']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("0")) {
            LegendsFallObj("1500Hz_0:PASSED", "1500Hz_0:FAILED", 44, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("-10")) {
            LegendsFallObj("1500Hz_-10:PASSED", "1500Hz_-10:FAILED", 45, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("-20")) {
            LegendsFallObj("1500Hz_-20:PASSED", "1500Hz_-20:FAILED", 46, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("-30")) {
            LegendsFallObj("1500Hz_-30:PASSED", "1500Hz_-30:FAILED", 47, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("-40")) {
            LegendsFallObj("1500Hz_-40:PASSED", "1500Hz_-40:FAILED", 48, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("-50")) {
            LegendsFallObj("1500Hz_-50:PASSED", "1500Hz_-50:FAILED", 49, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("0")) {
            LegendsFallObj("1500Hz_0:PASSED", "1500Hz_0:FAILED", 50, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("10")) {
            LegendsFallObj("1500Hz_10:PASSED", "1500Hz_10:FAILED", 51, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("20")) {
            LegendsFallObj("1500Hz_20:PASSED", "1500Hz_20:FAILED", 52, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("30")) {
            LegendsFallObj("1500Hz_30:PASSED", "1500Hz_30:FAILED", 53, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("40")) {
            LegendsFallObj("1500Hz_40:PASSED", "1500Hz_40:FAILED", 54, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("50")) {
            LegendsFallObj("1500Hz_50:PASSED", "1500Hz_50:FAILED", 55, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        //5000hz
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        TimeUnit.SECONDS.sleep(2);
        equalizerFourth();
        TimeUnit.SECONDS.sleep(5);
        startSettings();
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
                element.click();
            }
        }
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Системные звуки']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Обработка Dolby Audio']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("0")) {
            LegendsFallObj("5000Hz_0:PASSED", "5000Hz_0:FAILED", 56, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("-10")) {
            LegendsFallObj("5000Hz_-10:PASSED", "5000Hz_-10:FAILED", 57, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("-20")) {
            LegendsFallObj("5000Hz_-20:PASSED", "5000Hz_-20:FAILED", 58, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("-30")) {
            LegendsFallObj("5000Hz_-30:PASSED", "5000Hz_-30:FAILED", 59, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("-40")) {
            LegendsFallObj("5000Hz_-40:PASSED", "5000Hz_-40:FAILED", 60, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("-50")) {
            LegendsFallObj("5000Hz_-50:PASSED", "5000Hz_-50:FAILED", 61, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("0")) {
            LegendsFallObj("5000Hz_0:PASSED", "5000Hz_0:FAILED", 62, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("10")) {
            LegendsFallObj("5000Hz_10:PASSED", "5000Hz_10:FAILED", 63, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("20")) {
            LegendsFallObj("5000Hz_20:PASSED", "5000Hz_20:FAILED", 64, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("30")) {
            LegendsFallObj("5000Hz_30:PASSED", "5000Hz_30:FAILED", 65, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("40")) {
            LegendsFallObj("5000Hz_40:PASSED", "5000Hz_40:FAILED", 66, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("50")) {
            LegendsFallObj("5000Hz_50:PASSED", "5000Hz_50:FAILED", 67, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        //10000hz
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        TimeUnit.SECONDS.sleep(2);
        equalizerFifth();
        TimeUnit.SECONDS.sleep(5);
        startSettings();
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
                element.click();
            }
        }
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Системные звуки']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Обработка Dolby Audio']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("0")) {
            LegendsFallObj("10000Hz_0:PASSED", "10000Hz_0:FAILED", 68, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("-10")) {
            LegendsFallObj("10000Hz_-10:PASSED", "10000Hz_-10:FAILED", 69, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("-20")) {
            LegendsFallObj("10000Hz_-20:PASSED", "10000Hz_-20:FAILED", 70, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("-30")) {
            LegendsFallObj("10000Hz_-30:PASSED", "10000Hz_-30:FAILED", 71, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("-40")) {
            LegendsFallObj("10000Hz_-40:PASSED", "10000Hz_-40:FAILED", 72, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("-50")) {
            LegendsFallObj("10000Hz_-50:PASSED", "10000Hz_-50:FAILED", 73, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("0")) {
            LegendsFallObj("10000Hz_0:PASSED", "10000Hz_0:FAILED", 74, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("10")) {
            LegendsFallObj("10000Hz_10:PASSED", "10000Hz_10:FAILED", 75, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("20")) {
            LegendsFallObj("10000Hz_20:PASSED", "10000Hz_20:FAILED", 76, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("30")) {
            LegendsFallObj("10000Hz_30:PASSED", "10000Hz_30:FAILED", 77, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("40")) {
            LegendsFallObj("10000Hz_40:PASSED", "10000Hz_40:FAILED", 78, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("50")) {
            LegendsFallObj("10000Hz_50:PASSED", "10000Hz_50:FAILED", 79, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        TimeUnit.SECONDS.sleep(5);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_RIGHT);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        AndroidElement textfield = driver.findElementByClassName("android.widget.EditText");
        if (textfield == null) {
            dpad(AndroidKey.BACK);
            dpad(AndroidKey.DPAD_RIGHT);
            dpad(AndroidKey.DPAD_CENTER);
        }
        textfield.sendKeys("Включи розетку");
        TimeUnit.SECONDS.sleep(10);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        //Розетка вкл!
        TimeUnit.SECONDS.sleep(2);
        equalizerFourth(); //TODO Change video
        TimeUnit.SECONDS.sleep(5);
        startSettings();
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
                element.click();
            }
        }
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Системные звуки']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Обработка Dolby Audio']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        LegendsFallArray("DIGITAL_OUTPUT_AUTO:PASSED", "DIGITAL_OUTPUT_AUTO:FAILED", 80, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        LegendsFallArray("DIGITAL_OUTPUT_BYPASS:PASSED", "DIGITAL_OUTPUT_BYPASS:FAILED", 81, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        LegendsFallArray("DIGITAL_OUTPUT_PCM:PASSED", "DIGITAL_OUTPUT_PCM:FAILED", 82, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        LegendsFallArray("DIGITAL_OUTPUT_DDP:PASSED", "DIGITAL_OUTPUT_DDP:FAILED", 83, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        LegendsFallArray("DIGITAL_OUTPUT_DD:PASSED", "DIGITAL_OUTPUT_DD:FAILED", 84, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_UP);
        }
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_RIGHT);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        textfield = driver.findElementByClassName("android.widget.EditText");
        if (textfield == null) {
            dpad(AndroidKey.BACK);
            dpad(AndroidKey.BACK);
            dpad(AndroidKey.DPAD_UP);
            dpad(AndroidKey.DPAD_RIGHT);
            dpad(AndroidKey.DPAD_CENTER);
        }
        textfield.sendKeys("Выключи розетку");
        dpad(AndroidKey.ENTER);
        TimeUnit.SECONDS.sleep(5);
        dpad(AndroidKey.VOLUME_MUTE);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        //Розетка выкл!
        //Button Sound
        TimeUnit.SECONDS.sleep(4);
        chosenPort.closePort();
        TimeUnit.SECONDS.sleep(2);
        chosenPort = SerialPort.getCommPort(COMPORT2);
        chosenPort.openPort();
        chosenPort.setBaudRate(9600);
        chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        TimeUnit.SECONDS.sleep(4);
        startSettings();
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Приложения']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Приложения']");
                element.click();
            }
        }
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Все приложения']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Все приложения']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 0; i < 30; i++) {
            dpad(AndroidKey.DPAD_DOWN);
        }
        TimeUnit.SECONDS.sleep(2);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Показывать системные приложения']");
        element.click();
        TimeUnit.SECONDS.sleep(2);
        jserialInput(11); // IRSender
        TimeUnit.SECONDS.sleep(2);
        chosenPort.closePort();
        TimeUnit.SECONDS.sleep(2);
        chosenPort = SerialPort.getCommPort(COMPORT);
        chosenPort.openPort();
        chosenPort.setBaudRate(9600);
        chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        TimeUnit.SECONDS.sleep(2);
        boolean buttSound = false;
        for (int i = 0; i < 18; i++) {
            JSONObject obj = jserialOutputObj();
            int soundCheck = obj.getInt("volumeLevel");
            if (soundCheck > 15){
                buttSound = true;
                continue;
            }
        }
        if (!buttSound) report.add("(+) " + "BUTT_SOUND: PASSED");
        else report.add("(+) " + "BUTT_SOUND: FAILED");
        // AVL
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        TimeUnit.SECONDS.sleep(2);
        balanceTest(); //TODO video
        TimeUnit.SECONDS.sleep(5);
        startSettings();
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
                element.click();
            }
        }
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Звук']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Системные звуки']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Обработка Dolby Audio']");
        if (element != null) {
            dpad(AndroidKey.DPAD_DOWN); // for TV with massage
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_DOWN);
        }
        dpad(AndroidKey.DPAD_CENTER);
        boolean avl = false;
        for (int i = 0; i < 18; i++) {
            JSONObject obj = jserialOutputObj();
            int soundCheck = obj.getInt("volumeLevel");
            if (soundCheck < 300) {
                avl = true;
            }
        }
        if (!avl) report.add("(+) " + "AVL: PASSED");
        else report.add("(-) " + "AVL: FAILED");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        TimeUnit.SECONDS.sleep(2);
        chosenPort.closePort();
        driver.quit();

        // Write report
        String reportStr = String.join("\n", report);
        try {
            FileWriter myWriter = new FileWriter("report4.txt");
            myWriter.write(reportStr);
            myWriter.close();
            System.out.println("Successfully wrote to the file!");
        } catch (IOException e) {
            System.out.println("An error occurred:");
            e.printStackTrace();
        }
    }

    //send int to arduino, each number have different function, detailed in arduino code
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

    //get json obj from arduino
    private static JSONObject jserialOutputObj() {
        for (int i = 0; i < 5; i++) {
            String genuino = "";
            jserialInput(1);
            try {
                TimeUnit.SECONDS.sleep(4);
                input = new BufferedReader(new InputStreamReader(chosenPort.getInputStream()));
                genuino = input.readLine();
                System.out.println(genuino);
                InputStream comPortInput = chosenPort.getInputStream();
                comPortInput.skip(comPortInput.available());
                Thread.sleep(100);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            try {
                JSONObject obj = new JSONObject(genuino);
                return obj;
            } catch (org.json.JSONException exception) {
                continue;
            }
        }
        return null;
    }

    //get json array from arduino
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

    // obj comparsion
    private static boolean objScreenVal(int rowCount, List<String[]> contents) {
        JSONObject obj;
        boolean result = false;
        String[] row = contents.get(rowCount);
        double volumeLevelEthalon = Double.parseDouble(row[1]);
        obj = jserialOutputObj();
        double volumeLevelReal = obj.getDouble("volumeLevel");
        if (volumeLevelEthalon - volumeLevelReal < 5) {
            result = true;
        }
        return result;
    }

    // array comparsion
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

    //results
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

    private static void balanceTest() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/cyv-W1pjtrs?t=902\""));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            dpad(AndroidKey.DPAD_DOWN);
        }
    }

    private static void equalizerFirst() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/UlsrFMJkNBc?t=10\""));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            dpad(AndroidKey.DPAD_DOWN);
        }
    }

    private static void equalizerSecond() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/xS3c2DcrWgk?t=11\""));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            dpad(AndroidKey.DPAD_DOWN);
        }
    }

    private static void equalizerThird() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/3el7QptqE1w?t=10\""));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            dpad(AndroidKey.DPAD_DOWN);
        }
    }

    private static void equalizerFourth() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/b_wbLqci_iQ?t=11\""));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            dpad(AndroidKey.DPAD_DOWN);
        }
    }

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

    private static void dpad(AndroidKey key) {
        driver.pressKey(new KeyEvent(key));
    }

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

    private static MobileElement findElementByImage(String using) {
        try {
            return driver.findElementByImage(using);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private static AndroidElement findElementWrapper(String using) {
        try {
            return driver.findElement(By.id(using));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private static AndroidElement findElementByXPathWrapper(String using) {
        try {
            return driver.findElementByXPath(using);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

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

    public static List<String[]> getCVSFileContents(String path) {
        List<String[]> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}

