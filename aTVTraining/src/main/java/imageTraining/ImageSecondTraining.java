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

public class ImageSecondTraining {
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
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        TimeUnit.SECONDS.sleep(10);
        redScreen();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            redScreen();
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
        for (int i = 1; i < 12; i++) {
            dpad(AndroidKey.DPAD_DOWN);
        }
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("100")) {
            writeToCSVObj("RED_HUE_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("90")) {
            writeToCSVObj("RED_HUE_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("80")) {
            writeToCSVObj("RED_HUE_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("70")) {
            writeToCSVObj("RED_HUE_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("60")) {
            writeToCSVObj("RED_HUE_60");
        }
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("47")) {
            writeToCSVObj("RED_HUE_50");
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("40")) {
            writeToCSVObj("RED_HUE_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("30")) {
            writeToCSVObj("RED_HUE_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("20")) {
            writeToCSVObj("RED_HUE_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("10")) {
            writeToCSVObj("RED_HUE_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("0")) {
            writeToCSVObj("RED_HUE_0");
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("100")) {
            writeToCSVObj("RED_SATURATION_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("90")) {
            writeToCSVObj("RED_SATURATION_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("80")) {
            writeToCSVObj("RED_SATURATION_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("70")) {
            writeToCSVObj("RED_SATURATION_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("60")) {
            writeToCSVObj("RED_SATURATION_60");
        }
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("47")) {
            writeToCSVObj("RED_SATURATION_50");
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("40")) {
            writeToCSVObj("RED_SATURATION_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("30")) {
            writeToCSVObj("RED_SATURATION_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("20")) {
            writeToCSVObj("RED_SATURATION_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("10")) {
            writeToCSVObj("RED_SATURATION_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("0")) {
            writeToCSVObj("RED_SATURATION_0");
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("100")) {
            writeToCSVObj("RED_BRIGHTNESS_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("90")) {
            writeToCSVObj("RED_BRIGHTNESS_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("80")) {
            writeToCSVObj("RED_BRIGHTNESS_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("70")) {
            writeToCSVObj("RED_BRIGHTNESS_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("60")) {
            writeToCSVObj("RED_BRIGHTNESS_60");
        }
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("47")) {
            writeToCSVObj("RED_BRIGHTNESS_50");
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("40")) {
            writeToCSVObj("RED_BRIGHTNESS_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("30")) {
            writeToCSVObj("RED_BRIGHTNESS_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("20")) {
            writeToCSVObj("RED_BRIGHTNESS_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("10")) {
            writeToCSVObj("RED_BRIGHTNESS_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("0")) {
            writeToCSVObj("RED_BRIGHTNESS_0");
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("100")) {
            writeToCSVObj("RED_OFFSET_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("90")) {
            writeToCSVObj("RED_OFFSET_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("80")) {
            writeToCSVObj("RED_OFFSET_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("70")) {
            writeToCSVObj("RED_OFFSET_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("60")) {
            writeToCSVObj("RED_OFFSET_60");
        }
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("47")) {
            writeToCSVObj("RED_OFFSET_50");
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("40")) {
            writeToCSVObj("RED_OFFSET_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("30")) {
            writeToCSVObj("RED_OFFSET_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("20")) {
            writeToCSVObj("RED_OFFSET_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("10")) {
            writeToCSVObj("RED_OFFSET_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("0")) {
            writeToCSVObj("RED_OFFSET_0");
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("100")) {
            writeToCSVObj("RED_GAIN_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("90")) {
            writeToCSVObj("RED_GAIN_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("80")) {
            writeToCSVObj("RED_GAIN_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("70")) {
            writeToCSVObj("RED_GAIN_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("60")) {
            writeToCSVObj("RED_GAIN_60");
        }
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("47")) {
            writeToCSVObj("RED_GAIN_50");
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("40")) {
            writeToCSVObj("RED_GAIN_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("30")) {
            writeToCSVObj("RED_GAIN_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("20")) {
            writeToCSVObj("RED_GAIN_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("10")) {
            writeToCSVObj("RED_GAIN_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("0")) {
            writeToCSVObj("RED_GAIN_0");
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        TimeUnit.SECONDS.sleep(10);
        greenScreen();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            greenScreen();
            TimeUnit.SECONDS.sleep(5);
        }
        startImageSettings();
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_DOWN);
                dpad(AndroidKey.DPAD_DOWN);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
                element.click();
            } else if (element != null) {
                element.click();
                continue;
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
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_DOWN);
        }
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("100")) {
            writeToCSVObj("GREEN_HUE_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("90")) {
            writeToCSVObj("GREEN_HUE_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("80")) {
            writeToCSVObj("GREEN_HUE_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("70")) {
            writeToCSVObj("GREEN_HUE_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("60")) {
            writeToCSVObj("GREEN_HUE_60");
        }
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("47")) {
            writeToCSVObj("GREEN_HUE_50");
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("40")) {
            writeToCSVObj("GREEN_HUE_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("30")) {
            writeToCSVObj("GREEN_HUE_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("20")) {
            writeToCSVObj("GREEN_HUE_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("10")) {
            writeToCSVObj("GREEN_HUE_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("0")) {
            writeToCSVObj("GREEN_HUE_0");
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("100")) {
            writeToCSVObj("GREEN_SATURATION_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("90")) {
            writeToCSVObj("GREEN_SATURATION_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("80")) {
            writeToCSVObj("GREEN_SATURATION_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("70")) {
            writeToCSVObj("GREEN_SATURATION_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("60")) {
            writeToCSVObj("GREEN_SATURATION_60");
        }
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("47")) {
            writeToCSVObj("GREEN_SATURATION_50");
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("40")) {
            writeToCSVObj("GREEN_SATURATION_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("30")) {
            writeToCSVObj("GREEN_SATURATION_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("20")) {
            writeToCSVObj("GREEN_SATURATION_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("10")) {
            writeToCSVObj("GREEN_SATURATION_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("0")) {
            writeToCSVObj("GREEN_SATURATION_0");
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("100")) {
            writeToCSVObj("GREEN_BRIGHTNESS_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("90")) {
            writeToCSVObj("GREEN_BRIGHTNESS_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("80")) {
            writeToCSVObj("GREEN_BRIGHTNESS_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("70")) {
            writeToCSVObj("GREEN_BRIGHTNESS_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("60")) {
            writeToCSVObj("GREEN_BRIGHTNESS_60");
        }
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("47")) {
            writeToCSVObj("GREEN_BRIGHTNESS_50");
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("40")) {
            writeToCSVObj("GREEN_BRIGHTNESS_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("30")) {
            writeToCSVObj("GREEN_BRIGHTNESS_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("20")) {
            writeToCSVObj("GREEN_BRIGHTNESS_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("10")) {
            writeToCSVObj("GREEN_BRIGHTNESS_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("0")) {
            writeToCSVObj("GREEN_BRIGHTNESS_0");
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("100")) {
            writeToCSVObj("GREEN_OFFSET_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("90")) {
            writeToCSVObj("GREEN_OFFSET_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("80")) {
            writeToCSVObj("GREEN_OFFSET_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("70")) {
            writeToCSVObj("GREEN_OFFSET_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("60")) {
            writeToCSVObj("GREEN_OFFSET_60");
        }
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("47")) {
            writeToCSVObj("GREEN_OFFSET_50");
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("40")) {
            writeToCSVObj("GREEN_OFFSET_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("30")) {
            writeToCSVObj("GREEN_OFFSET_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("20")) {
            writeToCSVObj("GREEN_OFFSET_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("10")) {
            writeToCSVObj("GREEN_OFFSET_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("0")) {
            writeToCSVObj("GREEN_OFFSET_0");
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(1).getText().equals("100")) {
            writeToCSVObj("GREEN_GAIN_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("90")) {
            writeToCSVObj("GREEN_GAIN_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("80")) {
            writeToCSVObj("GREEN_GAIN_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("70")) {
            writeToCSVObj("GREEN_GAIN_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("60")) {
            writeToCSVObj("GREEN_GAIN_60");
        }
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("47")) {
            writeToCSVObj("GREEN_GAIN_50");
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("40")) {
            writeToCSVObj("GREEN_GAIN_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("30")) {
            writeToCSVObj("GREEN_GAIN_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("20")) {
            writeToCSVObj("GREEN_GAIN_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("10")) {
            writeToCSVObj("GREEN_GAIN_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("0")) {
            writeToCSVObj("GREEN_GAIN_0");
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_LEFT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
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
            FileWriter csvWriter = new FileWriter(PathTillProject + "/src/main/ImageSecondEthalon.csv", true);
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

    private static void greenScreen() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/5MVnJkkM-9A?t=73\""));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            dpad(AndroidKey.DPAD_DOWN);
        }
    }

    private static void redScreen() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/e9z2K8d7XTs?t=492\""));
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
