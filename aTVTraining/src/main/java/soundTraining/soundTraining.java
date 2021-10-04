package soundTraining;

import com.fazecast.jSerialComm.SerialPort;

import java.io.*;

import com.google.common.collect.Lists;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.json.*;

public class soundTraining {
    //private static final String COMPORT = "/dev/ttyACM0"; //Linux
    private static final String COMPORT = "COM3"; //Windows
    private static SerialPort chosenPort;
    private static BufferedReader input;
    static AndroidDriver<AndroidElement> driver;
    static Map<String, Object> driver_args = new HashMap<>();
    static List<AndroidElement> elements;

    public static void main(String[] args) throws InterruptedException {
        //Initialize serial port
        chosenPort = SerialPort.getCommPort(COMPORT);
        chosenPort.openPort();
        chosenPort.setBaudRate(9600);
        chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        //Connect to the TV
        try {
            connect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Report
        List<String> report = new ArrayList<>();
        report.add("Sound:");
        report.add("ENG LOCAL:\n");
        // Declare obj
        AndroidElement element;
        // Start script
        dpad(AndroidKey.DPAD_DOWN);
        jserialInput(1);
        TimeUnit.SECONDS.sleep(2);
        equalizerFourth();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            equalizerFourth();
            TimeUnit.SECONDS.sleep(5);
        }
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
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_DOWN);
        }
        TimeUnit.SECONDS.sleep(2);
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
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_RIGHT);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 6; i++) {
            dpad(AndroidKey.DPAD_UP);
        }
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
        TimeUnit.SECONDS.sleep(2);
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
        writeToCSVObj("VOLUME_10");
        for (int i = 0; i < 11; i++) {
            dpad(AndroidKey.VOLUME_UP);
        }
        TimeUnit.SECONDS.sleep(2);
        writeToCSVObj("VOLUME_20");
        for (int i = 0; i < 11; i++) {
            dpad(AndroidKey.VOLUME_UP);
        }
        TimeUnit.SECONDS.sleep(2);
        writeToCSVObj("VOLUME_30");
        for (int i = 0; i < 11; i++) {
            dpad(AndroidKey.VOLUME_DOWN);
        }
        TimeUnit.SECONDS.sleep(2);
        writeToCSVArray("STANDARD_PRESET");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        writeToCSVArray("VIVID_PRESET");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        writeToCSVArray("SPORTS_PRESET");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        writeToCSVArray("MOVIE_PRESET");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        writeToCSVArray("MUSIC_PRESET");
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
        writeToCSVArray("NEWS_PRESET");
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
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            balanceTest();
            TimeUnit.SECONDS.sleep(5);
        }
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
            writeToCSVObj("BALANCE_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("20")) {
            writeToCSVObj("BALANCE_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("30")) {
            writeToCSVObj("BALANCE_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("40")) {
            writeToCSVObj("BALANCE_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("50")) {
            writeToCSVObj("BALANCE_50");
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
            writeToCSVObj("BALANCE_-10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-20")) {
            writeToCSVObj("BALANCE_-20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-30")) {
            writeToCSVObj("BALANCE_-30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-40")) {
            writeToCSVObj("BALANCE_-40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-50")) {
            writeToCSVObj("BALANCE_-50");
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
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            equalizerFourth();
            TimeUnit.SECONDS.sleep(5);
        }
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
        writeToCSVArray("SURROUND_SOUND");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        TimeUnit.SECONDS.sleep(5);
        equalizerFirst();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            equalizerFirst();
            TimeUnit.SECONDS.sleep(5);
        }
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
            writeToCSVObj("120Hz_0");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-10")) {
            writeToCSVObj("120Hz_-10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-20")) {
            writeToCSVObj("120Hz_-20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-30")) {
            writeToCSVObj("120Hz_-30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-40")) {
            writeToCSVObj("120Hz_-40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("-50")) {
            writeToCSVObj("120Hz_-50");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("0")) {
            writeToCSVObj("120Hz_0");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("10")) {
            writeToCSVObj("120Hz_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("20")) {
            writeToCSVObj("120Hz_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("30")) {
            writeToCSVObj("120Hz_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("40")) {
            writeToCSVObj("120Hz_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("50")) {
            writeToCSVObj("120Hz_50");
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
        TimeUnit.SECONDS.sleep(5);
        equalizerSecond();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            equalizerSecond();
            TimeUnit.SECONDS.sleep(5);
        }
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
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("0")) {
            writeToCSVObj("500Hz_0");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("-10")) {
            writeToCSVObj("500Hz_-10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("-20")) {
            writeToCSVObj("500Hz_-20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("-30")) {
            writeToCSVObj("500Hz_-30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("-40")) {
            writeToCSVObj("500Hz_-40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("-50")) {
            writeToCSVObj("500Hz_-50");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("0")) {
            writeToCSVObj("500Hz_0");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("10")) {
            writeToCSVObj("500Hz_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("20")) {
            writeToCSVObj("500Hz_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("30")) {
            writeToCSVObj("500Hz_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("40")) {
            writeToCSVObj("500Hz_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("50")) {
            writeToCSVObj("500Hz_50");
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
        TimeUnit.SECONDS.sleep(5);
        equalizerThird();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            equalizerThird();
            TimeUnit.SECONDS.sleep(5);
        }
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
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("0")) {
            writeToCSVObj("1500Hz_0");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("-10")) {
            writeToCSVObj("1500Hz_-10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("-20")) {
            writeToCSVObj("1500Hz_-20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("-30")) {
            writeToCSVObj("1500Hz_-30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("-40")) {
            writeToCSVObj("1500Hz_-40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("-50")) {
            writeToCSVObj("1500Hz_-50");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("0")) {
            writeToCSVObj("1500Hz_0");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("10")) {
            writeToCSVObj("1500Hz_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("20")) {
            writeToCSVObj("1500Hz_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("30")) {
            writeToCSVObj("1500Hz_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("40")) {
            writeToCSVObj("1500Hz_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("50")) {
            writeToCSVObj("1500Hz_50");
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
        TimeUnit.SECONDS.sleep(5);
        equalizerFourth();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            equalizerFourth();
            TimeUnit.SECONDS.sleep(5);
        }
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
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("0")) {
            writeToCSVObj("5000Hz_0");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("-10")) {
            writeToCSVObj("5000Hz_-10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("-20")) {
            writeToCSVObj("5000Hz_-20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("-30")) {
            writeToCSVObj("5000Hz_-30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("-40")) {
            writeToCSVObj("5000Hz_-40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("-50")) {
            writeToCSVObj("5000Hz_-50");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("0")) {
            writeToCSVObj("5000Hz_0");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("10")) {
            writeToCSVObj("5000Hz_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("20")) {
            writeToCSVObj("5000Hz_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("30")) {
            writeToCSVObj("5000Hz_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("40")) {
            writeToCSVObj("5000Hz_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("50")) {
            writeToCSVObj("5000Hz_50");
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
        TimeUnit.SECONDS.sleep(5);
        equalizerFifth();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            equalizerFifth();
            TimeUnit.SECONDS.sleep(5);
        }
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
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("0")) {
            writeToCSVObj("10000Hz_0");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("-10")) {
            writeToCSVObj("10000Hz_-10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("-20")) {
            writeToCSVObj("10000Hz_-20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("-30")) {
            writeToCSVObj("10000Hz_-30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("-40")) {
            writeToCSVObj("10000Hz_-40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("-50")) {
            writeToCSVObj("10000Hz_-50");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("0")) {
            writeToCSVObj("10000Hz_0");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("10")) {
            writeToCSVObj("10000Hz_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("20")) {
            writeToCSVObj("10000Hz_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("30")) {
            writeToCSVObj("10000Hz_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("40")) {
            writeToCSVObj("10000Hz_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("50")) {
            writeToCSVObj("10000Hz_50");
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
        dpad(AndroidKey.ENTER);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.VOLUME_MUTE);
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
        writeToCSVArray("DIGITAL_OUTPUT_AUTO");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        writeToCSVArray("DIGITAL_OUTPUT_BYPASS");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        writeToCSVArray("DIGITAL_OUTPUT_PCM");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        writeToCSVArray("DIGITAL_OUTPUT_DDP");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        writeToCSVArray("DIGITAL_OUTPUT_DD");
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
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_RIGHT);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        textfield = driver.findElementByClassName("android.widget.EditText");
        if (textfield == null) {
            dpad(AndroidKey.BACK);
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
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.HOME);
        TimeUnit.SECONDS.sleep(2);
        chosenPort.closePort();
        driver.quit();
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

    public static void writeToCSVObj(String name) {
        try {
            String PathTillProject = System.getProperty("user.dir");
            FileWriter csvWriter = new FileWriter(PathTillProject + "/src/main/SoundEthalon.csv", true);
            JSONObject obj;
            obj = jserialOutputObj();
            double soundLevel = obj.getDouble("volumeLevel");
            String vol = Double.toString(soundLevel);
            csvWriter.append(name);
            csvWriter.append(",");
            csvWriter.append(vol);
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToCSVArray(String name) throws InterruptedException {
        try {
            String PathTillProject = System.getProperty("user.dir");
            FileWriter csvWriter = new FileWriter(PathTillProject + "/src/main/SoundEthalon.csv", true);
            TimeUnit.SECONDS.sleep(4);
            double[] real = jserialOutputArray();
            String[] sendData = new String[real.length];
            String separator = ",";
            csvWriter.append(name);
            csvWriter.append(separator);
            for (int i = 0; i < real.length; i++) {
                sendData[i] = String.valueOf(real[i]);
                csvWriter.append(sendData[i]);
                csvWriter.append(separator);
            }
            csvWriter.append("0");
            csvWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        dc.setCapability("deviceName", "komagome");
        dc.setCapability("noReset", "true");
        dc.setCapability("platformName", "Android");
        dc.setCapability("platformVersion", "9");
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver<AndroidElement>(url, dc);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        System.out.println("Application started....");
    }
}
