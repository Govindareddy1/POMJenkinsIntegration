package com.qa.smokesuite.testcases;

import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.Status;
import com.qa.base.BasePage;
import com.qa.extentereport.ExtentTestManager;
import com.qa.pages.HomePage;
import com.qa.pages.LoginPage;
import com.qa.utils.Constants;
import com.qa.utils.ExcelUtil;

public class DemoTest_case2 extends BasePage
{
	
	public BasePage basePage;
	public Properties prop;
	LoginPage loginPage;
	HomePage homePage;
	
	@BeforeMethod
	public void setUp() {
		
		basePage = new BasePage();
		prop = basePage.init_prop();
		driver = basePage.init_driver(prop);
		loginPage=new LoginPage(driver);
	}
	
	@org.testng.annotations.Test
	public void tc_test1()
	{
		System.out.println("....test111...");
		ExtentTestManager.getTest().log(Status.INFO, "test1");
	}

	
	@org.testng.annotations.Test
	public void tc_test2() throws InterruptedException
	{
		System.out.println("....test2..");
		ExtentTestManager.getTest().log(Status.INFO, "test2 ------");
		loginPage.doLaunch_URL(prop.getProperty("demo2_url"));
		Thread.sleep(1000);
		ExtentTestManager.getTest().log(Status.INFO, "test2 URL 2 launched ------");
	}
	
	@DataProvider
	public Object[][] getTestData(){
		Object data[][] = ExcelUtil.getTestData(Constants.TEST_DATA_SHEET_NAME);
		return data;
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
