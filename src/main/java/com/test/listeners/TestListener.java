package com.test.listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.base.BasePage;
import com.qa.extentereport.ExtentManager;
import com.qa.extentereport.ExtentTestManager;

public class TestListener implements ITestListener {
 
	public void onStart(ITestContext context) 
	{
		System.out.println("*** Test Suite " + context.getName() + " started ***");
	}

	public void onFinish(ITestContext context) {
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	public void onTestStart(ITestResult result) {
		System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));
		//code added here
		String testcaseclassName = result.getTestClass().getRealClass().getSimpleName();
		ExtentTestManager.startTest(testcaseclassName + "  ::  " +result.getMethod().getMethodName());
		//-----------------------------------------------------------------
	
		String qualifiedName = result.getMethod().getQualifiedName();
		int last = qualifiedName.lastIndexOf(".");
		int mid = qualifiedName.substring(0, last).lastIndexOf(".");
		String className = qualifiedName.substring(mid + 1, last);
		System.out.println("#####################################################");
		
		ExtentTestManager.getTest().assignCategory(result.getTestContext().getSuite().getName());
		ExtentTestManager.getTest().assignCategory(className);
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
		ExtentTestManager.getTest().log(Status.PASS, "Test passed");
	}

	/*public void onTestFailure(ITestResult result) {
		System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
		ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
	}*/

	
	public void onTestFailure(ITestResult result)
	{
		//log.info("*** Test execution " + result.getMethod().getMethodName() + " failed...");
		//log.info((result.getMethod().getMethodName() + " failed!"));

	//	ITestContext context = result.getTestContext();
		//WebDriver driver = (WebDriver) context.getAttribute("driver");

		String targetLocation = null;
		//String testClassName = getTestClassName(result.getInstanceName()).trim();
		String testClassName = result.getTestClass().getRealClass().getSimpleName();
		long timeStamp = System.currentTimeMillis(); // get timestamp
		String testMethodName = result.getName().toString().trim();
		String screenShotName = testMethodName + timeStamp + ".png";
		String fileSeperator = System.getProperty("file.separator");
		String reportsPath = System.getProperty("user.dir") + fileSeperator + "TestReport" + fileSeperator + "screenshots";
		//log.info("Screen shots reports path - " + reportsPath);
		try 
		{
			File file = new File(reportsPath + fileSeperator + testClassName); // Set screenshots folder																																	
			if (!file.exists())
			{
				if (file.mkdirs()) 
				{
					System.out.println("Directory: " + file.getAbsolutePath() + " is created!");
					//log.info("Directory: " + file.getAbsolutePath() + " is created!");
				} 
				else {
					System.out.println("Failed to create directory: " + file.getAbsolutePath());
					//log.info("Failed to create directory: " + file.getAbsolutePath());
				}

			}

			File screenshotFile = ((TakesScreenshot) BasePage.getDriver()).getScreenshotAs(OutputType.FILE);
			targetLocation = reportsPath + fileSeperator + testClassName + fileSeperator + screenShotName;// define location
																										
			File targetFile = new File(targetLocation);
			System.out.println("Screen shot file location - " + screenshotFile.getAbsolutePath());
			//log.info("Screen shot file location - " + screenshotFile.getAbsolutePath());
			System.out.println("Target File location - " + targetFile.getAbsolutePath());
			//log.info("Target File location - " + targetFile.getAbsolutePath());
			FileHandler.copy(screenshotFile, targetFile);

		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found exception occurred while taking screenshot " + e.getMessage());
			//log.info("File not found exception occurred while taking screenshot " + e.getMessage());
		} 
		catch (Exception e) 
		{
			System.out.println("An exception occurred while taking screenshot " + e.getCause());
			//log.info("An exception occurred while taking screenshot " + e.getCause());
		}

		// attach screenshots to report
		try {
			
			ExtentTestManager.getTest().fail("Screenshot",MediaEntityBuilder.createScreenCaptureFromPath(targetLocation).build());
		}
		catch (IOException e) 
		{
			System.out.println("An exception occured while taking screenshot " + e.getCause());
			//log.info("An exception occured while taking screenshot " + e.getCause());
		}
		ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
	}
	
	
	public void onTestSkipped(ITestResult result) 
	{
		System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
	{
		System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}

}