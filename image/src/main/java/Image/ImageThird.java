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

public class ImageThird {
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
        imageEthalonVal = getCVSFileContents("../image-config/imageThirdEthalonVal.csv"); //Sheet name need to change for each panel
        AndroidElement element;
        //Connect to the TV
        try {
            connect(tvName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Report
        List<String> report = new ArrayList<>();
        report.add("Image[3]:");
        // Start script
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        TimeUnit.SECONDS.sleep(10);
        blueScreenFirst();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            blueScreenFirst();
            TimeUnit.SECONDS.sleep(5);
        }
        startImageSettings();
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
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("100")) {
            LegendsFall("BLUE_HUE_100:PASSED", "BLUE_HUE_100:FAILED", 0, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("90")) {
            LegendsFall("BLUE_HUE_90:PASSED", "BLUE_HUE_90:FAILED", 1, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("80")) {
            LegendsFall("BLUE_HUE_80:PASSED", "BLUE_HUE_80:FAILED", 2, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("70")) {
            LegendsFall("BLUE_HUE_70:PASSED", "BLUE_HUE_70:FAILED", 3, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("60")) {
            LegendsFall("BLUE_HUE_60:PASSED", "BLUE_HUE_60:FAILED", 4, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("50")) {
            LegendsFall("BLUE_HUE_50:PASSED", "BLUE_HUE_50:FAILED", 5, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("40")) {
            LegendsFall("BLUE_HUE_40:PASSED", "BLUE_HUE_40:FAILED", 6, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("30")) {
            LegendsFall("BLUE_HUE_30:PASSED", "BLUE_HUE_30:FAILED", 7, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("20")) {
            LegendsFall("BLUE_HUE_20:PASSED", "BLUE_HUE_20:FAILED", 8, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("10")) {
            LegendsFall("BLUE_HUE_10:PASSED", "BLUE_HUE_10:FAILED", 9, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("0")) {
            LegendsFall("BLUE_HUE_0:PASSED", "BLUE_HUE_0:FAILED", 10, report);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("100")) {
            LegendsFall("BLUE_BRIGHTNESS_100:PASSED", "BLUE_BRIGHTNESS_100:FAILED", 11, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("90")) {
            LegendsFall("BLUE_BRIGHTNESS_90:PASSED", "BLUE_BRIGHTNESS_90:FAILED", 12, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("80")) {
            LegendsFall("BLUE_BRIGHTNESS_80:PASSED", "BLUE_BRIGHTNESS_80:FAILED", 13, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("70")) {
            LegendsFall("BLUE_BRIGHTNESS_70:PASSED", "BLUE_BRIGHTNESS_70:FAILED", 14, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("60")) {
            LegendsFall("BLUE_BRIGHTNESS_60:PASSED", "BLUE_BRIGHTNESS_60:FAILED", 15, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("50")) {
            LegendsFall("BLUE_BRIGHTNESS_50:PASSED", "BLUE_BRIGHTNESS_50:FAILED", 16, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("40")) {
            LegendsFall("BLUE_BRIGHTNESS_40:PASSED", "BLUE_BRIGHTNESS_40:FAILED", 17, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("30")) {
            LegendsFall("BLUE_BRIGHTNESS_30:PASSED", "BLUE_BRIGHTNESS_30:FAILED", 18, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("20")) {
            LegendsFall("BLUE_BRIGHTNESS_20:PASSED", "BLUE_BRIGHTNESS_20:FAILED", 19, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);

        if (elements.get(2).getText().equals("10")) {
            LegendsFall("BLUE_BRIGHTNESS_10:PASSED", "BLUE_BRIGHTNESS_10:FAILED", 20, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("0")) {
            LegendsFall("BLUE_BRIGHTNESS_0:PASSED", "BLUE_BRIGHTNESS_0:FAILED", 21, report);
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
        blueScreenSecond();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            blueScreenSecond();
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
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("100")) {
            LegendsFall("BLUE_SATURATION_100:PASSED", "BLUE_SATURATION_100:FAILED", 22, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("90")) {
            LegendsFall("BLUE_SATURATION_90:PASSED", "BLUE_SATURATION_90:FAILED", 23, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("80")) {
            LegendsFall("BLUE_SATURATION_80:PASSED", "BLUE_SATURATION_80:FAILED", 24, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("70")) {
            LegendsFall("BLUE_SATURATION_70:PASSED", "BLUE_SATURATION_70:FAILED", 25, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("60")) {
            LegendsFall("BLUE_SATURATION_60:PASSED", "BLUE_SATURATION_60:FAILED", 26, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("50")) {
            LegendsFall("BLUE_SATURATION_50:PASSED", "BLUE_SATURATION_50:FAILED", 27, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("40")) {
            LegendsFall("BLUE_SATURATION_40:PASSED", "BLUE_SATURATION_40:FAILED", 28, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("30")) {
            LegendsFall("BLUE_SATURATION_30:PASSED", "BLUE_SATURATION_30:FAILED", 29, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("20")) {
            LegendsFall("BLUE_SATURATION_20:PASSED", "BLUE_SATURATION_20:FAILED", 30, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("10")) {
            LegendsFall("BLUE_SATURATION_10:PASSED", "BLUE_SATURATION_10:FAILED", 31, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("0")) {
            LegendsFall("BLUE_SATURATION_0:PASSED", "BLUE_SATURATION_0:FAILED", 32, report);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_CENTER);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("100")) {
            LegendsFall("BLUE_OFFSET_100:PASSED", "BLUE_OFFSET_100:FAILED", 33, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("90")) {
            LegendsFall("BLUE_OFFSET_90:PASSED", "BLUE_OFFSET_90:FAILED", 34, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("80")) {
            LegendsFall("BLUE_OFFSET_80:PASSED", "BLUE_OFFSET_80:FAILED", 35, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("70")) {
            LegendsFall("BLUE_OFFSET_70:PASSED", "BLUE_OFFSET_70:FAILED", 36, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("60")) {
            LegendsFall("BLUE_OFFSET_60:PASSED", "BLUE_OFFSET_60:FAILED", 37, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("50")) {
            LegendsFall("BLUE_OFFSET_50:PASSED", "BLUE_OFFSET_50:FAILED", 38, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("40")) {
            LegendsFall("BLUE_OFFSET_40:PASSED", "BLUE_OFFSET_40:FAILED", 39, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("30")) {
            LegendsFall("BLUE_OFFSET_30:PASSED", "BLUE_OFFSET_30:FAILED", 40, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("20")) {
            LegendsFall("BLUE_OFFSET_20:PASSED", "BLUE_OFFSET_20:FAILED", 41, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("10")) {
            LegendsFall("BLUE_OFFSET_10:PASSED", "BLUE_OFFSET_10:FAILED", 42, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("0")) {
            LegendsFall("BLUE_OFFSET_0:PASSED", "BLUE_OFFSET_0:FAILED", 43, report);
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
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(2).getText().equals("100")) {
            LegendsFall("BLUE_GAIN_100:PASSED", "BLUE_GAIN_100:FAILED", 44, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("90")) {
            LegendsFall("BLUE_GAIN_90:PASSED", "BLUE_GAIN_90:FAILED", 45, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("80")) {
            LegendsFall("BLUE_GAIN_80:PASSED", "BLUE_GAIN_80:FAILED", 46, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("70")) {
            LegendsFall("BLUE_GAIN_70:PASSED", "BLUE_GAIN_70:FAILED", 47, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("60")) {
            LegendsFall("BLUE_GAIN_60:PASSED", "BLUE_GAIN_60:FAILED", 48, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("50")) {
            LegendsFall("BLUE_GAIN_50:PASSED", "BLUE_GAIN_50:FAILED", 49, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("40")) {
            LegendsFall("BLUE_GAIN_40:PASSED", "BLUE_GAIN_40:FAILED", 50, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("30")) {
            LegendsFall("BLUE_GAIN_30:PASSED", "BLUE_GAIN_30:FAILED", 51, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("20")) {
            LegendsFall("BLUE_GAIN_20:PASSED", "BLUE_GAIN_20:FAILED", 52, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("10")) {
            LegendsFall("BLUE_GAIN_10:PASSED", "BLUE_GAIN_10:FAILED", 53, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(2).getText().equals("0")) {
            LegendsFall("BLUE_GAIN_0:PASSED", "BLUE_GAIN_0:FAILED", 54, report);
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
        cyanScreen();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            cyanScreen();
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
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("100")) {
            LegendsFall("CYAN_HUE_100:PASSED", "CYAN_HUE_100:FAILED", 55, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("90")) {
            LegendsFall("CYAN_HUE_90:PASSED", "CYAN_HUE_90:FAILED", 56, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("80")) {
            LegendsFall("CYAN_HUE_80:PASSED", "CYAN_HUE_80:FAILED", 57, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("70")) {
            LegendsFall("CYAN_HUE_70:PASSED", "CYAN_HUE_70:FAILED", 58, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("60")) {
            LegendsFall("CYAN_HUE_60:PASSED", "CYAN_HUE_60:FAILED", 59, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("50")) {
            LegendsFall("CYAN_HUE_50:PASSED", "CYAN_HUE_50:FAILED", 60, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("40")) {
            LegendsFall("CYAN_HUE_40:PASSED", "CYAN_HUE_40:FAILED", 61, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("30")) {
            LegendsFall("CYAN_HUE_30:PASSED", "CYAN_HUE_30:FAILED", 62, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("20")) {
            LegendsFall("CYAN_HUE_20:PASSED", "CYAN_HUE_20:FAILED", 63, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("10")) {
            LegendsFall("CYAN_HUE_10:PASSED", "CYAN_HUE_10:FAILED", 64, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("0")) {
            LegendsFall("CYAN_HUE_0:PASSED", "CYAN_HUE_0:FAILED", 65, report);
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
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("100")) {
            LegendsFall("CYAN_SATURATION_100:PASSED", "CYAN_SATURATION_100:FAILED", 66, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("90")) {
            LegendsFall("CYAN_SATURATION_90:PASSED", "CYAN_SATURATION_90:FAILED", 67, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("80")) {
            LegendsFall("CYAN_SATURATION_80:PASSED", "CYAN_SATURATION_80:FAILED", 68, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("70")) {
            LegendsFall("CYAN_SATURATION_70:PASSED", "CYAN_SATURATION_70:FAILED", 69, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("60")) {
            LegendsFall("CYAN_SATURATION_60:PASSED", "CYAN_SATURATION_60:FAILED", 70, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("50")) {
            LegendsFall("CYAN_SATURATION_50:PASSED", "CYAN_SATURATION_50:FAILED", 71, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("40")) {
            LegendsFall("CYAN_SATURATION_40:PASSED", "CYAN_SATURATION_40:FAILED", 72, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("30")) {
            LegendsFall("CYAN_SATURATION_30:PASSED", "CYAN_SATURATION_30:FAILED", 73, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("20")) {
            LegendsFall("CYAN_SATURATION_20:PASSED", "CYAN_SATURATION_20:FAILED", 74, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("10")) {
            LegendsFall("CYAN_SATURATION_10:PASSED", "CYAN_SATURATION_10:FAILED", 75, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("0")) {
            LegendsFall("CYAN_SATURATION_0:PASSED", "CYAN_SATURATION_0:FAILED", 76, report);
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
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(3).getText().equals("100")) {
            LegendsFall("CYAN_BRIGHTNESS_100:PASSED", "CYAN_BRIGHTNESS_100:FAILED", 77, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("90")) {
            LegendsFall("CYAN_BRIGHTNESS_90:PASSED", "CYAN_BRIGHTNESS_90:FAILED", 78, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("80")) {
            LegendsFall("CYAN_BRIGHTNESS_80:PASSED", "CYAN_BRIGHTNESS_80:FAILED", 79, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("70")) {
            LegendsFall("CYAN_BRIGHTNESS_70:PASSED", "CYAN_BRIGHTNESS_70:FAILED", 80, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("60")) {
            LegendsFall("CYAN_BRIGHTNESS_60:PASSED", "CYAN_BRIGHTNESS_60:FAILED", 81, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("50")) {
            LegendsFall("CYAN_BRIGHTNESS_50:PASSED", "CYAN_BRIGHTNESS_50:FAILED", 82, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("40")) {
            LegendsFall("CYAN_BRIGHTNESS_40:PASSED", "CYAN_BRIGHTNESS_40:FAILED", 83, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("30")) {
            LegendsFall("CYAN_BRIGHTNESS_30:PASSED", "CYAN_BRIGHTNESS_30:FAILED", 84, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("20")) {
            LegendsFall("CYAN_BRIGHTNESS_20:PASSED", "CYAN_BRIGHTNESS_20:FAILED", 85, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("10")) {
            LegendsFall("CYAN_BRIGHTNESS_10:PASSED", "CYAN_BRIGHTNESS_10:FAILED", 86, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(3).getText().equals("0")) {
            LegendsFall("CYAN_BRIGHTNESS_0:PASSED", "CYAN_BRIGHTNESS_0:FAILED", 87, report);
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
        magentaScreen();
        TimeUnit.SECONDS.sleep(5);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Рекомендованные']");
        if (element != null) {
            magentaScreen();
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
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("100")) {
            LegendsFall("MAGENTA_HUE_100:PASSED", "MAGENTA_HUE_100:FAILED", 88, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("90")) {
            LegendsFall("MAGENTA_HUE_90:PASSED", "MAGENTA_HUE_90:FAILED", 89, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("80")) {
            LegendsFall("MAGENTA_HUE_80:PASSED", "MAGENTA_HUE_80:FAILED", 90, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("70")) {
            LegendsFall("MAGENTA_HUE_70:PASSED", "MAGENTA_HUE_70:FAILED", 91, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("60")) {
            LegendsFall("MAGENTA_HUE_60:PASSED", "MAGENTA_HUE_60:FAILED", 92, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("50")) {
            LegendsFall("MAGENTA_HUE_50:PASSED", "MAGENTA_HUE_50:FAILED", 93, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("40")) {
            LegendsFall("MAGENTA_HUE_40:PASSED", "MAGENTA_HUE_40:FAILED", 94, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("30")) {
            LegendsFall("MAGENTA_HUE_30:PASSED", "MAGENTA_HUE_30:FAILED", 95, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("20")) {
            LegendsFall("MAGENTA_HUE_20:PASSED", "MAGENTA_HUE_20:FAILED", 96, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("10")) {
            LegendsFall("MAGENTA_HUE_10:PASSED", "MAGENTA_HUE_10:FAILED", 97, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("0")) {
            LegendsFall("MAGENTA_HUE_0:PASSED", "MAGENTA_HUE_0:FAILED", 98, report);
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
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("100")) {
            LegendsFall("MAGENTA_SATURATION_100:PASSED", "MAGENTA_SATURATION_100:FAILED", 99, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("90")) {
            LegendsFall("MAGENTA_SATURATION_90:PASSED", "MAGENTA_SATURATION_90:FAILED", 100, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("80")) {
            LegendsFall("MAGENTA_SATURATION_80:PASSED", "MAGENTA_SATURATION_80:FAILED", 101, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("70")) {
            LegendsFall("MAGENTA_SATURATION_70:PASSED", "MAGENTA_SATURATION_70:FAILED", 102, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("60")) {
            LegendsFall("MAGENTA_SATURATION_60:PASSED", "MAGENTA_SATURATION_60:FAILED", 103, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("50")) {
            LegendsFall("MAGENTA_SATURATION_50:PASSED", "MAGENTA_SATURATION_50:FAILED", 104, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("40")) {
            LegendsFall("MAGENTA_SATURATION_40:PASSED", "MAGENTA_SATURATION_40:FAILED", 105, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("30")) {
            LegendsFall("MAGENTA_SATURATION_30:PASSED", "MAGENTA_SATURATION_30:FAILED", 106, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("20")) {
            LegendsFall("MAGENTA_SATURATION_20:PASSED", "MAGENTA_SATURATION_20:FAILED", 107, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("10")) {
            LegendsFall("MAGENTA_SATURATION_10:PASSED", "MAGENTA_SATURATION_10:FAILED", 108, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("0")) {
            LegendsFall("MAGENTA_SATURATION_0:PASSED", "MAGENTA_SATURATION_0:FAILED", 109, report);
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
        TimeUnit.SECONDS.sleep(2);
        for (int i = 1; i < 51; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        elements = driver.findElements(By.id("com.mediatek.overlay.tvsettings:id/preference_progress_value"));
        if (elements.get(4).getText().equals("100")) {
            LegendsFall("MAGENTA_BRIGHTNESS_100:PASSED", "MAGENTA_BRIGHTNESS_100:FAILED", 110, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("90")) {
            LegendsFall("MAGENTA_BRIGHTNESS_100:PASSED", "MAGENTA_BRIGHTNESS_100:FAILED", 224, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("80")) {
            LegendsFall("MAGENTA_BRIGHTNESS_80:PASSED", "MAGENTA_BRIGHTNESS_80:FAILED", 226, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("70")) {
            LegendsFall("MAGENTA_BRIGHTNESS_70:PASSED", "MAGENTA_BRIGHTNESS_70:FAILED", 228, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("60")) {
            LegendsFall("MAGENTA_BRIGHTNESS_60:PASSED", "MAGENTA_BRIGHTNESS_60:FAILED", 230, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("50")) {
            LegendsFall("MAGENTA_BRIGHTNESS_50:PASSED", "MAGENTA_BRIGHTNESS_50:FAILED", 232, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("40")) {
            LegendsFall("MAGENTA_BRIGHTNESS_40:PASSED", "MAGENTA_BRIGHTNESS_40:FAILED", 234, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("30")) {
            LegendsFall("MAGENTA_BRIGHTNESS_30:PASSED", "MAGENTA_BRIGHTNESS_30:FAILED", 236, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("20")) {
            LegendsFall("MAGENTA_BRIGHTNESS_20:PASSED", "MAGENTA_BRIGHTNESS_20:FAILED", 238, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("10")) {
            LegendsFall("MAGENTA_BRIGHTNESS_10:PASSED", "MAGENTA_BRIGHTNESS_10:FAILED", 240, report);
        }
        for (int i = 1; i < 11; i++) {
            dpad(AndroidKey.DPAD_LEFT);
        }
        TimeUnit.SECONDS.sleep(2);
        if (elements.get(4).getText().equals("0")) {
            LegendsFall("MAGENTA_BRIGHTNESS_0:PASSED", "MAGENTA_BRIGHTNESS_0:FAILED", 242, report);
        }
        TimeUnit.SECONDS.sleep(2);
        dpad(AndroidKey.DPAD_RIGHT);
        for (int i = 1; i < 50; i++) {
            dpad(AndroidKey.DPAD_RIGHT);
        }
        TimeUnit.SECONDS.sleep(2);
        chosenPort.closePort();
        driver.quit();

        // Write report
        String reportStr = String.join("\n", report);
        try {
            FileWriter myWriter = new FileWriter("report3-3.txt");
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

    private static void blueScreenFirst() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://www.youtube.com/watch?v=rQK9yxY7O5Y&t=1436s\""));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            dpad(AndroidKey.DPAD_DOWN);
        }
    }

    private static void blueScreenSecond() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://www.youtube.com/watch?v=lNJldzx4I6w&t=120s\""));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            dpad(AndroidKey.DPAD_DOWN);
        }
    }

    private static void cyanScreen() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/V4R5QUV3L9g?t=671\""));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            dpad(AndroidKey.DPAD_DOWN);
        }
    }

    private static void magentaScreen() {
        driver_args.put("command", "am start -a ");
        driver_args.put("args", Lists.newArrayList("android.intent.action.VIEW \"https://youtu.be/rqDa5I1x8gQ?t=930\""));
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