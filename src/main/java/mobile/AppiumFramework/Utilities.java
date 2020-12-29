package mobile.AppiumFramework;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Utilities {

	AndroidDriver<AndroidElement> driver;

	public Utilities(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
	}

	public void scrollToText(String conuntry) {
		driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\""
						+ conuntry + "\").instance(0))"));
	}
}
