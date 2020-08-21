package com.ixigo.Pages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;

import com.ixigo.BaseClass.BaseClass;
import com.ixigo.Pages.IXIGO_FlightResult;
import com.ixigo.Util.IXIGO_Util;

public class IXIGO_HomePage extends BaseClass {

	@FindBy(xpath = "//div[text()='From']/following-sibling::input")
	private WebElement From_City;

	@FindBy(xpath = "//div[text()='To']/following-sibling::input")
	private WebElement To_City;

	@FindBy(xpath = "//div[text()='From']/../following-sibling::div/div//div[@class='city']")
	private List<WebElement> FromCity_Suggestion = new ArrayList<>();

	@FindBy(xpath = "//div[text()='To']/../following-sibling::div/div//div[@class='city']")
	private List<WebElement> ToCity_Suggestion = new ArrayList<>();

	@FindBy(xpath = "//input[@class='c-input u-v-align-middle' and @placeholder='Depart']")
	private static WebElement Depart_Cal;

	@FindBy(xpath = "//div[@class='rd-date']/div/div")
	private static WebElement Depart_Dateyear;

	@FindBy(xpath = "//*[@class='ixi-icon-arrow rd-next']")
	private static WebElement Arrow_Next;

	@FindBy(xpath = "//*[@class='ixi-icon-arrow u-rotate-180 rd-back']/following-sibling::table/tbody/tr/td/div[@class='day has-info']")
	private static List<WebElement> Departure_date = new ArrayList<>();

	@FindBy(xpath = "//*[@class='rd-container flight-ret-cal extra-bottom rd-container-attachment']//button[@class='ixi-icon-arrow u-rotate-180 rd-back']/following-sibling::div")
	private static WebElement Return_Dateyear;

	@FindBy(xpath = "//*[@class='rd-container flight-ret-cal extra-bottom rd-container-attachment']//button[@class='ixi-icon-arrow rd-next']")
	private static WebElement Next_arrow;

	@FindBy(xpath = "//*[@class='ixi-icon-arrow u-rotate-180 rd-back']/following-sibling::table/tbody/tr/td/div[@class='day has-info' and text()]")
	private static List<WebElement> Return_date = new ArrayList<>();

	@FindBy(xpath = "//input[@class='c-input u-v-align-middle' and @placeholder='Return']")
	private static WebElement Return_Cal;

	@FindBy(xpath = "//div[contains(text(),'Travellers | Class')]/following-sibling::input")
	private WebElement Traveller_field;

	@FindBy(xpath = "//div[@class='main' and text()='Adult']/../following-sibling::div/span")
	private List<WebElement> Traveler_count = new ArrayList<>();;

	@FindBy(xpath = "//*[@class='c-btn u-link enabled' and text()='Search']")
	private WebElement search;

	private static String current_year = "";
	private static String current_month = "";
	private static String date_year = "";
	private static String[] month_year = new String[2];

	public IXIGO_HomePage() {
		PageFactory.initElements(driver, this);
	}

	// Method to get the ttile of Home page

	public String ValidateTiltle() {
		Reporter.log("Getting Page title...");
		return driver.getTitle();
	}

	// Method to fetch and enter departure Cities for Search

	public boolean EnterFromCity(String FromCityName) throws InterruptedException {

		boolean flag = false;
		From_City.click();
		From_City.sendKeys(FromCityName);
		Reporter.log("Enter Depature City " + FromCityName);
		test.get().info("Enter Depature City " + FromCityName);
		Thread.sleep(1000);
		Reporter.log("waiting to populate Auto Suggestion after entering City..");
		test.get().info("waiting to populate Auto Suggestion after entering City..");

		for (WebElement e : FromCity_Suggestion) {

			if (e.getAttribute("textContent").trim().contains(FromCityName)) {
				From_City.sendKeys(Keys.ENTER);
				Reporter.log("selected city...");
				test.get().info("selected city...");
				flag = true;
				break;
			}
		}

		return flag;

	}

	// Method to fetch and enter departure Cities for Search

