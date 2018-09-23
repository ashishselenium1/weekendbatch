package com.qtpselenium.hybrid.keywords;

import com.aventstack.extentreports.Status;

public class ValidationKeywords extends GenericKeywords{
	public  ValidationKeywords() {
		
	}
	
	public void validateElementPresent(String locatorKey) {
		test.log(Status.INFO, "Checking the present of the element "+ locatorKey);
		boolean present = isElementPresent(locatorKey);
		if(!present)// not present
			reportFailure("Element not found "+ locatorKey);
		else
			test.log(Status.INFO, "Element Present ");
	}
	
	public void validateTitle(String expectedTitleKey) {
		
	}
	public void validateText(String locatorKey,String expectedTextKey) {
		
	}
}
