package com.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewAllJobsPage {
	
	public WebDriver driver;
	
	@FindBy(tagName = "h1")
	public WebElement title;
	
	@FindBy(xpath = "//input[@id = 'input-field']")
	public WebElement input;
	
	@FindBy(tagName = "table")
	public WebElement table;
	
	@FindBy(xpath = "//button[@id='183']")
	public WebElement applyButton;
	
	/*@FindBy(xpath = "//table/tbody/tr[1]/td[4]\"")
	public WebElement companyName;*/
	
	public ViewAllJobsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void searchForJobByTitle(String title) {
		this.input.sendKeys(title);
	}
	
	public void clearInput() {
		this.input.clear();
	}
	
	public void searchForJobByLocation(String location) {
		this.input.sendKeys(location);
	}
	
	public void searchForJobByCompany(String company) {
		this.input.sendKeys(company);
	}
	
	public void searchForJobByIndustry(String industry) {
		this.input.sendKeys(industry);
	}
	
	public void clickApplyButton() {
		this.applyButton.click();
	}
}
