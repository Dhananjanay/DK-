package com.ixigo.BaseClass;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.ixigo.Util.ExtentReportGenerator;

public class BaseClass {

	private final static String FilePath = System.getProperty("user.dir")
			+ "/src/main/java/com/ixigo/properties/config.Properties";

	static FileInputStream fs = null;

	public static Properties Config = null;

	String BrowserName;

	public static WebDriver driver;

	public static ExtentReports extent = ExtentReportGenerator.createInstance();
	public static ThreadLocal<ExtentTest> test1 = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public static void fileSetup() {
		try {
			fs = new FileInputStream(new File(FilePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Config = new Properties();
		try {
			Config.load(fs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@BeforeTest
	public void Setup() throws Exception {

		// fs = new FileInputStream(new File(FilePath));
		// Config = new Properties();
		// Config.load(fs);
		fileSetup();

		BrowserName = Config.getProperty("BrowserName");

		if (BrowserName.equalsIgnoreCase("Chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			/*
			 * options.addArguments("start-maximized");
			 * options.addArguments("test-type"); options.addArguments(
			 * "enable-strict-powerful-feature-restrictions");
			 * options.addArguments("disable-geolocation");
			 */

			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver.exe");
			driver = new ChromeDriver(options);

			Reporter.log(BrowserName + " Opened");
			driver.manage().window().maximize();
		} else if (BrowserName.equalsIgnoreCase("Firefox")) {

			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/geckodrive.exe");

			driver = new FirefoxDriver();
			Reporter.log(BrowserName + " Opened");
			// log.info(BrowserName+" Opened");
			driver.manage().window().maximize();
			Reporter.log(BrowserName + " Maximized");

		} else if (BrowserName.equalsIgnoreCase("IE")) {

			driver = new InternetExplorerDriver();
			Reporter.log(BrowserName + " Opened");

		} else {
			Reporter.log(BrowserName + " is invalid");

			throw new Exception("Invalid Browser Name");
		}

		driver.get(Config.getProperty("Testing_URL"));
		Reporter.log(Config.getProperty("Testing_URL") + " Opened");
		Robot robot = new Robot();
		robot.delay(5000);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_ENTER);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("ImplicitWait")),
				TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

	}

	@AfterTest
	public void TearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
