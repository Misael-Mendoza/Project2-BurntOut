package com.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewSelfPostings {

	@FindBy(xpath="//table/tbody/tr[last()]/td[2]")
	public WebElement cell;
	
	public ViewSelfPostings(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public String checkLastTitleAdded() {
		return cell.getText();
	}

}
