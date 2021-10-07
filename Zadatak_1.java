package d30_09_2021;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Zadatak_1 {
	private WebDriver driver;
	private WebDriverWait waiter;

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "driver-lib\\chromedriver.exe");

		this.driver = new ChromeDriver();
		this.waiter = new WebDriverWait(driver, 10);

		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.opensource-socialnetwork.org/login");
	}

	@Test
	public void testAddFriend() throws InterruptedException {

		driver.findElement(By.xpath("//*[@id='ossn-login']/fieldset/div[4]/input")).click();
		driver.findElement(By.name("q")).sendKeys("Gwen Rohan" + Keys.ENTER);

		Thread.sleep(500);

		boolean addBtnExists = true;

		try {
			driver.findElement(By.xpath("//*[@class= 'right users-list-controls']/a"));
			addBtnExists = true;
			Thread.sleep(500);

		} catch (Exception e) {
			addBtnExists = false;
		}

		Assert.assertTrue(addBtnExists, "'Add Friend' button not found.");
		Thread.sleep(500);

		driver.findElement(By.xpath("//*[@class= 'right users-list-controls']/a")).click();
		Thread.sleep(500);

		boolean msgExists = true;

		String successMsg = " ";

		try {
			driver.findElement(By.className("alert alert-success"));

			successMsg = driver.findElement(By.className("alert alert-success")).findElement(By.tagName("a")).getText();

			waiter.until(ExpectedConditions.invisibilityOfElementWithText(By.className("alert alert-success"),
					"Your friend request has been sent!"));

			Thread.sleep(500);

		} catch (Exception e) {

			msgExists = false;
		}

		if (successMsg.equals("Your friend request has been sent!")) {
			Assert.assertTrue(msgExists, "Message not displayed.");
		}
		Thread.sleep(500);

		boolean cnclBtnExists = true;

		try {
			driver.findElement(By.xpath("//*[@class= 'right users-list-controls']/a"));

		} catch (Exception e) {
			cnclBtnExists = false;
		}

		Assert.assertTrue(cnclBtnExists, "'Cancel Request' button not found.");

		driver.navigate().refresh();
		Thread.sleep(2000);

		if (cnclBtnExists) {
			driver.findElement(By.xpath("//*[@class= 'right users-list-controls']/a")).click();
		}

		String cancelMsg = "";

		try {
			driver.findElement(By.className("alert alert-danger"));

			cancelMsg = driver.findElement(By.className("alert alert-danger")).findElement(By.tagName("a")).getText();

			waiter.until(ExpectedConditions.invisibilityOfElementWithText(By.className("alert alert-danger"),
					"Friend request deleted!"));

			Thread.sleep(500);

		} catch (Exception e) {

			msgExists = false;
		}

		if (cancelMsg.equals("Friend request deleted!")) {
			Assert.assertTrue(msgExists, "Message not displayed.");
		}
		Thread.sleep(500);

		try {
			driver.findElement(By.xpath("//*[@class= 'right users-list-controls']/a"));
			addBtnExists = true;
		} catch (Exception e) {
			addBtnExists = false;
		}

		Assert.assertTrue(addBtnExists, "'Add Friend' button not found");

	}

	@AfterMethod
	public void quit() throws InterruptedException {
		Thread.sleep(500);
		driver.quit();
	}

}
