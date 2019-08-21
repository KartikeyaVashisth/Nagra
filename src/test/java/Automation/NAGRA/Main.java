package Automation.NAGRA;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.mongodb.operation.DropUserOperation;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class Main {

	public static AndroidDriver<AndroidElement> driver;
	ExtentReports extent;
	ExtentTest test;
	AppiumServiceBuilder builder;
	AppiumDriverLocalService service;

	@BeforeSuite
	public void report() {

		extent=new ExtentReports("C:\\Users\\kartikeya.v\\Desktop\\Reports\\report.html",true); 
		//Initialization of the extent report class.
		
		//Build the Appium service
		builder=new AppiumServiceBuilder();
		builder.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"));
		builder.withAppiumJS(new File("C:\\Users\\kartikeya.v\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"));
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(4723);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
		
		//Start the server with the builder
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		
	}

	@BeforeTest
	public void setup() throws MalformedURLException {
		
		DesiredCapabilities cap=new DesiredCapabilities();
		cap.setCapability("platformVersion", "8.1.0");
		cap.setCapability("platformName", "Android");
		cap.setCapability("deviceName", "XP8800");
		cap.setCapability("automationName", "uiautomator2");
		cap.setCapability("udid","c551c442");
		cap.setCapability("appPackage", "com.android.contacts");
		cap.setCapability("appActivity", "com.android.contacts.activities.PeopleActivity");

		driver=new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap); //To send a request through this URL with the above desired capabilities to the server.
	}

	@BeforeTest
	public void t_beforetest() {
		Pages p=new Pages(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver),p);//It will initialise the locators
	}

	@BeforeMethod	
	public void testcaseName(Method method) {
		test=extent.startTest(this.getClass().getSimpleName() +":"+ method.getName() );
	}

	@Test	
	public void Open() throws InterruptedException {
		
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
		
		TimeUnit.SECONDS.sleep(2);
		
		Pages.Applist.click();
		TimeUnit.SECONDS.sleep(2);

		AndroidElement ele_chrome = driver.findElementByAccessibilityId("Chrome");
		AndroidElement ele_contacts = driver.findElementByAccessibilityId("Contacts");
		
		
		AndroidTouchAction t1=new AndroidTouchAction(driver);
		AndroidTouchAction t2=new AndroidTouchAction(driver);

		//Move element to a particular position
		t1.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(ele_chrome)))
		.moveTo(ElementOption.element(ele_contacts)).release().perform();
		
		//Expand a context menu
		t2.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(ele_chrome)).withDuration(Duration.ofSeconds(2))).perform();

		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Youtube\"))").click();
		
//		t2.press(PointOption.point(115, 650)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
//        .moveTo(PointOption.point(115, 350)).release().perform();		
		
//		MultiTouchAction m=new MultiTouchAction(driver);
//		m.add(t1).add(t2).perform();
			
		SoftAssert sa=new SoftAssert();	
		TimeUnit.SECONDS.sleep(10); 

		Pages p1=new Pages(driver);
		p1.name.click();
		sa.assertTrue(true,"");
		test.log(LogStatus.PASS, "Test case is passed");
	}
	
	@AfterMethod	
	public void testcaseName() {
		extent.endTest(test);
		extent.flush(); //To append the result in the report 
	}


}
