package mobile.AppiumFramework;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import mobile.PageObjects.CheckOutPage;
import mobile.PageObjects.FormPage;

public class ShopingCartApp extends BaseSetup {

	@Test
	public void totalCostValidation() throws InterruptedException, IOException {
		
		service = startServer();
		AndroidDriver<AndroidElement> driver = Capabalities("GeneralStoreApp");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		FormPage fpage= new FormPage(driver);
		
		Utilities uts = new Utilities(driver);
		
	//	driver.findElement(MobileBy.AndroidUIAutomator(
			//	"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\""
				//		+ "Australia" + "\").instance(0))"));
		
		uts.scrollToText("Australia");
		
		driver.findElement(By.xpath("//*[@text='Australia']")).click();
		
		// Option1: Use the page object variable
		fpage.nameField.sendKeys("Kat");
		
		//Option2: Create a method in PageObject class say as getNameFiled and call here
		fpage.getNameField().sendKeys("Kat");
		
		//driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Kat");
		// This is used to hide the keyboard
		driver.hideKeyboard();
		
		// driver.findElement(By.xpath("//*[@text='Female']")).click();
		
		fpage.genderOption.click();
		
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		Thread.sleep(1000);
		System.out.println("First item added to cart ::::");
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		System.out.println("Second item added to cart ::::");
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		System.out.println("Clicked on Add to cart button ::::");
		Thread.sleep(4000);
		
		//Page object for ChekOut Page
		CheckOutPage chkOut= new CheckOutPage(driver);
		
		int count = chkOut.productList.size();
		System.out.println("Total items count :::: " + count);
		double sumOfProducts = 0;
		for (int i = 0; i < count; i++) {
			String amount = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(i)
					.getText();
			Thread.sleep(2000);
			double item1Amount = getAmount(amount);
			System.out.println("Item cost :" + item1Amount);
			sumOfProducts = sumOfProducts + item1Amount;
			System.out.println("Sum of products is :::: " + sumOfProducts);
		}
		Thread.sleep(2000);
		// Total purchase amount
		String totalPrice = chkOut.totalAmount.getText();
		System.out.println("Total purchase amount :::: " + totalPrice);

		double totalPriceOfItems = getAmount(totalPrice);
		Assert.assertEquals(sumOfProducts, totalPriceOfItems, 0);
		System.out.println("Validated that Sum of prices of items is equals to total purchase amount");
		service.stop();
	}

	public static double getAmount(String value) {
		value = value.substring(1);
		double amountValue = Double.parseDouble(value);
		return amountValue;		
	}
	
/*To get rid of Null pointer exception (Exception comes when Appium server is started via command prompt already and you start TCs execution
 in this case service will not have any life and while performing service.stop(), and Null Pointer Exception comes. 
*/	
	
	/*@BeforeTest()
	public void killAllNodes() throws IOException, InterruptedException
	{
		System.out.println("Killing all the nodes ::::");
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(3000);
	}*/
	
}
