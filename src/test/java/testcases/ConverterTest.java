package testcases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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

	@BeforeMethod
	@Step("Зайти на страницу {0}")
	public void openPage() {
		final String url = ReadPropertyData.getBaseUrl();
		if (!driver.getCurrentUrl().contains(url)) {
			driver.get(url);
			driver.switchTo().window(driver.getWindowHandle());
		}
	}

	@Title("Тест 02")
	@Test
	public void test02() {
		final ConverterPage homePage = new ConverterPage(driver);
		homePage.setMyUsd();
		homePage.setWishRur();
		homePage.setMyInput("1");
		final Float currencyValue = Float.parseFloat(homePage.getWishInput());
		assertTrue(currencyValue > 40 && currencyValue < 90,
				"неправильная конвертация курса");
		homePage.setMyRur();
		homePage.setWishUsd();
		homePage.setMyInput(currencyValue.toString());
		assertEquals(homePage.getWishInput(), "1",
				"обратная конвертация курса не совпадает с прямой");
	}

	@Title("Тест 07")
	@Test
	public void test07() {
		final ConverterPage homePage = new ConverterPage(driver);
		homePage.setDate("1.05.2017");
		homePage.setMyRur();
		homePage.setWishUsd();
		homePage.setMyInput("5000.1");
		homePage.setСbRf();
		Float currencyValue = Float.parseFloat(homePage.getWishInput());
		assertTrue(currencyValue > 87.5 && currencyValue < 88,
				"неправильная конвертация курса");
		homePage.setForex();
		currencyValue = Float.parseFloat(homePage.getWishInput());
		assertTrue(currencyValue > 87.5 && currencyValue < 88,
				"неправильная конвертация курса");
		homePage.setExchange();
		currencyValue = Float.parseFloat(homePage.getWishInput());
		assertTrue(currencyValue > 87 && currencyValue < 88,
				"неправильная конвертация курса");
	}

	@AfterClass
	public void postconditions() {
		driver.quit();
	}
}
