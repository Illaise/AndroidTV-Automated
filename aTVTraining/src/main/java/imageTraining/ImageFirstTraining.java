package imageTraining;

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

public class ImageFirstTraining {
    //private static final String COMPORT = "/dev/ttyACM0"; //Linux
    private static final String COMPORT = "COM4"; //Windows
    private static SerialPort chosenPort;
    private static BufferedReader input;
    static AndroidDriver<AndroidElement> driver;
    static Map<String, Object> driver_args = new HashMap<>();
    static List<AndroidElement> elements;

    public static void runTests(String tvName) throws InterruptedException {
        //Initialize serial port
        chosenPort = SerialPort.getCommPort(COMPORT);
        chosenPort.openPort();

        chosenPort.setBaudRate(9600);
        chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        //Connect to the TV
        try {
            connect(tvName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Report
        List<String> report = new ArrayList<>();
        report.add("ENG LOCAL:\n");
        report.add("Image[1]:");
        // Declare obj
        AndroidElement element;
        // Start script
        dpad(AndroidKey.DPAD_DOWN);
        jserialInput(10);
        TimeUnit.SECONDS.sleep(5);
        brownScreenOne();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Добавить аккаунт']");
        if (element != null) {
            dpad(AndroidKey.DPAD_CENTER);
        }
        TimeUnit.SECONDS.sleep(2);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            brownScreenOne();
            TimeUnit.SECONDS.sleep(5);
        }
        startImageSettings();
        TimeUnit.SECONDS.sleep(5);
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
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Изображение']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Изображение']");
                element.click();
            }
        }
        for (int i = 1; i < 11; i++) {
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
        dpad(AndroidKey.DPAD_RIGHT);
        dpad(AndroidKey.DPAD_RIGHT);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(4);
        for (int i = 1; i < 5; i++) {
            dpad(AndroidKey.DPAD_UP);
        }
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Режим изображения']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_UP);
                dpad(AndroidKey.DPAD_UP);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Режим изображения']");
                element.click();
            }
        }
        for (int i = 1; i < 4; i++) {
            dpad(AndroidKey.DPAD_UP);
        }
        TimeUnit.SECONDS.sleep(1);
        dpad(AndroidKey.DPAD_CENTER);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Отмена']");
        if (element != null) {
            dpad(AndroidKey.DPAD_UP);  // for TV with massage
            dpad(AndroidKey.DPAD_CENTER); // for TV with massage
        }
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_UP);
        TimeUnit.SECONDS.sleep(4);
        writeToCSVObj("USER_PRESET");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Отмена']");
        if (element != null) {
            dpad(AndroidKey.DPAD_UP);  // for TV with massage
            dpad(AndroidKey.DPAD_CENTER); // for TV with massage
        }
        TimeUnit.SECONDS.sleep(4);
        writeToCSVObj("STANDARD_PRESET");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Отмена']");
        if (element != null) {
            dpad(AndroidKey.DPAD_UP);  // for TV with massage
            dpad(AndroidKey.DPAD_CENTER); // for TV with massage
        }
        TimeUnit.SECONDS.sleep(4);
        writeToCSVObj("VIVID_PRESET");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Отмена']");
        if (element != null) {
            dpad(AndroidKey.DPAD_UP);  // for TV with massage
            dpad(AndroidKey.DPAD_CENTER); // for TV with massage
        }
        TimeUnit.SECONDS.sleep(4);
        writeToCSVObj("SPORT_PRESET");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Отмена']");
        if (element != null) {
            dpad(AndroidKey.DPAD_UP);  // for TV with massage
            dpad(AndroidKey.DPAD_CENTER); // for TV with massage
        }
        TimeUnit.SECONDS.sleep(4);
        writeToCSVObj("MOVIE_PRESET");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Отмена']");
        if (element != null) {
            dpad(AndroidKey.DPAD_UP);  // for TV with massage
            dpad(AndroidKey.DPAD_CENTER); // for TV with massage
        }
        TimeUnit.SECONDS.sleep(4);
        writeToCSVObj("GAME_PRESET");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Отмена']");
        if (element != null) {
            dpad(AndroidKey.DPAD_UP);  // for TV with massage
            dpad(AndroidKey.DPAD_CENTER); // for TV with massage
        }
        TimeUnit.SECONDS.sleep(4);
        writeToCSVObj("ENERGY_SAVE_PRESET");
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 7; i++) {
            dpad(AndroidKey.DPAD_UP);
        }
        dpad(AndroidKey.DPAD_CENTER);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Отмена']");
        if (element != null) {
            dpad(AndroidKey.DPAD_UP);  // for TV with massage
            dpad(AndroidKey.DPAD_CENTER); // for TV with massage
        }
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("100")) {
            writeToCSVObj("CONTRAST_100");
        }
        for (int i = 1; i < 10; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        if (elements.get(2).getText().equals("91")) {
            writeToCSVObj("CONTRAST_90");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 12; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("80")) {
            writeToCSVObj("CONTRAST_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("70")) {
            writeToCSVObj("CONTRAST_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("60")) {
            writeToCSVObj("CONTRAST_60");
        }
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("47")) {
            writeToCSVObj("CONTRAST_50");
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("40")) {
            writeToCSVObj("CONTRAST_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("30")) {
            writeToCSVObj("CONTRAST_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("20")) {
            writeToCSVObj("CONTRAST_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);

        if (elements.get(2).getText().equals("10")) {
            writeToCSVObj("CONTRAST_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("0")) {
            writeToCSVObj("CONTRAST_0");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        TimeUnit.SECONDS.sleep(5);
        greyFiveNine();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            greyFiveNine();
            TimeUnit.SECONDS.sleep(5);
        }
        startImageSettings();
        TimeUnit.SECONDS.sleep(2);
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
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Изображение']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Изображение']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("100")) {
            writeToCSVObj("BACKLIGHT_100");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("90")) {
            writeToCSVObj("BACKLIGHT_90");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("80")) {
            writeToCSVObj("BACKLIGHT_80");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("70")) {
            writeToCSVObj("BACKLIGHT_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("60")) {
            writeToCSVObj("BACKLIGHT_60");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("50")) {
            writeToCSVObj("BACKLIGHT_50");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("40")) {
            writeToCSVObj("BACKLIGHT_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("30")) {
            writeToCSVObj("BACKLIGHT_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("20")) {
            writeToCSVObj("BACKLIGHT_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("10")) {
            writeToCSVObj("BACKLIGHT_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(5);
        if (elements.get(0).getText().equals("0")) {
            writeToCSVObj("BACKLIGHT_0");
        }
        TimeUnit.SECONDS.sleep(5);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 101; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("100")) {
            writeToCSVObj("BRIGHTNESS_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("90")) {
            writeToCSVObj("BRIGHTNESS_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("80")) {
            writeToCSVObj("BRIGHTNESS_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("70")) {
            writeToCSVObj("BRIGHTNESS_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("60")) {
            writeToCSVObj("BRIGHTNESS_60");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("50")) {
            writeToCSVObj("BRIGHTNESS_50");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("40")) {
            writeToCSVObj("BRIGHTNESS_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("30")) {
            writeToCSVObj("BRIGHTNESS_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("20")) {
            writeToCSVObj("BRIGHTNESS_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("10")) {
            writeToCSVObj("BRIGHTNESS_10");

        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("0")) {
            writeToCSVObj("BRIGHTNESS_0");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        TimeUnit.SECONDS.sleep(5);
        brownScreenTwo();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            brownScreenTwo();
            TimeUnit.SECONDS.sleep(5);
        }
        startImageSettings();
        TimeUnit.SECONDS.sleep(5);
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
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Изображение']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Изображение']");
                element.click();
            }
        }
        for (int i = 1; i < 5; i++) {
            dpad(AndroidKey.DPAD_DOWN);
        }
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("100")) {
            writeToCSVObj("SATURATION_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("90")) {
            writeToCSVObj("SATURATION_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("80")) {
            writeToCSVObj("SATURATION_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("70")) {
            writeToCSVObj("SATURATION_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("60")) {
            writeToCSVObj("SATURATION_60");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("50")) {
            writeToCSVObj("SATURATION_50");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("40")) {
            writeToCSVObj("SATURATION_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("30")) {
            writeToCSVObj("SATURATION_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("20")) {
            writeToCSVObj("SATURATION_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("10")) {
            writeToCSVObj("SATURATION_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("0")) {
            writeToCSVObj("SATURATION_0");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("50")) {
            writeToCSVObj("HUE_50");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("40")) {
            writeToCSVObj("HUE_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("30")) {
            writeToCSVObj("HUE_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("20")) {
            writeToCSVObj("HUE_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("10")) {
            writeToCSVObj("HUE_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("0")) {
            writeToCSVObj("HUE_0");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(5);
        if (elements.get(4).getText().equals("-10")) {
            writeToCSVObj("HUE_-10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("-20")) {
            writeToCSVObj("HUE_-20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("-30")) {
            writeToCSVObj("HUE_-30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("-40")) {
            writeToCSVObj("HUE_-40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("-50")) {
            writeToCSVObj("HUE_-50");
        }
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_CENTER);
        writeToCSVObj("GAMMA_STANDARD");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_CENTER);
        writeToCSVObj("GAMMA_DARK");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        writeToCSVObj("GAMMA_BRIGHT");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        writeToCSVObj("COLOR_TEMPERATURE_COLD");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        writeToCSVObj("COLOR_TEMPERATURE_STANDARD");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        writeToCSVObj("COLOR_TEMPERATURE_WARM");
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 101; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("100")) {
            writeToCSVObj("RED_GAIN_100");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(5);
        if (elements.get(0).getText().equals("80")) {
            writeToCSVObj("RED_GAIN_80");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("60")) {
            writeToCSVObj("RED_GAIN_60");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("40")) {
            writeToCSVObj("RED_GAIN_40");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("20")) {
            writeToCSVObj("RED_GAIN_20");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("0")) {
            writeToCSVObj("RED_GAIN_0");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("-20")) {
            writeToCSVObj("RED_GAIN_-20");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("-40")) {
            writeToCSVObj("RED_GAIN_-40");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("-60")) {
            writeToCSVObj("RED_GAIN_-60");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("-80")) {
            writeToCSVObj("RED_GAIN_-80");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("-100")) {
            writeToCSVObj("RED_GAIN_-100");
        }
        for (int i = 1; i < 101; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 1; i < 101; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("100")) {
            writeToCSVObj("GREEN_GAIN_100");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("80")) {
            writeToCSVObj("GREEN_GAIN_80");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("60")) {
            writeToCSVObj("GREEN_GAIN_60");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("40")) {
            writeToCSVObj("GREEN_GAIN_40");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(5);
        if (elements.get(1).getText().equals("20")) {
            writeToCSVObj("GREEN_GAIN_20");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("0")) {
            writeToCSVObj("GREEN_GAIN_0");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("-20")) {
            writeToCSVObj("GREEN_GAIN_-20");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(5);
        if (elements.get(1).getText().equals("-40")) {
            writeToCSVObj("GREEN_GAIN_-40");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("-60")) {
            writeToCSVObj("GREEN_GAIN_-60");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("-80")) {
            writeToCSVObj("GREEN_GAIN_-80");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("-100")) {
            writeToCSVObj("GREEN_GAIN_-100");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 101; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);

        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        TimeUnit.SECONDS.sleep(5);
        blueScreenOne();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            blueScreenOne();
            TimeUnit.SECONDS.sleep(5);
        }
        startImageSettings();
        TimeUnit.SECONDS.sleep(2);
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
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Изображение']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Изображение']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 10; i++) {
            dpad(AndroidKey.DPAD_DOWN);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 101; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("100")) {
            writeToCSVObj("BLUE_GAIN_100");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("80")) {
            writeToCSVObj("BLUE_GAIN_80");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("60")) {
            writeToCSVObj("BLUE_GAIN_60");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("40")) {
            writeToCSVObj("BLUE_GAIN_40");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("20")) {
            writeToCSVObj("BLUE_GAIN_20");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("0")) {
            writeToCSVObj("BLUE_GAIN_0");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("-20")) {
            writeToCSVObj("BLUE_GAIN_-20");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("-40")) {
            writeToCSVObj("BLUE_GAIN_-40");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("-60")) {
            writeToCSVObj("BLUE_GAIN_-60");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("-80")) {
            writeToCSVObj("BLUE_GAIN_-80");
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("-100")) {
            writeToCSVObj("BLUE_GAIN_-100");
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 101; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        chosenPort.closePort();
        driver.quit();
        // Write report
        String reportStr = String.join("\n", report);
        try {
            FileWriter myWriter = new FileWriter("report3-1.txt");
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
        for (int i = 0; i < 3; i++) {
            String genuino = null;
            jserialInput(10);
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            try {
                input = new BufferedReader(new InputStreamReader(chosenPort.getInputStream()));
                genuino = input.readLine();
                System.out.println(genuino);
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
                if (jserialOutputObj().has("lumR") && jserialOutputObj().has("lumG") &&
                        jserialOutputObj().has("lumB") && jserialOutputObj().has("lumW")) {
                    continue;
                }
            }
        }
        return null;
    }

    //get json array from arduino
    private static double[] jserialOutputArray() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        String arduino = null;
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(chosenPort.getInputStream()));
            arduino = input.readLine();
            Thread.sleep(100);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        JSONObject obj = new JSONObject(arduino);
        JSONArray arr = obj.getJSONArray("lumin");
        if (arr == null) {
        }
        double[] numbers = new double[arr.length()];
        for (int i = 0; i < arr.length(); ++i) {
            numbers[i] = arr.optDouble(i);
        }
        return numbers;
    }

    public static void writeToCSVObj(String name) throws InterruptedException {
        try {
            String PathTillProject = System.getProperty("user.dir");
            FileWriter csvWriter = new FileWriter(PathTillProject + "/src/main/ImageFirstEthalon.csv", true);
            JSONObject obj;
            obj = jserialOutputObj();
            TimeUnit.SECONDS.sleep(2);
            double luminanceR = obj.getDouble("lumR");
            double luminanceG = obj.getDouble("lumG");
            double luminanceB = obj.getDouble("lumB");
            double luminanceW = obj.getDouble("lumW");
            String lumR = Double.toString(luminanceR);
            String lumG = Double.toString(luminanceG);
            String lumB = Double.toString(luminanceB);
            String lumW = Double.toString(luminanceW);
            csvWriter.append(name);
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

    private static void dpad(AndroidKey key) {
        driver.pressKey(new KeyEvent(key));
    }

    private static void startImageSettings() {
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

    private static void brownScreenOne() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://www.youtube.com/watch?v=49moZyoW3H8&t=4s\""));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            dpad(AndroidKey.DPAD_DOWN);
        }
    }

    private static void greyFiveNine() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/QH74pprdy5o?t=312\""));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            dpad(AndroidKey.DPAD_DOWN);
        }
    }

    private static void brownScreenTwo() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/ce9jTI-b1zo?t=2129\""));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            dpad(AndroidKey.DPAD_DOWN);
        }
    }

    private static void blueScreenOne() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/j1bk10gca1k?t=628\""));
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

    public static void connect(String tvName) throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability("deviceName", tvName);
        dc.setCapability("noReset", "true");
        dc.setCapability("platformName", "Android");
        dc.setCapability("platformVersion", "9");
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver<AndroidElement>(url, dc);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        System.out.println("Application started....");
    }
}
