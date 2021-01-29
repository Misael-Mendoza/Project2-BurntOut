package com.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WelcomePage {
	
	@FindBy(xpath = "//h1")
	public WebElement header; 
	
	@FindBy(id="create-posting")
	public WebElement createPostingButton;
	
	@FindBy(xpath = "//*[@id ='sign-in-button']")
	public WebElement signInButton;
	
	@FindBy(xpath = "//*[@id = 'sign-up-button']")
	public WebElement signUpButton;

	@FindBy(xpath = "//a[@href ='/blog']")
	public WebElement BlogButton;

	@FindBy(xpath = "//*[@id ='profile-button']")
	public WebElement profileButton;
	
	public WelcomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	
}
