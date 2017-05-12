package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.yandex.qatools.allure.annotations.Step;
import utils.TimeUtil;

public final class ConverterPage {

	private static final By MY_USD_LOCATOR = By
			.xpath(".//*[text()='У меня есть']/..//div[text()='USD']");
	private static final By MY_RUR_LOCATOR = By
			.xpath(".//*[text()='У меня есть']/..//div[text()='RUR']");
	private static final By MY_INPUT_LOCATOR = By
			.xpath(".//*[text()='У меня есть']/..//input[@type]");
	private static final By WISH_USD_LOCATOR = By
			.xpath(".//*[text()='Хочу приобрести']/..//div[text()='USD']");
	private static final By WISH_RUR_LOCATOR = By
			.xpath(".//*[text()='Хочу приобрести']/..//div[text()='RUR']");
	private static final By WISH_INPUT_LOCATOR = By
			.xpath(".//*[text()='Хочу приобрести']/..//input[@type]");
	private static final By DATE_LOCATOR = By
			.cssSelector("input[placeholder='Дата']");
	private static final By CUSTOM_SELECT_LOCATOR = By
			.cssSelector("div.custom_select");
	private static final By CB_RF_LOCATOR = By.xpath("//div[text()='ЦБ РФ']");
	private static final By FOREX_LOCATOR = By.xpath("//div[text()='Forex']");
	private static final By EXCHANGE_LOCATOR = By
			.xpath("//div[text()='Пункты обмена']");

	private static final String URL_MATCH = "https://cash.rbc.ru/";

	private WebDriver driver;

	public ConverterPage(WebDriver driver) {
		if (!driver.getCurrentUrl().contains(URL_MATCH)) {
			throw new IllegalStateException(
					"This is not the page you are expected");
		}

		this.driver = driver;
	}

	private void setCurrency(By locator) {
		final WebElement element = driver.findElement(locator);
		if (!element.isSelected()) {
			element.click();
		}
	}

	private void setInputValue(By inputLocator, String value) {
		final WebElement inputField = driver.findElement(inputLocator);
		inputField.clear();
		inputField.sendKeys(value);
	}

	private String getInputValue(By inputLocator) {
		String inputValue = null;
		WebElement inputField = driver.findElement(inputLocator);
		String newInputValue = inputField.getAttribute("value");
		while (!newInputValue.equals(inputValue)) {
			inputValue = newInputValue;
			// wait for conversion operation is completed and all transitional
			// results disappear from input field. This sleep is the only way
			// since proposal waiting for AJAX is completed doesn't work
			TimeUtil.sleepTimeoutMillis(500);
			newInputValue = inputField.getAttribute("value");
		}
		return inputValue;
	}

	@Step("В графе \"у меня есть\" выбрать USD")
	public void setMyUsd() {
		setCurrency(MY_USD_LOCATOR);
	}

	@Step("В графе \"у меня есть\" выбрать RUR")
	public void setMyRur() {
		setCurrency(MY_RUR_LOCATOR);
	}

	@Step("В графе \"хочу приобрести\" выбрать USD")
	public void setWishUsd() {
		setCurrency(WISH_USD_LOCATOR);
	}

	@Step("В графе \"хочу приобрести\" выбрать RUR")
	public void setWishRur() {
		setCurrency(WISH_RUR_LOCATOR);
	}

	@Step("В графе \"у меня есть\" ввести {0}")
	public void setMyInput(String value) {
		setInputValue(MY_INPUT_LOCATOR, value);
	}

	@Step("Взять значение из графы \"хочу приобрести\"")
	public String getWishInput() {
		return getInputValue(WISH_INPUT_LOCATOR);
	}

	@Step("Установить дату {0}")
	public void setDate(String date) {
		setInputValue(DATE_LOCATOR, date);
	}

	@Step("Установить курс ЦБ РФ")
	public void setСbRf() {
		setCurrencyType(CB_RF_LOCATOR);
	}

	@Step("Установить курс Forex")
	public void setForex() {
		setCurrencyType(FOREX_LOCATOR);
	}

	@Step("Установить курс пунктов обмена")
	public void setExchange() {
		setCurrencyType(EXCHANGE_LOCATOR);
	}

	private void setCurrencyType(By locator) {
		driver.findElement(CUSTOM_SELECT_LOCATOR).click();
		driver.findElement(locator).click();
	}
}
