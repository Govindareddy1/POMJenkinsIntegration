package com.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.utils.ElementUtil;
import com.qa.utils.OptionsManager;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author AGREDDY
 *
 */
public class BasePage 
{
	public WebDriver driver;
	public Properties prop;
	public ElementUtil elementUtil;
	public OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public static synchronized WebDriver getDriver() 
	{
		return tlDriver.get();
	}

	/**
	 * this method is used to initialize the properties from config.proeprties
	 * file on the basis of given env variable
	 * 
	 * 
	 * @return prop
	 */
	public Properties init_prop() {
		prop = new Properties();
		String path = null;
		String env = null;

		try {
			env = System.getProperty("env");
			System.out.println("Entered env value is--->" + env);
			if (env == null) 
			{
				
				 path = ".\\src\\main\\java\\com\\qa\\config\\config.properties";
				 System.out.println("Entered env value is " + env + " so tests executing on --->" + env);
			} 
			else if(env.equalsIgnoreCase("stage"))
			{ 
				
			    path = ".\\src\\main\\java\\com\\qa\\config\\stage.config.properties";
			    System.out.println("Entered env value is " + env + "so tests executing on --->" + env);
			}else
			{
				
				path = ".\\src\\main\\java\\com\\qa\\config\\test.config.properties";
				System.out.println("Entered env value is " + env + "so tests executing on --->" + env);
			}
			
			FileInputStream ip = new FileInputStream(path);
			prop.load(ip);
		} 
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
	
	
	/**
	 * this method is used to initialize the WebDriver on the basis of browser
	 * 
	 * @param browserName
	 * @return driver
	 */
	public WebDriver init_driver(Properties prop) {

		String browserName = null;
		if (System.getProperty("browser") == null) {
			browserName = prop.getProperty("browser");
		} else {
			System.out.println("browser selected from prop file..");
			browserName = System.getProperty("browser");
		}

		System.out.println("Test Scripts Running on --->" + browserName + " browser");

		optionsManager = new OptionsManager(prop);

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteWebDriver(browserName);
			}
			else {
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}

		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteWebDriver(browserName);
			}
			else {
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
				
			}
		}
		else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				
				init_remoteWebDriver(browserName);
			}
			else {
				tlDriver.set(new EdgeDriver());
				
			}
		}
		else if (browserName.equalsIgnoreCase("IE")) {
			WebDriverManager.iedriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteWebDriver(browserName);
			}
			else {
				tlDriver.set(new InternetExplorerDriver());
				
			}
		}
		else if (browserName.equalsIgnoreCase("safari")) {
			WebDriverManager.getInstance(SafariDriver.class).setup();
			tlDriver.set(new SafariDriver());
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		
		return getDriver();

	}

	private void init_remoteWebDriver(String browserName) {
		if (browserName.equalsIgnoreCase("chrome")) {
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		else if (browserName.equalsIgnoreCase("firefox")) {
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getFirefoxOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * this method will take the screenshot
	 */
	public String getScreenshot() {

		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/FailedScreenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;

	}
	
}
