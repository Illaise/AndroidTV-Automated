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

public class ImageFourth {
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
        imageEthalonVal = getCVSFileContents("../image-config/imageFourthEthalonVal.csv"); //Sheet name need to change for each panel
        AndroidElement element;
        //Connect to the TV
        try {
            connect(tvName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Report
        List<String> report = new ArrayList<>();
        report.add("Image[4]:");

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
            LegendsFall("YELLOW_HUE_100:PASSED", "YELLOW_HUE_100:FAILED", 2, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("90")) {
            LegendsFall("YELLOW_HUE_90:PASSED", "YELLOW_HUE_90:FAILED", 4, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("80")) {
            LegendsFall("YELLOW_HUE_80:PASSED", "YELLOW_HUE_80:FAILED", 6, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("70")) {
            LegendsFall("YELLOW_HUE_70:PASSED", "YELLOW_HUE_70:FAILED", 8, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("60")) {
            LegendsFall("YELLOW_HUE_60:PASSED", "YELLOW_HUE_60:FAILED", 10, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("50")) {
            LegendsFall("YELLOW_HUE_50:PASSED", "YELLOW_HUE_50:FAILED", 12, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("40")) {
            LegendsFall("YELLOW_HUE_40:PASSED", "YELLOW_HUE_40:FAILED", 14, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("30")) {
            LegendsFall("YELLOW_HUE_30:PASSED", "YELLOW_HUE_30:FAILED", 16, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("20")) {
            LegendsFall("YELLOW_HUE_20:PASSED", "YELLOW_HUE_20:FAILED", 18, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("10")) {
            LegendsFall("YELLOW_HUE_10:PASSED", "YELLOW_HUE_10:FAILED", 20, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("0")) {
            LegendsFall("YELLOW_HUE_0:PASSED", "YELLOW_HUE_0:FAILED", 22, report);
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
            LegendsFall("YELLOW_SATURATION_100:PASSED", "YELLOW_SATURATION_100:FAILED", 24, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("90")) {
            LegendsFall("YELLOW_SATURATION_90:PASSED", "YELLOW_SATURATION_90:FAILED", 26, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("80")) {
            LegendsFall("YELLOW_SATURATION_80:PASSED", "YELLOW_SATURATION_80:FAILED", 28, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("70")) {
            LegendsFall("YELLOW_SATURATION_70:PASSED", "YELLOW_SATURATION_70:FAILED", 30, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("60")) {
            LegendsFall("YELLOW_SATURATION_60:PASSED", "YELLOW_SATURATION_60:FAILED", 32, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("50")) {
            LegendsFall("YELLOW_SATURATION_50:PASSED", "YELLOW_SATURATION_50:FAILED", 34, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("40")) {
            LegendsFall("YELLOW_SATURATION_40:PASSED", "YELLOW_SATURATION_40:FAILED", 36, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("30")) {
            LegendsFall("YELLOW_SATURATION_30:PASSED", "YELLOW_SATURATION_30:FAILED", 38, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("20")) {
            LegendsFall("YELLOW_SATURATION_20:PASSED", "YELLOW_SATURATION_20:FAILED", 40, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("10")) {
            LegendsFall("YELLOW_SATURATION_10:PASSED", "YELLOW_SATURATION_10:FAILED", 42, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("0")) {
            LegendsFall("YELLOW_SATURATION_0:PASSED", "YELLOW_SATURATION_0:FAILED", 44, report);
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
            LegendsFall("YELLOW_BRIGHTNESS_100:PASSED", "YELLOW_BRIGHTNESS_100:FAILED", 46, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("90")) {
            LegendsFall("YELLOW_BRIGHTNESS_90:PASSED", "YELLOW_BRIGHTNESS_90:FAILED", 48, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("80")) {
            LegendsFall("YELLOW_BRIGHTNESS_80:PASSED", "YELLOW_BRIGHTNESS_80:FAILED", 50, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("70")) {
            LegendsFall("YELLOW_BRIGHTNESS_70:PASSED", "YELLOW_BRIGHTNESS_70:FAILED", 52, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("60")) {
            LegendsFall("YELLOW_BRIGHTNESS_60:PASSED", "YELLOW_BRIGHTNESS_60:FAILED", 54, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("50")) {
            LegendsFall("YELLOW_BRIGHTNESS_50:PASSED", "YELLOW_BRIGHTNESS_50:FAILED", 56, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("40")) {
            LegendsFall("YELLOW_BRIGHTNESS_40:PASSED", "YELLOW_BRIGHTNESS_40:FAILED", 58, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("30")) {
            LegendsFall("YELLOW_BRIGHTNESS_30:PASSED", "YELLOW_BRIGHTNESS_30:FAILED", 60, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("20")) {
            LegendsFall("YELLOW_BRIGHTNESS_20:PASSED", "YELLOW_BRIGHTNESS_20:FAILED", 62, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("10")) {
            LegendsFall("YELLOW_BRIGHTNESS_10:PASSED", "YELLOW_BRIGHTNESS_10:FAILED", 64, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(5).getText().equals("0")) {
            LegendsFall("YELLOW_BRIGHTNESS_0:PASSED", "YELLOW_BRIGHTNESS_0:FAILED", 66, report);
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
        TimeUnit.SECONDS.sleep(5);
        brownScreenTwo();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            brownScreenTwo();
            TimeUnit.SECONDS.sleep(5);
        }
        startImageSettings();
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
            LegendsFall("FLESH_HUE_100:PASSED", "FLESH_HUE_100:FAILED", 68, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("90")) {
            LegendsFall("FLESH_HUE_90:PASSED", "FLESH_HUE_90:FAILED", 70, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("80")) {
            LegendsFall("FLESH_HUE_80:PASSED", "FLESH_HUE_80:FAILED", 72, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("70")) {
            LegendsFall("FLESH_HUE_70:PASSED", "FLESH_HUE_70:FAILED", 74, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("60")) {
            LegendsFall("FLESH_HUE_60:PASSED", "FLESH_HUE_60:FAILED", 76, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("50")) {
            LegendsFall("FLESH_HUE_50:PASSED", "FLESH_HUE_50:FAILED", 78, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("40")) {
            LegendsFall("FLESH_HUE_40:PASSED", "FLESH_HUE_40:FAILED", 80, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("30")) {
            LegendsFall("FLESH_HUE_30:PASSED", "FLESH_HUE_30:FAILED", 82, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("20")) {
            LegendsFall("FLESH_HUE_20:PASSED", "FLESH_HUE_20:FAILED", 84, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("10")) {
            LegendsFall("FLESH_HUE_10:PASSED", "FLESH_HUE_10:FAILED", 86, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("0")) {
            LegendsFall("FLESH_HUE_0:PASSED", "FLESH_HUE_0:FAILED", 88, report);
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
            LegendsFall("FLESH_SATURATION_100:PASSED", "FLESH_SATURATION_100:FAILED", 90, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("90")) {
            LegendsFall("FLESH_SATURATION_90:PASSED", "FLESH_SATURATION_90:FAILED", 92, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("80")) {
            LegendsFall("FLESH_SATURATION_80:PASSED", "FLESH_SATURATION_80:FAILED", 94, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("70")) {
            LegendsFall("FLESH_SATURATION_70:PASSED", "FLESH_SATURATION_70:FAILED", 96, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("60")) {
            LegendsFall("FLESH_SATURATION_60:PASSED", "FLESH_SATURATION_60:FAILED", 98, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("50")) {
            LegendsFall("FLESH_SATURATION_50:PASSED", "FLESH_SATURATION_50:FAILED", 100, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("40")) {
            LegendsFall("FLESH_SATURATION_40:PASSED", "FLESH_SATURATION_40:FAILED", 102, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("30")) {
            LegendsFall("FLESH_SATURATION_30:PASSED", "FLESH_SATURATION_30:FAILED", 104, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("20")) {
            LegendsFall("FLESH_SATURATION_20:PASSED", "FLESH_SATURATION_20:FAILED", 106, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("10")) {
            LegendsFall("FLESH_SATURATION_10:PASSED", "FLESH_SATURATION_10:FAILED", 108, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("0")) {
            LegendsFall("FLESH_SATURATION_0:PASSED", "FLESH_SATURATION_0:FAILED", 110, report);
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
            LegendsFall("FLESH_BRIGHTNESS_100:PASSED", "FLESH_BRIGHTNESS_100:FAILED", 112, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("90")) {
            LegendsFall("FLESH_BRIGHTNESS_90:PASSED", "FLESH_BRIGHTNESS_90:FAILED", 114, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("80")) {
            LegendsFall("FLESH_BRIGHTNESS_80:PASSED", "FLESH_BRIGHTNESS_80:FAILED", 116, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("70")) {
            LegendsFall("FLESH_BRIGHTNESS_70:PASSED", "FLESH_BRIGHTNESS_70:FAILED", 118, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("60")) {
            LegendsFall("FLESH_BRIGHTNESS_60:PASSED", "FLESH_BRIGHTNESS_60:FAILED", 120, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("50")) {
            LegendsFall("FLESH_BRIGHTNESS_50:PASSED", "FLESH_BRIGHTNESS_50:FAILED", 122, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("40")) {
            LegendsFall("FLESH_BRIGHTNESS_40:PASSED", "FLESH_BRIGHTNESS_40:FAILED", 124, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("30")) {
            LegendsFall("FLESH_BRIGHTNESS_30:PASSED", "FLESH_BRIGHTNESS_30:FAILED", 126, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("20")) {
            LegendsFall("FLESH_BRIGHTNESS_20:PASSED", "FLESH_BRIGHTNESS_20:FAILED", 128, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("10")) {
            LegendsFall("FLESH_BRIGHTNESS_10:PASSED", "FLESH_BRIGHTNESS_10:FAILED", 130, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(6).getText().equals("0")) {
            LegendsFall("FLESH_BRIGHTNESS_0:PASSED", "FLESH_BRIGHTNESS_0:FAILED", 132, report);
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
            FileWriter myWriter = new FileWriter("report3-4.txt");
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