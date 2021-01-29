package com.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogIn {
	
	@FindBy(xpath = "//h1")
	public WebElement header; 
	
	@FindBy(xpath ="//input[@placeholder='Username']")
	public WebElement username;
	
	@FindBy(xpath ="//input[@placeholder='Password']")
	public WebElement password;
	
	@FindBy(xpath ="//button[@id='loginPost']")
	public WebElement logInButton;
	
	@FindBy(linkText = "Forgot Password?")
	public WebElement forgotPasswordLink;
	
	public LogIn(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void logIn(String username , String password) {
		this.username.sendKeys(username);
		this.password.sendKeys(password);
		this.logInButton.click();
	}
	
	public void goToForgotPassword() {
		this.forgotPasswordLink.click();
	}
}
