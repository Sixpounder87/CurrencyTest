package testcases;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.ConverterPage;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.annotations.Title;
import utils.ReadPropertyData;

public final class ConverterTest {

	private WebDriver driver;

	@BeforeClass
	public void preconditions() {
		System.setProperty("webdriver.chrome.driver",
				ReadPropertyData.getDriverPath());
		final ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
	}

	@Title("Тест 02")
	@Test
	public void test02() {
		openPage(ReadPropertyData.getBaseUrl());
		final ConverterPage homePage = new ConverterPage(driver);
		homePage.setMyUsd();
		homePage.setWishRur();
		homePage.setMyInput("1");
		final Float currencyValue = Float.parseFloat(homePage.getWishInput());
		assertTrue(currencyValue > 40 && currencyValue < 90);
		homePage.setMyRur();
		homePage.setWishUsd();
		homePage.setMyInput(currencyValue.toString());
		assertEquals(homePage.getWishInput(), "1");
	}

	@AfterClass
	public void postconditions() {
		driver.quit();
	}
	
	@Step("Зайти на страницу {0}")
	private void openPage(String url) {
		driver.get(url);
		driver.switchTo().window(driver.getWindowHandle());
	}

}
