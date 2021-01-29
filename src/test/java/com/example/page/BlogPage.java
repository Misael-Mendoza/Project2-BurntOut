package com.example.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlogPage {
	
	@FindBy(xpath = "//h3[@id='pageTitle']")
	public WebElement header;
	
	@FindBy(xpath = "//*[@id='post-blog']")
	public WebElement addButton;
	
	@FindBy(xpath = "//*[@id='title-field']")
	public WebElement titleField;
	
	@FindBy(xpath = "//*[@id='blog-message']")
	public WebElement messageField;
	
	@FindBy(xpath = "//*[@id='post-btn']")
	public WebElement postButton;
	
	@FindBy(xpath = "//*[@class='container-post']")
	public List<WebElement> posts;
	
	public BlogPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	
	public void enterInfo(String title, String message) {
		this.titleField.sendKeys(title);
		this.messageField.sendKeys(message);
		this.postButton.click();
	}
	
	public int blogPosts() {
		return posts.size();
	}
	
}
