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

public class ImageFourthTraining {
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
        TimeUnit.SECONDS.sleep(5);
        yellowScreen();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            yellowScreen();
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
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(5).getText().equals("100")) {
            writeToCSVObj("YELLOW_HUE_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("90")) {
            writeToCSVObj("YELLOW_HUE_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("80")) {
            writeToCSVObj("YELLOW_HUE_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("70")) {
            writeToCSVObj("YELLOW_HUE_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("60")) {
            writeToCSVObj("YELLOW_HUE_60");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("50")) {
            writeToCSVObj("YELLOW_HUE_50");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("40")) {
            writeToCSVObj("YELLOW_HUE_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("30")) {
            writeToCSVObj("YELLOW_HUE_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("20")) {
            writeToCSVObj("YELLOW_HUE_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("10")) {
            writeToCSVObj("YELLOW_HUE_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("0")) {
            writeToCSVObj("YELLOW_HUE_0");
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
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(5).getText().equals("100")) {
            writeToCSVObj("YELLOW_SATURATION_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("90")) {
            writeToCSVObj("YELLOW_SATURATION_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("80")) {
            writeToCSVObj("YELLOW_SATURATION_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("70")) {
            writeToCSVObj("YELLOW_SATURATION_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("60")) {
            writeToCSVObj("YELLOW_SATURATION_60");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("50")) {
            writeToCSVObj("YELLOW_SATURATION_50");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("40")) {
            writeToCSVObj("YELLOW_SATURATION_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("30")) {
            writeToCSVObj("YELLOW_SATURATION_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("20")) {
            writeToCSVObj("YELLOW_SATURATION_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("10")) {
            writeToCSVObj("YELLOW_SATURATION_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("0")) {
            writeToCSVObj("YELLOW_SATURATION_0");
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
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(5).getText().equals("100")) {
            writeToCSVObj("YELLOW_BRIGHTNESS_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("90")) {
            writeToCSVObj("YELLOW_BRIGHTNESS_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("80")) {
            writeToCSVObj("YELLOW_BRIGHTNESS_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("70")) {
            writeToCSVObj("YELLOW_BRIGHTNESS_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("60")) {
            writeToCSVObj("YELLOW_BRIGHTNESS_60");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("50")) {
            writeToCSVObj("YELLOW_BRIGHTNESS_50");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("40")) {
            writeToCSVObj("YELLOW_BRIGHTNESS_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("30")) {
            writeToCSVObj("YELLOW_BRIGHTNESS_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("20")) {
            writeToCSVObj("YELLOW_BRIGHTNESS_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("10")) {
            writeToCSVObj("YELLOW_BRIGHTNESS_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("0")) {
            writeToCSVObj("YELLOW_BRIGHTNESS_0");
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
        dpad(AndroidKey.BACK);
        TimeUnit.SECONDS.sleep(2);
        brownScreenTwo();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            brownScreenTwo();
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
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(6).getText().equals("100")) {
            writeToCSVObj("FLESH_HUE_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("90")) {
            writeToCSVObj("FLESH_HUE_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("80")) {
            writeToCSVObj("FLESH_HUE_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("70")) {
            writeToCSVObj("FLESH_HUE_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("60")) {
            writeToCSVObj("FLESH_HUE_60");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("50")) {
            writeToCSVObj("FLESH_HUE_50");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("40")) {
            writeToCSVObj("FLESH_HUE_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("30")) {
            writeToCSVObj("FLESH_HUE_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("20")) {
            writeToCSVObj("FLESH_HUE_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("10")) {
            writeToCSVObj("FLESH_HUE_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("0")) {
            writeToCSVObj("FLESH_HUE_0");
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
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(6).getText().equals("100")) {
            writeToCSVObj("FLESH_SATURATION_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("90")) {
            writeToCSVObj("FLESH_SATURATION_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("80")) {
            writeToCSVObj("FLESH_SATURATION_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("70")) {
            writeToCSVObj("FLESH_SATURATION_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("60")) {
            writeToCSVObj("FLESH_SATURATION_60");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("50")) {
            writeToCSVObj("FLESH_SATURATION_50");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("40")) {
            writeToCSVObj("FLESH_SATURATION_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("30")) {
            writeToCSVObj("FLESH_SATURATION_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("20")) {
            writeToCSVObj("FLESH_SATURATION_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("10")) {
            writeToCSVObj("FLESH_SATURATION_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("0")) {
            writeToCSVObj("FLESH_SATURATION_0");
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
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(6).getText().equals("100")) {
            writeToCSVObj("FLESH_BRIGHTNESS_100");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("90")) {
            writeToCSVObj("FLESH_BRIGHTNESS_90");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("80")) {
            writeToCSVObj("FLESH_BRIGHTNESS_80");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("70")) {
            writeToCSVObj("FLESH_BRIGHTNESS_70");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("60")) {
            writeToCSVObj("FLESH_BRIGHTNESS_60");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("50")) {
            writeToCSVObj("FLESH_BRIGHTNESS_50");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("40")) {
            writeToCSVObj("FLESH_BRIGHTNESS_40");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("30")) {
            writeToCSVObj("FLESH_BRIGHTNESS_30");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("20")) {
            writeToCSVObj("FLESH_BRIGHTNESS_20");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("10")) {
            writeToCSVObj("FLESH_BRIGHTNESS_10");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("0")) {
            writeToCSVObj("FLESH_BRIGHTNESS_0");
        }
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
                if (obj.has("lumR") && obj.has("lumG") && obj.has("lumB") && obj.has("lumW")) {
                    return obj;
                }
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
            FileWriter csvWriter = new FileWriter(PathTillProject + "/src/main/ImageFourthEthalon.csv", true);
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

    private static void yellowScreen() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/b9LzymfGMk8?t=325\""));
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
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/ce9jTI-b1zo?t=2126\""));
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
