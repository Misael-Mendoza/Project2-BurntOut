package com.example.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CandidatePage {
	WebDriver driver;
	
	@FindBy(tagName = "h1")
	WebElement title;
	
	@FindBy(tagName = "p")
	WebElement intro;
	
	@FindBy(tagName = "h1")
	WebElement header1;
	
	WebElement burntOutLink = driver.findElement(By.className("navbar-brand"));
	
	@FindBy(linkText = "Home")
	WebElement homeLink;
	
	@FindBy(linkText = "Blog")
	WebElement blogLink;
	
	@FindBy(linkText = "View Jobs")
	WebElement viewJobsLink;
	
	@FindBy(xpath = "//button [@id = 'profile-button']")
	WebElement profileButton;
	
	@FindBy(xpath = "//button[text() = 'Sign Out']")
	WebElement signOutButton;
	
	public CandidatePage() {
		PageFactory.initElements(driver, this);
	}
	
	public void clickBurntOut() {
		burntOutLink.click();
	}
	
	
}