	public boolean EnterToCity(String ToCityName) throws InterruptedException {

		boolean flag = false;
		To_City.click();
		To_City.sendKeys(ToCityName);
		Reporter.log("Enter Arrival City " + ToCityName);
		test.get().info("Enter Arrival City " + ToCityName);
		Thread.sleep(1000);
		Reporter.log("waiting to populate Auto Suggestion after entering City..");
		test.get().info("waiting to populate Auto Suggestion after entering City..");

		for (WebElement e : ToCity_Suggestion) {

			if (e.getText().toUpperCase().contains(ToCityName.toUpperCase())) {
				To_City.sendKeys(Keys.ENTER);
				Reporter.log("selected city...");
				test.get().info("selected city...");
				flag = true;
				break;
			}
		}

		return flag;

	}

	

	// Method to fetch and enter departure date for Search

	public static boolean DepartureDate(String year, String monthname, String day, WebDriver driver) {
		boolean flag = false;
		try {
			Depart_Cal.click();
			date_year = Depart_Dateyear.getText();
			month_year = date_year.split(" ");
			current_year = month_year[1];
			current_month = month_year[0];
			Reporter.log("Fetched cuurent displayed depart month and year...");
			test.get().info("Fetched cuurent displayed depart month and year...");

			while (!current_year.equals(year)) {
				Arrow_Next.click();
				date_year = Depart_Dateyear.getText();
				month_year = date_year.split(" ");
				current_year = month_year[1];
				Reporter.log("Fetched desired depart year...");
				test.get().info("Fetched desired depart year...");
			}

			while (!current_month.equals(monthname)) {
				Arrow_Next.click();
				date_year = Depart_Dateyear.getText();
				month_year = date_year.split(" ");
				current_month = month_year[0];
				Reporter.log("Fetched depart month...");
				test.get().info("Fetched depart month...");
			}

			for (WebElement d : Departure_date) {
				if (d.getText().trim().equals(day)) {
					d.click();
					Reporter.log("selected depart date...");
					test.get().info("selected depart date...");
					flag = true;
					break;
				}

			}
		} catch (Exception e) {
			Reporter.log(e.getMessage());
			e.printStackTrace();

		}
		return flag;
	}

	// Method to fetch and enter return date for Search

	public static boolean returnDate(String year, String monthname, String day, WebDriver driver) {
		boolean flag = false;
		try {
			Return_Cal.click();
			date_year = Return_Dateyear.getText();
			month_year = date_year.split(" ");
			current_year = month_year[1];
			current_month = month_year[0];
			Reporter.log("Fetched cuurent displayed return month and year...");
			test.get().info("Fetched cuurent displayed return month and year...");

			while (!current_year.equals(year)) {
				Next_arrow.click();
				date_year = Return_Dateyear.getText();
				month_year = date_year.split(" ");
				current_year = month_year[1];
				Reporter.log("Fetched desired return year...");
				test.get().info("Fetched desired return year...");
			}

			while (!current_month.equals(monthname)) {
				Next_arrow.click();
				date_year = Return_Dateyear.getText();
				month_year = date_year.split(" ");
				current_month = month_year[0];
				Reporter.log("Fetched return month...");
				test.get().info("Fetched return month...");
			}

			for (WebElement d : Return_date) {
				if (d.getText().trim().equals(day)) {
					d.click();
					flag = true;
					Reporter.log("selected return date...");
					test.get().info("selected return date...");
					break;
				}

			}
		} catch (Exception e) {
			Reporter.log(e.getMessage());
			e.printStackTrace();

		}
		return flag;
	}

	// Method to fetch and enter traveler details for Search

	public boolean Travellersdetail(String travellerno) {
		boolean flag = false;
		Traveller_field.click();
		for (WebElement traveler : Traveler_count) {
			if (traveler.getText().trim().equals(travellerno)) {
				traveler.click();
				Reporter.log("selected traveller detail...");
				test.get().info("selected traveller detail...");
				flag = true;
				break;
			}
		}
		return flag;
	}

	// Method to click search button and navigate to flight result page

	public IXIGO_FlightResult ClickSearchButton() {
		search.click();
		Reporter.log("Clicked on Search button after entering all details");
		return new IXIGO_FlightResult();

	}

}
