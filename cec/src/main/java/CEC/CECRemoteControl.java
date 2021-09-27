package CEC;

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

public class CECRemoteControl {
    private static final String COMPORT = "/dev/ttyACM0"; //"COM4";
    private static String TVName;
    private static SerialPort chosenPort;
    private static BufferedReader input;
    static AndroidDriver<AndroidElement> driver;
    static Map< String, Object > driver_args = new HashMap< >();
    static List <AndroidElement> elements;

    public static void main(String[] args) throws InterruptedException {
        TVName = args[0];
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
        report.add("CEC:");
        testCEC(driver, report);
        // Write report
        String reportStr = String.join("\n", report);
        try {
            FileWriter myWriter = new FileWriter("report5.txt");
            myWriter.write(reportStr);
            myWriter.close();
            System.out.println("Successfully wrote to the file!");
        } catch (IOException e) {
            System.out.println("An error occurred:");
            e.printStackTrace();
        }
    }

    private static void testCEC(AndroidDriver<AndroidElement> driver, List<String> report) throws InterruptedException {
        // Device Preferences -> Inputs -> HDMI Control
        startInputsPanel();
        Thread.sleep(2000);
        AndroidElement element = findElementByXPathWrapper("//android.widget.TextView[@text='HDMI 3']");
        element.click();
        Thread.sleep(5000);
        startSettings();
        Thread.sleep(1000);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Настройки устройства']");
        element.click();
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Входы']");
        element.click();
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_DOWN);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_UP);
        dpad(AndroidKey.DPAD_CENTER);
        element = findElementByXPathWrapper("//android.widget.TextView[@text='Отмена']");
        if (element != null) {
            dpad(AndroidKey.DPAD_UP);  // for TV with massage
            dpad(AndroidKey.DPAD_CENTER); // for TV with massage
        }
        Thread.sleep(1000);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        dpad(AndroidKey.BACK);
        Thread.sleep(2000);
        jserialInput(2);
        Thread.sleep(2000);
        jserialInput(2);
        Thread.sleep(2000);
        jserialInput(2);
        Thread.sleep(2000);
        jserialInput(2);
        Thread.sleep(2000);
        jserialInput(2);
        Thread.sleep(2000);
        jserialInput(2);
        Thread.sleep(2000);
        jserialInput(2);
        Thread.sleep(2000);
        jserialInput(2);
        Thread.sleep(2000);
        jserialInput(3);
        Thread.sleep(2000);
        jserialInput(4);
        Thread.sleep(2000);
        jserialInput(4);
        Thread.sleep(2000);
        jserialInput(4);
        Thread.sleep(2000);
        jserialInput(4);
        Thread.sleep(2000);
        jserialInput(4);
        Thread.sleep(2000);
        jserialInput(4);
        Thread.sleep(2000);
        jserialInput(4);
        Thread.sleep(2000);
        jserialInput(4);
        Thread.sleep(2000);
        jserialInput(4);
        Thread.sleep(2000);
        jserialInput(6);
        Thread.sleep(2000);
        jserialInput(6);
        Thread.sleep(2000);
        jserialInput(6);
        Thread.sleep(2000);
        jserialInput(10);
        Thread.sleep(2000);
        JSONObject obj = jserialOutputObj();
        Thread.sleep(2000);
        double lumR = obj.getDouble("lumR");
        double lumG = obj.getDouble("lumG");
        double lumB = obj.getDouble("lumB");
        double lumW = obj.getDouble("lumW");
        boolean passFlag = false;
        if (lumR >= 5 && lumR <= 7 && lumG >= 6 && lumG <= 10 && lumB >= 28 && lumB <= 33 && lumW >= 15 && lumW <= 25){
            passFlag = true;
        }
        if (passFlag) {
            System.out.println("CEC_CONTROL: PASSED");
            report.add("HDMI_CEC_CONTROL: PASSED");
        } else {
            System.out.println("CEC_CONTROL: FAILED");
            report.add("HDMI_CEC_CONTROL: FAILED");
        }


        // Device Preferences -> Inputs -> Device auto power off
        driver.pressKey(new KeyEvent(AndroidKey.TV_POWER));
        Thread.sleep(30_000); // wait for 30 sec
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
    private static JSONObject jserialOutputObj(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        String genuino = null;
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
        JSONObject obj = new JSONObject(genuino);
        return obj;
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

    private static void startInputsPanel() {
        driver_args.put("command", "am start -n ");
        driver_args.put("args", Lists.newArrayList("com.google.android.tvlauncher/com.google.android.tvlauncher.inputs.InputsPanelActivity"));
        try {
            driver.executeScript("mobile:shell", driver_args);
        } catch (NullPointerException e) {
            System.out.println("(!) Something went wrong with the script...");
            System.exit(1);
            driver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
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
        driver = new AndroidDriver < AndroidElement > (url, dc);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        System.out.println("Application started....");
    }
}
