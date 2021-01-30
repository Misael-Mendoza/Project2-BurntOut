package com.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ApplicationPage {
	
	public static final String title = "Submit Application";
	
	@FindBy(id="fN")
	public WebElement firstName;
	
	@FindBy(id="lN")
	public WebElement lastName;
	
	@FindBy(id="posting_id")
	public WebElement postingId;
	
	@FindBy(xpath = "//input[@type='file']")
	public WebElement chooseFile;
	
	@FindBy(id="submit")
	public WebElement submitButton;
	
	@FindBy(linkText = "View Applications")
	public WebElement viewApplicationsLink;
	
	public ApplicationPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void uploadResume() {
		this.chooseFile.sendKeys("C://Users/Olyun/Resume.pdf");
	}
	
	public void clickSubmitButton() {
		this.submitButton.click();
	}
	
	public void clickViewApplicationsLink() {
		viewApplicationsLink.click();
	}

}
