package Automation.NAGRA;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class Pages {

	AndroidDriver<AndroidElement> driver;

	public Pages(AndroidDriver<AndroidElement> driver) {
		driver=this.driver;
	}

	@AndroidFindBy(uiAutomator="new UiSelector().text(\"UIDAI\")")
	public static AndroidElement name;
	
	@AndroidFindBy(uiAutomator="new UiSelector().description(\"Apps list\")")
	public static AndroidElement Applist;

	
}
