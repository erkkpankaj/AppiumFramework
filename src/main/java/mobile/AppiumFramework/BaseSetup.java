package mobile.AppiumFramework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class BaseSetup {

	public static AppiumDriverLocalService service;
	static AndroidDriver<AndroidElement> driver;

	// Starting the Appium server via Automation, we need to add couple of dependencies in POM.xml to support it
	public AppiumDriverLocalService startServer() {
		boolean flag = checkIfServerIsRunnning(4723);
		if (!flag) {
			service = AppiumDriverLocalService.buildDefaultService();
			service.start();
		}
		return service;
	}

	// Check if Appium server is already running
	public static boolean checkIfServerIsRunnning(int port) {
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

	
	// Launch the Emulator programmatically, and comes in picture when you want to test with Emulators
	public static void startEmulator() throws IOException
	{
		Runtime.getRuntime().exec(System.getProperty("user.dir")+ "\\src\\main\\java\\resources\\startEmulator.bat");
	}
	
	public static AndroidDriver<AndroidElement> Capabalities(String apkName) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\mobile\\AppiumFramework\\global.properties");
		Properties prop = new Properties();
		prop.load(fis);
		File appDir = new File("scr");
		File app = new File(appDir, prop.getProperty("appName"));
		DesiredCapabilities cap = new DesiredCapabilities();
		String device = prop.getProperty("deviceName");
		
		//Call this method if Emulator contains the AVD
		if(device.contains("AVD"))
		{
			startEmulator();
		}
		
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, device);
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		cap.setCapability(MobileCapabilityType.APP, "C:\\IVL_Selenium@2019\\MobileAutomation\\General-Store.apk");
		AndroidDriver<AndroidElement> driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		return driver;
	}
	
	
}
