package com.ixigo.Util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.ixigo.BaseClass.BaseClass;

public class IXIGO_Util extends BaseClass {

	static JavascriptExecutor je = (JavascriptExecutor) driver;

	public static void JavaScriptClick(WebElement e) {
		je.executeScript("arguments[0].click();", e);
		// Reporter.log("Clicked on element "+e);
	}

	public static void Explicitwait(int timeout, String xpathkey) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathkey)));
	}

	public static void JavaScriptEnterValue(String value, WebElement e) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value='" + value + "';", e);
		Reporter.log("Value entered in element " + e);
	}

	public static void checkPageIsReady() {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			System.out.println("Page Is loaded.");
			return;
		}

		for (int i = 0; i < 25; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}

	public static void ScrollDownComplete() {
		Reporter.log("Scrolling down to load complete Page content..");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("Scroll down completly to load all elements...");

		for (int i = 0; i < 10; i++) {

			je.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		}
	}

	public static String takeScreenShot() throws Exception {
		File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String Path = System.getProperty("user.dir") + "/ScreenShot/" + System.currentTimeMillis() + ".png";

		FileUtils.copyFile(screenshotAs, new File(Path));

		return Path;
	}

}
