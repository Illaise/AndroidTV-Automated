package Image;

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

public class ImageFirst {
    //private static final String COMPORT = "/dev/ttyACM0"; //"COM4"; Linux
      private static final String COMPORT = "COM4"; //Windows
    private static SerialPort chosenPort;
    private static BufferedReader input;
    static AndroidDriver<AndroidElement> driver;
    static Map<String, Object> driver_args = new HashMap<>();
    static List<AndroidElement> elements;
    private static List<String[]> imageEthalonVal;

    public static void runTests(String tvName) throws InterruptedException {
        //Initialize serial port
        chosenPort = SerialPort.getCommPort(COMPORT);
        chosenPort.openPort();
        chosenPort.setBaudRate(9600);
        chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        //Initialize excel file
        imageEthalonVal = getCVSFileContents("../image-config/imageFirstEthalonVal.csv"); //Sheet name need to change for each panel
        //Declare obj
        AndroidElement element;
        //Connect to the TV
        try {
            connect(tvName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Report
        List<String> report = new ArrayList<>();
        report.add("Image[1]:");
        // Start script
        dpad(AndroidKey.DPAD_DOWN);
        jserialInput(10);
        TimeUnit.SECONDS.sleep(2);
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
        startSettings();
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
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_RIGHT);
        dpad(AndroidKey.DPAD_CENTER);
        TimeUnit.SECONDS.sleep(4);
        for (int i = 1; i < 5; i++) {
            dpad(AndroidKey.DPAD_UP);
        }
        TimeUnit.SECONDS.sleep(2);
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
        LegendsFall("USER_PRESET:PASSED", "USER_PRESET:FAILED", 0, report);
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
        LegendsFall("STANDARD_PRESET:PASSED", "STANDARD_PRESET:FAILED", 1, report);
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
        LegendsFall("VIVID_PRESET: PASSED", "VIVID_PRESET: FAILED", 2, report);
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
        LegendsFall("SPORT_PRESET: PASSED", "SPORT_PRESET: FAILED", 3, report);
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
        LegendsFall("MOVIE_PRESET: PASSED", "MOVIE_PRESET: FAILED", 4, report);
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
        LegendsFall("GAME_PRESET: PASSED", "GAME_PRESET: FAILED", 5, report);
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
        LegendsFall("ENERGY_SAVE_PRESET: PASSED", "ENERGY_SAVE_PRESET: FAILED", 6, report);
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
            LegendsFall("CONTRAST_100: PASSED", "CONTRAST_100: FAILED", 7, report);
        }
        for (int i = 1; i < 10; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        if (elements.get(2).getText().equals("91")) {
            LegendsFall("CONTRAST_90: PASSED", "CONTRAST_90: FAILED", 8, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 12; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("80")) {
            LegendsFall("CONTRAST_80: PASSED", "CONTRAST_80: FAILED", 9, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("70")) {
            LegendsFall("CONTRAST_70: PASSED", "CONTRAST_70: FAILED", 10, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("60")) {
            LegendsFall("CONTRAST_60: PASSED", "CONTRAST_60: FAILED", 11, report);
        }
        for (int i = 1; i < 14; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("47")) {
            LegendsFall("CONTRAST_50: PASSED", "CONTRAST_50: FAILED", 12, report);
        }
        for (int i = 1; i < 8; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("40")) {
            LegendsFall("CONTRAST_40: PASSED", "CONTRAST_40: FAILED", 13, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("30")) {
            LegendsFall("CONTRAST_30: PASSED", "CONTRAST_30: FAILED", 14, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("20")) {
            LegendsFall("CONTRAST_20: PASSED", "CONTRAST_20: FAILED", 15, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);

        if (elements.get(2).getText().equals("10")) {
            LegendsFall("CONTRAST_10: PASSED", "CONTRAST_10: FAILED", 16, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);

        if (elements.get(2).getText().equals("0")) {
            LegendsFall("CONTRAST_0: PASSED", "CONTRAST_0: FAILED", 17, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        TimeUnit.SECONDS.sleep(2);
        greyFiveNine();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            greyFiveNine();
            TimeUnit.SECONDS.sleep(5);
        }
        startSettings();
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_UP);
                dpad(AndroidKey.DPAD_UP);
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
                dpad(AndroidKey.DPAD_UP);
                dpad(AndroidKey.DPAD_UP);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Изображение']");
                element.click();
            }
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(0).getText().equals("100")) {
            LegendsFall("BACKLIGHT_100: PASSED", "BACKLIGHT_100: FAILED", 18, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("90")) {
            LegendsFall("BACKLIGHT_90: PASSED", "BACKLIGHT_90: FAILED", 19, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("80")) {
            LegendsFall("BACKLIGHT_80: PASSED", "BACKLIGHT_80: FAILED", 20, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("70")) {
            LegendsFall("BACKLIGHT_70: PASSED", "BACKLIGHT_70: FAILED", 21, report);
            System.out.println("lox");
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("60")) {
            LegendsFall("BACKLIGHT_60: PASSED", "BACKLIGHT_60: FAILED", 22, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("50")) {
            LegendsFall("BACKLIGHT_50: PASSED", "BACKLIGHT_50: FAILED", 23, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("40")) {
            LegendsFall("BACKLIGHT_40: PASSED", "BACKLIGHT_40: FAILED", 24, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("30")) {
            LegendsFall("BACKLIGHT_30: PASSED", "BACKLIGHT_30: FAILED", 25, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("20")) {
            LegendsFall("BACKLIGHT_20: PASSED", "BACKLIGHT_20: FAILED", 26, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("10")) {
            LegendsFall("BACKLIGHT_10: PASSED", "BACKLIGHT_10: FAILED", 27, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(5);
        if (elements.get(0).getText().equals("0")) {
            LegendsFall("BACKLIGHT_0: PASSED", "BACKLIGHT_0: FAILED", 28, report);
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
            LegendsFall("BRIGHTNESS_100: PASSED", "BRIGHTNESS_100: FAILED", 29, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("90")) {
            LegendsFall("BRIGHTNESS_90: PASSED", "BRIGHTNESS_90: FAILED", 30, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("80")) {
            LegendsFall("BRIGHTNESS_80: PASSED", "BRIGHTNESS_80: FAILED", 31, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("70")) {
            LegendsFall("BRIGHTNESS_70: PASSED", "BRIGHTNESS_70: FAILED", 32, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("60")) {
            LegendsFall("BRIGHTNESS_60: PASSED", "BRIGHTNESS_60: FAILED", 33, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("50")) {
            LegendsFall("BRIGHTNESS_50: PASSED", "BRIGHTNESS_50: FAILED", 34, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("40")) {
            LegendsFall("BRIGHTNESS_40: PASSED", "BRIGHTNESS_40: FAILED", 35, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("30")) {
            LegendsFall("BRIGHTNESS_30: PASSED", "BRIGHTNESS_30: FAILED", 36, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("20")) {
            LegendsFall("BRIGHTNESS_20: PASSED", "BRIGHTNESS_20: FAILED", 37, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("10")) {
            LegendsFall("BRIGHTNESS_10: PASSED", "BRIGHTNESS_10: FAILED", 38, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("0")) {
            LegendsFall("BRIGHTNESS_0: PASSED", "BRIGHTNESS_0: FAILED", 39, report);
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
        brownScreenTwo();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            brownScreenTwo();
            TimeUnit.SECONDS.sleep(5);
        }
        startSettings();
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_UP);
                dpad(AndroidKey.DPAD_UP);
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
                dpad(AndroidKey.DPAD_UP);
                dpad(AndroidKey.DPAD_UP);
                element = findElementByXPathWrapper("//android.widget.TextView[@text='Изображение']");
                element.click();
            }
        }
        for (int i = 1; i < 5; i++) {
            dpad(AndroidKey.DPAD_DOWN);
        }
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("100")) {
            LegendsFall("SATURATION_100: PASSED", "SATURATION_100: FAILED", 40, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("90")) {
            LegendsFall("SATURATION_90: PASSED", "SATURATION_90: FAILED", 41, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("80")) {
            LegendsFall("SATURATION_80: PASSED", "SATURATION_80: FAILED", 42, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("70")) {
            LegendsFall("SATURATION_70: PASSED", "SATURATION_70: FAILED", 43, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("60")) {
            LegendsFall("SATURATION_60: PASSED", "SATURATION_60: FAILED", 44, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("50")) {
            LegendsFall("SATURATION_50: PASSED", "SATURATION_50: FAILED", 45, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("40")) {
            LegendsFall("SATURATION_40: PASSED", "SATURATION_40: FAILED", 46, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("30")) {
            LegendsFall("SATURATION_30: PASSED", "SATURATION_30: FAILED", 47, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("20")) {
            LegendsFall("SATURATION_20: PASSED", "SATURATION_20: FAILED", 48, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("10")) {
            LegendsFall("SATURATION_10: PASSED", "SATURATION_10: FAILED", 49, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("0")) {
            LegendsFall("SATURATION_0: PASSED", "SATURATION_0: FAILED", 50, report);
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
            LegendsFall("HUE_50: PASSED", "HUE_50: FAILED", 51, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("40")) {
            LegendsFall("HUE_40: PASSED", "HUE_40: FAILED", 52, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("30")) {
            LegendsFall("HUE_30: PASSED", "HUE_30: FAILED", 53, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("20")) {
            LegendsFall("HUE_20: PASSED", "HUE_20: FAILED", 54, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("10")) {
            LegendsFall("HUE_10: PASSED", "HUE_10: FAILED", 55, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("0")) {
            LegendsFall("HUE_0: PASSED", "HUE_0: FAILED", 56, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(5);
        if (elements.get(4).getText().equals("-10")) {
            LegendsFall("HUE_-10: PASSED", "HUE_-10: FAILED", 57, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("-20")) {
            LegendsFall("HUE_-20: PASSED", "HUE_-20: FAILED", 58, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("-30")) {
            LegendsFall("HUE_-30: PASSED", "HUE_-30: FAILED", 59, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("-40")) {
            LegendsFall("HUE_-40: PASSED", "HUE_-40: FAILED", 60, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("-50")) {
            LegendsFall("HUE_-50: PASSED", "HUE_-50: FAILED", 61, report);
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
        LegendsFall("GAMMA_STANDARD: PASSED", "GAMMA_STANDARD: FAILED", 62, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_CENTER);
        LegendsFall("GAMMA_DARK: PASSED", "GAMMA_DARK: FAILED", 63, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        LegendsFall("GAMMA_BRIGHT: PASSED", "GAMMA_BRIGHT: FAILED", 64, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        LegendsFall("COLOR_TEMPERATURE_COLD: PASSED", "COLOR_TEMPERATURE_COLD: FAILED", 65, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        LegendsFall("COLOR_TEMPERATURE_STANDARD: PASSED", "COLOR_TEMPERATURE_STANDARD: FAILED", 66, report);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        LegendsFall("COLOR_TEMPERATURE_WARM: PASSED", "COLOR_TEMPERATURE_WARM: FAILED", 67, report);
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
            LegendsFall("RED_GAIN_100: PASSED", "RED_GAIN_100: FAILED", 68, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(5);
        if (elements.get(0).getText().equals("80")) {
            LegendsFall("RED_GAIN_80: PASSED", "RED_GAIN_80: FAILED", 69, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("60")) {
            LegendsFall("RED_GAIN_60: PASSED", "RED_GAIN_60: FAILED", 70, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("40")) {
            LegendsFall("RED_GAIN_40: PASSED", "RED_GAIN_40: FAILED", 71, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("20")) {
            LegendsFall("RED_GAIN_20: PASSED", "RED_GAIN_20: FAILED", 72, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("0")) {
            LegendsFall("RED_GAIN_0: PASSED", "RED_GAIN_0: FAILED", 73, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("-20")) {
            LegendsFall("RED_GAIN_-20: PASSED", "RED_GAIN_-20: FAILED", 74, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("-40")) {
            LegendsFall("RED_GAIN_-40: PASSED", "RED_GAIN_-40: FAILED", 75, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("-60")) {
            LegendsFall("RED_GAIN_-60: PASSED", "RED_GAIN_-60: FAILED", 76, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("-80")) {
            LegendsFall("RED_GAIN_-80: PASSED", "RED_GAIN_-80: FAILED", 77, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(0).getText().equals("-100")) {
            LegendsFall("RED_GAIN_-100: PASSED", "RED_GAIN_-100: FAILED", 78, report);
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
            LegendsFall("GREEN_GAIN_100: PASSED", "GREEN_GAIN_100: FAILED", 79, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("80")) {
            LegendsFall("GREEN_GAIN_80: PASSED", "GREEN_GAIN_80: FAILED", 80, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("60")) {
            LegendsFall("GREEN_GAIN_60: PASSED", "GREEN_GAIN_60: FAILED", 81, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("40")) {
            LegendsFall("GREEN_GAIN_40: PASSED", "GREEN_GAIN_40: FAILED", 82, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(5);
        if (elements.get(1).getText().equals("20")) {
            LegendsFall("GREEN_GAIN_20: PASSED", "GREEN_GAIN_20: FAILED", 83, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("0")) {
            LegendsFall("GREEN_GAIN_0: PASSED", "GREEN_GAIN_0: FAILED", 84, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("-20")) {
            LegendsFall("GREEN_GAIN_-20: PASSED", "GREEN_GAIN_-20: FAILED", 85, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(5);
        if (elements.get(1).getText().equals("-40")) {
            LegendsFall("GREEN_GAIN_-40: PASSED", "GREEN_GAIN_-40: FAILED", 86, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("-60")) {
            LegendsFall("GREEN_GAIN_-60: PASSED", "GREEN_GAIN_-60: FAILED", 87, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("-80")) {
            LegendsFall("GREEN_GAIN_-80: PASSED", "GREEN_GAIN_-80: FAILED", 88, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(1).getText().equals("-100")) {
            LegendsFall("GREEN_GAIN_-100: PASSED", "GREEN_GAIN_-100: FAILED", 89, report);
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 101; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);

        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        TimeUnit.SECONDS.sleep(2);
        blueScreenOne();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            blueScreenOne();
            TimeUnit.SECONDS.sleep(5);
        }
        startSettings();
        for (int i = 0; i < 3; i++) {
            element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
            if (element != null) {
                element.click();
                continue;
            } else if (element == null) {
                TimeUnit.SECONDS.sleep(2);
                dpad(AndroidKey.DPAD_UP);
                dpad(AndroidKey.DPAD_UP);
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
                dpad(AndroidKey.DPAD_UP);
                dpad(AndroidKey.DPAD_UP);
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
            LegendsFall("BLUE_GAIN_100: PASSED", "BLUE_GAIN_100: FAILED", 90, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("80")) {
            LegendsFall("BLUE_GAIN_80: PASSED", "BLUE_GAIN_80: FAILED", 91, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("60")) {
            LegendsFall("BLUE_GAIN_60: PASSED", "BLUE_GAIN_60: FAILED", 92, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("40")) {
            LegendsFall("BLUE_GAIN_40: PASSED", "BLUE_GAIN_40: FAILED", 93, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("20")) {
            LegendsFall("BLUE_GAIN_20: PASSED", "BLUE_GAIN_20: FAILED", 94, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("0")) {
            LegendsFall("BLUE_GAIN_0: PASSED", "BLUE_GAIN_0: FAILED", 95, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("-20")) {
            LegendsFall("BLUE_GAIN_-20: PASSED", "BLUE_GAIN_-20: FAILED", 96, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("-40")) {
            LegendsFall("BLUE_GAIN_-40: PASSED", "BLUE_GAIN_-40: FAILED", 97, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("-60")) {
            LegendsFall("BLUE_GAIN_-60: PASSED", "BLUE_GAIN_-60: FAILED", 98, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("-80")) {
            LegendsFall("BLUE_GAIN_-80: PASSED", "BLUE_GAIN_-80: FAILED", 99, report);
        }
        for (int i = 1; i < 21; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("-100")) {
            LegendsFall("BLUE_GAIN_-100: PASSED", "BLUE_GAIN_-100: FAILED", 100, report);
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

    //obj comparsion
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

    //results
    private static void LegendsFall(String Passed, String Failed, int rowCount, List<String> report) throws InterruptedException {
        boolean passFlag = objScreenVal(rowCount, imageEthalonVal);
        if (passFlag) {
            System.out.println(Passed);
            report.add("(+) " + Passed);
        } else {
            System.out.println(Failed);
            report.add("(-) " + Failed);
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