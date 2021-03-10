package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.base.BasePage;
import com.qa.utils.Constants;
import com.qa.utils.ElementUtil;

public class HomePage extends BasePage {

	
	
	private WebDriver driver;
	public HomePage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(this.driver);
	}

	
	By txtusername = By.id("txtUsername");
	By txtpassword=By.id("txtPassword");
	By loginBtn=By.id("btnLogin");
	
	
	
	public String getHomePageTitle() 
	{
		return elementUtil.waitForTitleToBePresent(Constants.HOME_PAGE_TITLE, 60);
	}
	
	
	public String sspGetCurrentURL() throws InterruptedException {
		Thread.sleep(3000);
		return elementUtil.getCurrentURL();
		
	}
}
