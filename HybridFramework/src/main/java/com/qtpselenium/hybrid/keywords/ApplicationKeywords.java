package com.qtpselenium.hybrid.keywords;

import java.io.FileInputStream;
import java.util.Properties;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ApplicationKeywords extends ValidationKeywords{
	
	public ApplicationKeywords(ExtentTest t) {
		test=t;
		// initialze the properties file
		try {
		    prop  = new Properties();
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties");
			prop.load(fs);
			//System.out.println(prop.getProperty("url"));
		}catch(Exception e) {
			// control
		}
	}
	
	public void validateLogin(String expectedResult) {
		test.log(Status.INFO,"Validating Login");
		String actualResult="";
		if(isElementPresent("portfolioDropdown_id"))
			actualResult="LoginSuccess";
		else
			actualResult="LoginFailure";
	
		
		if(!actualResult.equals(expectedResult))
			reportFailure("Got actual Result - "+actualResult+". Expected was "+expectedResult );
		else
			test.log(Status.INFO, "Validation of Login test is Success");
	}
	public void defaultLogin() {
		test.log(Status.INFO, "Logging in with default ID and password");
		login(prop.getProperty("defaultUsername"),prop.getProperty("defaultPassword"));
		
	}
	public void login(String username, String password) {
		test.log(Status.INFO, "Logging in with "+username+" / "+password);
		validateTitle("loginPageTitle");
		validateElementPresent("email_id");
		type("email_id",username);
		click("emailSubmit_xpath");
		type("password_id",password);
		click("passwordSubmit_xpath");
		acceptAlertIfPresent();

		
	}
}
