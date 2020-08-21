package com.ixigo.TestCases;

import java.util.Calendar;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.ixigo.BaseClass.BaseClass;
import com.ixigo.Pages.IXIGO_HomePage;

/*Test Senario Covered:
1. Validating Home page title
2. Entering Search details 
 */

public class IXIGO_HomePageTests extends BaseClass {

	private IXIGO_HomePage home;

	@BeforeClass
	public void init() {
		home = new IXIGO_HomePage();
	}

	@Test(priority = 1)
	public void ValidateHomePage() {
		String homePageTitle = home.ValidateTiltle();
		Assert.assertEquals(homePageTitle, Config.getProperty("Ixigo_homePageTitle"));
	}

	@Test(priority = 2)
	public void SearchFlight() throws InterruptedException {
		Assert.assertTrue(home.EnterFromCity(Config.getProperty("From_City")));
		Assert.assertTrue(home.EnterToCity(Config.getProperty("To_City")));
		Assert.assertTrue(IXIGO_HomePage.DepartureDate(Config.getProperty("Departure_year"),
				Config.getProperty("Departure_month"), Config.getProperty("Departue_date"), driver));
		Assert.assertTrue(IXIGO_HomePage.returnDate(Config.getProperty("Returen_year"),
				Config.getProperty("Return_month"), Config.getProperty("Return_date"), driver));
		Assert.assertTrue(home.Travellersdetail(Config.getProperty("Travellerno")));

	}

}
