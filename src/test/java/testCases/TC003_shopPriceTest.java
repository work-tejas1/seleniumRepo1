package testCases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import objectRepository.Komoot_LandingPage;
import objectRepository.Komoot_LoginPage;
import objectRepository.Komoot_Shop;

public class TC003_shopPriceTest {
	public static WebDriver driver;

	public static String decodedStr(String encodedStr) {
		byte[] decoded = Base64.decodeBase64(encodedStr);
		return new String(decoded);
	}

	@BeforeTest
	public void LaunchBrowser() {
		System.out.println("#### Test Begin ####");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.komoot.com/");
	}

	@Test
	public void ShopPriceTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Komoot_LandingPage LandingPage = new Komoot_LandingPage(driver);
		wait.until(ExpectedConditions.elementToBeClickable(LandingPage.acceptAll()));
		LandingPage.acceptAll().click();
		LandingPage.signUp().click();
		Komoot_LoginPage LoginPage = new Komoot_LoginPage(driver);
		wait.until(ExpectedConditions.visibilityOf(LoginPage.email()));
		LoginPage.email().sendKeys("joan34p_y743a@hexud.com");
		LoginPage.continueWithEmail().click();
		wait.until(ExpectedConditions.visibilityOf(LoginPage.password()));
		LoginPage.password().sendKeys(decodedStr("am9hbjM0cF95NzQzYUBoZXh1ZC5jb21N"));
		LoginPage.logIn().click();
		wait.until(ExpectedConditions.visibilityOf(LoginPage.user()));
		String actualUser = LoginPage.user().getText();
		String expectedUser = "Admin User";
		Assert.assertEquals(expectedUser, actualUser);
		System.out.println("Expected User = " + actualUser);
		Komoot_Shop ShopPage = new Komoot_Shop(driver);
		wait.until(ExpectedConditions.elementToBeClickable(ShopPage.shop()));
		ShopPage.shop().click();
		Thread.sleep(1000);
		List<String> a = new ArrayList<>();
		List<WebElement> text = ShopPage.packType();
		for (int i = 0; i < text.size(); i++) {
			
			a.add(text.get(i).getText());
			System.out.println(text.get(i).getText());
			if (text.get(i).getText().contains("World Pack")) {
				System.out.println("�29.99");
				wait.until(ExpectedConditions.visibilityOf(ShopPage.worldPack()));
			} else if (text.get(i).getText().contains("Region Bundle")) {
				System.out.println("�8.99");
			} else if (text.get(i).getText().contains("Single Region")) {
				System.out.println("�3.99");
			}
		}
	}

	@AfterTest(alwaysRun = true)
	public void CloseBrowser() {
		System.out.println("#### Test End ####");
		driver.quit();
	}
}
