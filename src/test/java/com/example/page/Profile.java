package com.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Profile {
	
	@FindBy(xpath = "//h1")
	public WebElement header; 
	
	@FindBy(xpath ="//input[@id='username']")
	public WebElement usernameInput;
	
	@FindBy(xpath ="//td[@id='usernameHeader']")
	public WebElement username;
	
	@FindBy(xpath ="//button[@class='right btn btn-secondary']")
	public WebElement editButton;
	
	@FindBy(xpath ="//button[@class='right btn btn-success']")
	public WebElement acceptButton;
	
	public Profile(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void editButtonClick() {
		this.editButton.click();
	}
	
	public void inputUsername(String username) {
		this.usernameInput.clear();
		this.usernameInput.sendKeys(username);
	}
	
	public void approveChangesButtonClick() {
		this.acceptButton.click();
	}

}
