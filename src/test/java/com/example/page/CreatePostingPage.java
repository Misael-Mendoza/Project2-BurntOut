package com.example.page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreatePostingPage {

	@FindBy(id="title_textbox")
	public WebElement title;
	
	@FindBy(id="location_select")
	public WebElement location;
	
	@FindBy(id="industry_select")
	public WebElement industry;
	
	@FindBy(id="description-textarea")
	public WebElement description;
	
	@FindBy(id="submitButton")
	public WebElement submitButton;
	
	public void setTitle(String title) {
		this.title.sendKeys(title);
	}
	
	public void setLocation(String location) {
		this.location.sendKeys(location);
	}

	public void setIndustry(String industry) {
		this.industry.sendKeys(industry);
	}

	public void setDescription(String description) {
		this.description.sendKeys(description);
	}

	public void clickSubmit() {
		this.submitButton.click();
	}	
	
	public CreatePostingPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
}

