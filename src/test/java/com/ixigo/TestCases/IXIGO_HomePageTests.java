package com.ixigo.TestCases;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.ixigo.BaseClass.BaseClass;
import com.ixigo.Pages.IXIGO_HomePage;
import com.ixigo.Util.IXIGO_Util;

/*Test Senario Covered:
1. Validating Home page title
2. Entering Search details 
 */

public class IXIGO_HomePageTests extends BaseClass {

	private IXIGO_HomePage home;
	List<Map<String,String>> testdataInMap=IXIGO_Util.getMapTestData();

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
		Assert.assertTrue(home.EnterFromCity(testdataInMap.get(0).get("param1")));
		Assert.assertTrue(home.EnterToCity(testdataInMap.get(0).get("param2")));
		Assert.assertTrue(IXIGO_HomePage.DepartureDate(testdataInMap.get(0).get("param3"),
				testdataInMap.get(0).get("param4"), testdataInMap.get(0).get("param5"), driver));
		Assert.assertTrue(IXIGO_HomePage.returnDate(testdataInMap.get(0).get("param6"),
				testdataInMap.get(0).get("param7"), testdataInMap.get(0).get("param8"), driver));
		Assert.assertTrue(home.Travellersdetail(testdataInMap.get(0).get("param9")));

	}

}
