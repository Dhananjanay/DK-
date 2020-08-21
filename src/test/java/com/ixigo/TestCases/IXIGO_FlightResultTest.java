package com.ixigo.TestCases;

import java.util.ArrayList;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ixigo.BaseClass.BaseClass;
import com.ixigo.Pages.IXIGO_FlightResult;
import com.ixigo.Pages.IXIGO_HomePage;
import com.ixigo.Util.IXIGO_Util;

/*Test Senario Covered:
1.Validate the result page
2.Validate filter option for Stops, departure and Airlines – Select Non-Stop in Stops filter option
3.Print the list of airlines details (Only Airline Number, Departure Time and Fare) having fare < 5000 */

public class IXIGO_FlightResultTest extends BaseClass {

	private IXIGO_HomePage home;
	private IXIGO_FlightResult results;

	@BeforeClass
	public void init() {
		home = new IXIGO_HomePage();
		results = home.ClickSearchButton();

	}

	@Test(priority = 1, dependsOnMethods = "com.ixigo.TestCases.IXIGO_HomePageTests.SearchFlight")
	public void ValidateNumberOfStopOptions() {

		Reporter.log("*********** No of stop options***************");
		ArrayList<String> Expected_Stopoptions = new ArrayList<String>(
				Arrays.asList(Config.getProperty("First_Stopoption"), Config.getProperty("Second_Stopoption"),
						Config.getProperty("Third_Stopoption")));
		Assert.assertEquals(IXIGO_FlightResult.StopOptions(), Expected_Stopoptions);
		System.out.println("*********** correct stops options displayed***************");
		Reporter.log("*********** correct stops options displayed***************");
	}

	@Test(priority = 2)
	public void ValidateResultPage() {
		String resultPageTitle = driver.getTitle();
		Assert.assertEquals(resultPageTitle, Config.getProperty("Ixigo_resultPageTitle"));
	}

	@Test(priority = 3, dependsOnMethods = "com.ixigo.TestCases.IXIGO_HomePageTests.SearchFlight")
	public void ValidateNumberOfDepartureOptions() {
		Reporter.log("*********** No of departure options***************");
		ArrayList<String> Expected_departureoptions = new ArrayList<String>(
				Arrays.asList(Config.getProperty("First_Departureoption"), Config.getProperty("second_Departureoption"),
						Config.getProperty("Third_Departureoption"), Config.getProperty("Fourth_Departureoption")));
		Assert.assertEquals(IXIGO_FlightResult.DepartureOptions(), Expected_departureoptions);
		System.out.println("*********** correct departure options displayed***************");
		Reporter.log("*********** correct departure options displayed***************");

	}

	@Test(priority = 4, dependsOnMethods = "com.ixigo.TestCases.IXIGO_HomePageTests.SearchFlight")
	public void ValidateNumberOfAirlinesOptions() {
		Reporter.log("*********** No ofAirlines options***************");
		ArrayList<String> Expected_airlinesoptions = new ArrayList<String>(
				Arrays.asList(Config.getProperty("First_Airlineoption"), Config.getProperty("Second_Airlineoption"),
						Config.getProperty("Third_Airlineoption"), Config.getProperty("Fourth_Airlineoption"),
						Config.getProperty("Fifth_Airlineoption"), Config.getProperty("Sixth_Airlineoption")));
		Assert.assertEquals(IXIGO_FlightResult.AirlinesOptions(), Expected_airlinesoptions);
		System.out.println("*********** correct Airlines options displayed***************");
		Reporter.log("*********** correct Airlines options displayed***************");
	}

	@Test(priority = 5, dependsOnMethods = "com.ixigo.TestCases.IXIGO_HomePageTests.SearchFlight")
	public void ValidateAirlinesdetails() {
		System.out.println("*********** Airlines filtered details***************");
		IXIGO_FlightResult.Getflightsbydesiredfilterprice();
		Reporter.log("*********** correct Airlines options displayed***************");

	}

}
