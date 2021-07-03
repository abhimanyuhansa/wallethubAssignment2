package uiautomation.Tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import uiautomation.Common.Base;
import uiautomation.PageRepository.LoginPage;
import uiautomation.PageRepository.TestInsuranceCompanyPage;

public class SubmitReviewFlowTest extends Base {

	//public WebDriver driver;
	private static final int STARCOUNT = 4;
	private static final String CATEGORY = "Health Insurance";
	static TestInsuranceCompanyPage profilepage;
	static File reviewfile = new File("resources//content.txt");
	

	@Test(priority = 1, enabled = true)
	public void starsGettingHighligthed() {
		profilepage = new TestInsuranceCompanyPage(driver);
		profilepage.setPage(prop.getProperty("profileurl"));
		assertTrue(profilepage.isGettingLit(STARCOUNT));
	}

	@Test(priority = 2, enabled = true)
	public void rating() {

		profilepage.selectStar(STARCOUNT);
		profilepage.getPageTitle();
		assertEquals(profilepage.getPageTitle(), "test insurance company metatitle test");
	}

	@Test(priority = 3, enabled = true)
	public void submitReview() throws IOException {
		profilepage.selectDropdown(CATEGORY);
		assertTrue(profilepage.submitReview(reviewfile));
	}

	@Test(priority = 4, enabled = true)
	public void verifyReviewPosted() {
		profilepage = new TestInsuranceCompanyPage(driver);
		profilepage.setPage(prop.getProperty("profileurl"));
		assertEquals(profilepage.latestReviewPosted(), "Your Review");
		
	}

	@BeforeTest
	public void init() throws IOException {
		driver = initializeDriver();
		LoginPage login = new LoginPage(driver);
		prop = readProperties();
		String url = prop.getProperty("loginurl");
		String un = prop.getProperty("username");
		String pass = prop.getProperty("password");
		login.login(url, un, pass);
		
	}

	@AfterTest
	public void teardown() {
		quitDriver(driver);
	}

}
