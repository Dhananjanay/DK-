package com.ixigo.Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.SkipException;

import com.ixigo.Util.IXIGO_Util;
import com.ixigo.BaseClass.BaseClass;

public class IXIGO_FlightResult extends BaseClass {

	@FindBy(xpath = "//div[@class='fltr-hdr' and text()='Stops']")
	private static WebElement Stop_text;

	@FindBy(xpath = "//div[@class='stop-info']")
	private static List<WebElement> Stop_options = new ArrayList<>();

	@FindBy(xpath = "//div[text()='Departure from PNQ']/following-sibling::div[@class='fltr-cntnt']//div[@class='lbl']")
	private static List<WebElement> Departure_options = new ArrayList<>();

	@FindBy(xpath = "//div[text()='Airlines']//following::div[@class='arln-nm']")
	private static List<WebElement> Airlinesoptions = new ArrayList<>();

	@FindBy(xpath = "//div[text()='Non stop']/../preceding-sibling::span[@class='checkbox-button u-pos-rel u-v-align-top u-ib ']")
	private static WebElement NonStop_checkbox;

	@FindBy(xpath = "//div[@class='result-wrpr']//div[@class='c-price-display u-text-ellipsis ']")
	private static List<WebElement> Flight_price = new ArrayList<>();;

	private static String previous = "";

	public IXIGO_FlightResult() {
		PageFactory.initElements(driver, this);
	}

	// Method to fetch no of stops option displayed
	public static List<String> StopOptions() {
		IXIGO_Util.Explicitwait(40, "//div[@class='fltr-hdr' and text()='Stops']");
		List<String> stop_optionResult = new ArrayList<String>();
		for (WebElement stop : Stop_options) {
			stop_optionResult.add(stop.getText());
		}
		return stop_optionResult;
	}

	// Method to fetch no of departure option displayed
	public static List<String> DepartureOptions() {
		List<String> departure_optionResult = new ArrayList<String>();
		for (WebElement depart : Departure_options) {
			departure_optionResult.add(depart.getText());
		}
		return departure_optionResult;
	}

	// Method to fetch no of Airlines option displayed
	public static List<String> AirlinesOptions() {
		List<String> airlines_optionResult = new ArrayList<String>();
		for (WebElement airline : Airlinesoptions) {
			airlines_optionResult.add(airline.getAttribute("textContent"));
		}
		return airlines_optionResult;
	}

	// Method to fetch all flight details having fare less than 5000 INR
	// displayed
	public static void Getflightsbydesiredfilterprice() {
		NonStop_checkbox.click();
		int i = 2;
		for (WebElement fare : Flight_price) {
			String price = fare.getText().trim();
			int flight_fare = Integer.parseInt(price);
			if (flight_fare < 5000) {
				if (price.equals(previous)) {
					String flight_name = driver.findElement(
							By.xpath("(//div[@class='c-price-display u-text-ellipsis ']/span[text()='" + price
									+ "']/../../../preceding-sibling::div/div[@class='airline-text'])[" + i + "]"))
							.getText();
					String dep_time = driver.findElement(
							By.xpath("(//div[@class='c-price-display u-text-ellipsis ']/span[text()='" + price
									+ "']/../../../preceding-sibling::div/div[@class='divider']/preceding-sibling::div[@class='time'])["
									+ i + "]"))
							.getText();
					System.out.println("Flight details are : Flight name- " + flight_name + "     " + "Departure time- "
							+ dep_time + "       " + "Flight price-" + price);
					Reporter.log("Flight details are : Flight name- " + flight_name + "     " + "Departure time- "
							+ dep_time + "       " + "Flight price-" + price);
					i++;
				}

				else {
					String flight_name = driver
							.findElement(By.xpath("//div[@class='c-price-display u-text-ellipsis ']/span[text()='"
									+ price + "']/../../../preceding-sibling::div/div[@class='airline-text']"))
							.getText();
					String dep_time = driver
							.findElement(
									By.xpath("//div[@class='c-price-display u-text-ellipsis ']/span[text()='" + price
											+ "']/../../../preceding-sibling::div/div[@class='divider']/preceding-sibling::div[@class='time']"))
							.getText();
					System.out.println("Flight details are : Flight name- " + flight_name + "     " + "Departure time- "
							+ dep_time + "       " + "Flight price-" + price);
					Reporter.log("Flight details are : Flight name- " + flight_name + "     " + "Departure time- "
							+ dep_time + "       " + "Flight price-" + price);
					i = 2;
				}

				previous = price;
			}
		}

	}

}
