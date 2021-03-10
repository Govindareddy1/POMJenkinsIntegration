package com.qa.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.BasePage;



public class ElementUtil extends BasePage {

	public WebDriver driver;
	JavaScriptUtil jsUtil;

	
	public ElementUtil(WebDriver driver) {
		
		this.driver = driver;
		jsUtil = new JavaScriptUtil(this.driver);
		//prop = basePage.init_prop();
	}

	public List<WebElement> getElements(By locator) {
		List<WebElement> elementsList = driver.findElements(locator);
		return elementsList;
	}

	public void get(String url) {
	    driver.get(url);
	}
	
	public String getCurrentURL() {
	    String currentURL=driver.getCurrentUrl();
	    return currentURL;
	}
	
	public String randonemilId()
	{
		String emailid="uiautomation."+System.currentTimeMillis()+"@test.com";
		return emailid;	
	}
	
	public WebElement getElement(By locator) 
	{
		WebElement element = null;
		try {
			System.out.println("locator is : " + locator);
			element = driver.findElement(locator);
			
			/*if (prop.getProperty("highlight").equalsIgnoreCase("yes")) 
			{
				jsUtil.flash(element);
			}*/
			System.out.println("WebElement is created successfully : " + locator);

		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("some exception got occurred with this locator: " + locator);
		}
		return element;
	}

	public void doSendKeys(By locator, String value) {
		waitForElementPresent(locator, 10);
		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {
		waitForElementPresent(locator, 10);
		getElement(locator).click();
	}

	public void doClear(By locator) {
		waitForElementPresent(locator, 10);
		getElement(locator).clear();
	}
	public String doGetText(By locator) {
		waitForElementPresent(locator, 10);
		return getElement(locator).getText();
	}

	public boolean doIsDisplayed(By locator) {
		waitForElementPresent(locator, 10);
		return getElement(locator).isDisplayed();
	}

	// **********************************Drop Down Utils*********************************

	public void doSelectByVisibleText(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(value);
	}

	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public int doDropDownOptionsCount(By locator) {
		return doGetDropDownOptions(locator).size();
	}

	public ArrayList<String> doGetDropDownOptions(By locator) {
		ArrayList<String> ar = new ArrayList<String>();
		Select select = new Select(getElement(locator));
		List<WebElement> OptionsList = select.getOptions();

		for (int i = 0; i < OptionsList.size(); i++) {
			String text = OptionsList.get(i).getText();
			ar.add(text);
		}
		return ar;
	}

	public void doSelectDropDownValue(By locator, String value) {
		Select selectday = new Select(getElement(locator));
		List<WebElement> OptionsList = selectday.getOptions();

		for (int i = 0; i < OptionsList.size(); i++) {
			String text = OptionsList.get(i).getText();
			if (text.equals(value)) {
				OptionsList.get(i).click();
				break;
			}
		}
	}

	public void doSelectDropDownValueWithoutSelect(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);

		for (int i = 0; i < optionsList.size(); i++) {
			String text = optionsList.get(i).getText();
			if (text.equals(value)) {
				optionsList.get(i).click();
				break;
			}
		}
	}

	public void selectChoiceValues(By locator, String... value) {
		List<WebElement> choiceList = getElements(locator);

		if (!value[0].equalsIgnoreCase("ALL")) {

			for (int i = 0; i < choiceList.size(); i++) {
				String text = choiceList.get(i).getText();
				System.out.println(text);

				for (int k = 0; k < value.length; k++) {
					if (text.equals(value[k])) {
						choiceList.get(i).click();
						break;
					}
				}

			}
		}
		// select all the values:
		else {
			try {
				for (int all = 0; all < choiceList.size(); all++) {
					choiceList.get(all).click();
				}
			} catch (Exception e) {

			}
		}
	}

	/*
	 * 
	 */
	public void selectChoiceValuesFromList(By locator1,By locator, String... value) throws InterruptedException {
		// first open the select box to get list of values
		getElement(locator1).click();
		Thread.sleep(2000);
		// then store in list
		List<WebElement> choiceList = getElements(locator);

		if (!value[0].equalsIgnoreCase("ALL")) {

			for (int i = 0; i < choiceList.size(); i++) {
				String text = choiceList.get(i).getText();
				//System.out.println(text);

				for (int k = 0; k < value.length; k++) {
					if (text.equals(value[k])) {
						choiceList.get(i).click();
						break;
					}
				}

			}
		}
		// select all the values:
		else {
			try {
				for (int all = 0; all < choiceList.size(); all++) {
					choiceList.get(all).click();
				}
			} catch (Exception e) {

			}
		}
	}
	
	
	// **********************************Actions class Utils*********************************

	public void doDragAndDrop(By source, By target) {
		Actions action = new Actions(driver);
		WebElement sourceEle = getElement(source);
		WebElement targetEle = getElement(target);
		action.dragAndDrop(sourceEle, targetEle).build().perform();
	}

	public void doActionsSendKeys(By locator, String value) {
		Actions action = new Actions(driver);
		action.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsClick(By locator) {
		waitForElementPresent(locator, 10);
		Actions action = new Actions(driver);
		action.click(getElement(locator)).build().perform();
	}

	// ***************************** Wait Utils*******************************************
	
	//checking that all elements present on the web page that match the locatorare visible
	public List<WebElement> visibilityofAllElements(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
    //checking that an element is present on the DOM of a page
	public WebElement waitForElementPresent(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return element;
	}
	//Element present on the DOM of a page, isvisible
	public WebElement waitForElementToBeVisible(By locator, int timeout) {
		WebElement element = getElement(locator);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOf(element));
		return element;
	}

	//Verifies if the given element is present or not
	public WebElement verifywaitForElementToBeVisible(By locator, int timeout) {
		WebElement element = getElement(locator);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element;
	}
	
	//Verifies if the given element is present/clickable on the screen
	public WebElement waitForElementToBeClickable(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		return element;
	}
//An expectation for the URL of the current page to contain specific text.
	public boolean waitForUrl(String url, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.urlContains(url));
	}
	//Verifies if the alert is present or not.
	public Alert waitForAlertToBePresent(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		return alert;
	}

	// clickWhenReady:
	public void clickWhenReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
	}

	public String waitForTitleToBePresent(String title, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.titleContains(title));
		return driver.getTitle();
	}

	public WebElement waitForElementFluentWait(By locator, int timeout) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(Constants.POLLING_TIME_PERIOD))
				.ignoring(NoSuchElementException.class);

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}
	
	public void waitforElement(long timeout) {
		try 
		{
			Thread.sleep(timeout);
		} 
		catch (InterruptedException ie) 
		{
			ie.printStackTrace();
		}
	}
	
	public void waitforElementLong() {
		try 
		{
			Thread.sleep(10000);
		} 
		catch (InterruptedException ie) 
		{
			ie.printStackTrace();
		}
	}
	public void waitforElementshort() {
		try 
		{
			Thread.sleep(3000);
		} 
		catch (InterruptedException ie) 
		{
			ie.printStackTrace();
		}
	}
	/*public WebElement waitForElementWithFluentWait(By locator, int timeout) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(Constants.POLLING_TIME_PERIOD))
				.ignoring(NoSuchElementException.class);

		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return getElement(locator);
			}
		});

		return element;
	}*/

}
