package com.example.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CandidatePage {
	
	@FindBy(tagName = "h1")
	public WebElement title;
	
	@FindBy(tagName = "p")
	public WebElement intro;
	
	@FindBy(tagName = "h1")
	public WebElement header1;
	
	@FindBy(className = "navbar-brand")
	public WebElement burntOutLink;
	
	@FindBy(linkText = "Home")
	public WebElement homeLink;
	
	@FindBy(linkText = "Blog")
	public WebElement blogLink;
	
	@FindBy(linkText = "View Jobs")
	public WebElement viewJobsLink;
	
	@FindBy(xpath = "//button [@id = 'profile-button']")
	public WebElement profileButton;
	
	@FindBy(xpath = "//button[text() = 'Sign Out']")
	public WebElement signOutButton;
	
	public CandidatePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void clickBurntOut() {
		burntOutLink.click();
	}
	
	public void clickHome() {
		homeLink.click();
	}
	
	public void clickBlogLink() {
		blogLink.click();
	}
	
	public void clickViewJobsLink() {
		viewJobsLink.click();
	}
	
	public void clickProfileButtonLink() {
		profileButton.click();
	}
	
	public void clickSignOutButtonLink() {
		signOutButton.click();
	}
}
