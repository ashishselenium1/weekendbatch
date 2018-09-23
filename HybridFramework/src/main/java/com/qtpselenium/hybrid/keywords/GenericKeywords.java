package com.qtpselenium.hybrid.keywords;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.util.ExtentManager;

public class GenericKeywords {
	public WebDriver driver;
	public Properties prop;
	public ExtentTest test;


	
	
	public void openBrowser(String bName) {
		test.log(Status.INFO, "Opening Browser "+bName);
		
		if(prop.getProperty("gridRun").equals("N")) {// run on local
			if(bName.equals("Mozilla"))
				driver = new FirefoxDriver();
			if(bName.equals("Chrome"))
				driver = new ChromeDriver();
			if(bName.equals("IE"))
				driver = new InternetExplorerDriver();
		}else {// run on grid
			
			DesiredCapabilities cap=null;
			if(bName.equals("Mozilla")){
				cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setJavascriptEnabled(true);
				cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				
			}else if(bName.equals("Chrome")){
				 cap = DesiredCapabilities.chrome();
				 cap.setBrowserName("chrome");
				 cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			}
			
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		}
	}
	
	public void navigate(String urlKey) {
		test.log(Status.INFO, "Navigating to the site "+prop.getProperty(urlKey));
		driver.get(prop.getProperty(urlKey));
	}
	
	public void type(String locatorKey,String data) {
		test.log(Status.INFO, "Typing in the text field "+ prop.getProperty(locatorKey)+". Value - "+ data);
		getObject(locatorKey).sendKeys(data);
	}
	
	public void click(String locatorKey) {
		test.log(Status.INFO, "Clicking on a element "+prop.getProperty(locatorKey));
		getObject(locatorKey).click();
	}
	
	public void acceptAlertIfPresent() {
		test.log(Status.INFO, "Accepting alert"); 
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			test.log(Status.INFO, "Alert accepted Successfully"); 
		}catch(Exception e) {
			test.log(Status.INFO, "Alert Not found"); 
		}
	}
	
	public void clear(String locatorKey) {
		test.log(Status.INFO,"Clearing  "+locatorKey);
		getObject(locatorKey).clear();
		
	}
	
	public void select(String locatorKey, String data) {
		test.log(Status.INFO,"Selecting from dropdown "+locatorKey+" value "+ locatorKey);

		Select s = new Select(getObject(locatorKey));
		s.selectByVisibleText(data);
		
	}
	
	
	public boolean isElementPresent(String locatorKey) {
		test.log(Status.INFO, "Finding the element presence "+locatorKey);
		int size=0;
		
		if(locatorKey.endsWith("_xpath"))
			size = driver.findElements(By.xpath(prop.getProperty(locatorKey))).size();
		else if(locatorKey.endsWith("_id"))
			size = driver.findElements(By.id(prop.getProperty(locatorKey))).size();
		else if(locatorKey.endsWith("_name"))
			size = driver.findElements(By.name(prop.getProperty(locatorKey))).size();
		
		if(size==0)
			return false;// not found
		else
			return true;//found
	}
	
	// central function to extract element
	public WebElement getObject(String locatorKey) {
		
		WebElement e= null;
		try {
			if(locatorKey.endsWith("_xpath"))
				e = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_id"))
				e = driver.findElement(By.id(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_name"))
				e = driver.findElement(By.name(prop.getProperty(locatorKey)));
			
			WebDriverWait wait  = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOf(e));
			
		}catch(Exception ex) {
			// element not found
			// report the error
			reportFailure("Element not found "+locatorKey );
		}
		
		return e;
		
	}
	
	public void reportFailure(String failureMessage) {
		// report failure in extent reports
		test.log(Status.FAIL, failureMessage);
		// screenshot of the page and embed in reports
		takeScreenShot();
		// fail in testng
		Assert.fail(failureMessage);
	}
	// take the screenshot and embed in reports
	/* 1- Decide the file Name
	 * 2- Take screenshot and store the file in the hard drive
	 * 3- Embed in the reports
	 */
	public void takeScreenShot(){
		// fileName of the screenshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_")+".png";
		// take screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			// get the dynamic folder name
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+screenshotFile));
			//put screenshot file in reports
			test.log(Status.INFO,"Screenshot-> "+ test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
