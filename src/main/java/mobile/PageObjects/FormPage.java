package mobile.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FormPage {

	public FormPage(AndroidDriver<AndroidElement> driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	public WebElement nameField;
	
	@AndroidFindBy(xpath = "//*[@text='Female']")
	public WebElement genderOption;
	
	
	public WebElement getNameField()
	{
		System.out.println("Trying to get nameField");
		return nameField;
	}

}
