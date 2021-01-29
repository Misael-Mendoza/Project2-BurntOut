package com.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
	
	@FindBy(xpath = "//h1")
	public WebElement header;
	
	@FindBy(xpath = "//input[@id='firstName']")
	public WebElement firstName;
	
	@FindBy(xpath = "//input[@id='lastName']")
	public WebElement lastName;
	
	@FindBy(xpath = "//input[@id='email']")
	public WebElement email;
	
	@FindBy(xpath = "//input[@value='Candidate']")
	public WebElement candidateOption;
	
	@FindBy(xpath = "//input[@value='Company']")
	public WebElement companyOption;
	
	@FindBy(xpath = "//input[@placeholder='Enter company (employers only)']")
	public WebElement companyField;
	
	@FindBy(xpath = "//input[@id='username']")
	public WebElement username;
	
	@FindBy(xpath = "//input[@id='password']")
	public WebElement password;
	
	@FindBy(xpath = "//button[@type='submit']")
	public WebElement signUpButton;
	
	public SignUpPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void signUpCandidate(String fName, String lName, String email, String username, String password) {
		this.firstName.sendKeys(fName);
		this.lastName.sendKeys(lName);
		this.email.sendKeys(email);
		this.candidateOption.click();
		this.username.sendKeys(username);
		this.password.sendKeys(password);
		this.signUpButton.click();
	}
	
	public void signUpCompany(String fName, String lName, String email, String company, String username, String password) {
		this.firstName.sendKeys(fName);
		this.lastName.sendKeys(lName);
		this.email.sendKeys(email);
		this.companyOption.click();
		this.companyField.sendKeys(company);
		this.username.sendKeys(username);
		this.password.sendKeys(password);
		this.signUpButton.click();
	}
	
}
