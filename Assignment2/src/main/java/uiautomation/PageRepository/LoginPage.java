package uiautomation.PageRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import uiautomation.Common.Base;

public class LoginPage extends Base {

	// recreate using page factory 
	
	private WebDriver driver;
	private By username = By.cssSelector("#email");
	private By password = By.cssSelector("#password");
	private By loginbtn = By.xpath("//button[@type='button']/span[contains(text(),'Login')]");
	private By MyWalletbtn = By.cssSelector(".burger-menu-right-menu a[aria-label='My Wallet']");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		//
	}

	public void login(String url, String un, String pass) {

		driver.get(url);
		waitToClick(driver, username).sendKeys(un);
		driver.findElement(password).sendKeys(pass);
		driver.findElement(loginbtn).click();
		waitTillPresent(driver, MyWalletbtn);
		System.out.println(driver.getTitle());

	}

	//private method returns elements
	//then use those private methods in operations 

}
