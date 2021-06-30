package uiautomation.PageRepository;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


import uiautomation.Common.Base;

public class TestInsuranceCompanyPage extends Base {

	private WebDriver driver; // try to not initialize driver 
	private By stars = By.cssSelector(".rv.review-action.ng-enter-element>.rvs-svg>.rating-box-wrapper>.rvs-star-svg");
	private By dropdown = By.cssSelector("div[class='dropdown second'] span[class='dropdown-placeholder']");
	private By dropdownitems = By.cssSelector(
			"div[class='dropdown second opened'] ul[class='dropdown-list ng-enter-element'] li[class='dropdown-item']");
	private By reviewfield = By.cssSelector("textarea[placeholder='Write your review...']");
	private WebElement star;
	private By latestreviewer = By.xpath("(//section[@class='rvtab-content'] //span[@class='rvtab-ci-name'])[1]");  
	private By confirmationmsg = By.xpath("//main[@review-confirmation] //div[@class='rvc-header']/h4[contains(text(),'Your review has been posted.')]");
	//private By continuebtn = By.cssSelector(".btn.rvc-continue-btn");
	//static WebDriverWait w;

	public TestInsuranceCompanyPage(WebDriver driver) {
		this.driver = driver;
	}

	public String latestReviewPosted() {
		
		System.out.println(driver.findElement(latestreviewer).getText());
		return driver.findElement(latestreviewer).getText();
		
	}
	
	public void setPage(String url) {

		driver.navigate().to(url);

	//	return new TestInsuranceCompanyPage(driver);
	}

	public String getPageTitle() {
		return driver.getTitle();

	}

	public void selectStar(int starnumber) {
		
		List<WebElement> allstars = waitTillAllPresent(driver, stars);
				
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", allstars.get(0));

		for (int i = 1; i <= starnumber; i++) {

			Actions actions = new Actions(driver);
			star = driver.findElement(By.cssSelector(
					".rv.review-action.ng-enter-element>.rvs-svg>.rating-box-wrapper>.rvs-star-svg:nth-of-type(" + i
							+ ")"));

			actions.moveToElement(star).perform();

		}
		star.click();
	}

	public boolean submitReview(File reviewfile) throws IOException {
		WebElement reviewbox = driver.findElement(reviewfield);
		// reviewbox.click();
		
		BufferedReader bf = new BufferedReader(new FileReader(reviewfile));
		String reviewcontent = " ";

		reviewcontent = bf.readLine();

		System.out.println(reviewcontent);
		bf.close();
		reviewbox.sendKeys(reviewcontent);
		waitToClick(driver,(By.xpath("//sub-navigation //div[contains(text(),'Submit')]"))).click();
		return waitToClick(driver, confirmationmsg).isDisplayed();
	}

	
	public boolean isGettingLit(int totalstars) {
		boolean flag = true;

		for (int i = 1; i <= totalstars; i++) {
			Actions actions = new Actions(driver);
			WebElement star = driver.findElement(By.cssSelector(
					".rv.review-action.ng-enter-element>.rvs-svg>.rating-box-wrapper>.rvs-star-svg:nth-of-type(" + i
							+ ")"));

			actions.moveToElement(star).perform();
			String s = driver.findElement(By.cssSelector(
					".rv.review-action.ng-enter-element>.rvs-svg>.rating-box-wrapper>.rvs-star-svg:nth-of-type(" + i
							+ ")>g>path"))
					.getAttribute("d");
			System.out.println(s);
			if (!s.contains("M31.326")) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public void selectDropdown(String dropdownname) {
		waitToClick(driver, dropdown).click();
		List<WebElement> items = driver.findElements((dropdownitems));
		System.out.println(items.size());
		for (WebElement item : items) {
			// System.out.println(item.getText());
			if (item.getText().contains(dropdownname)) {
				item.click();
				break;
			}
		}
	}
}
