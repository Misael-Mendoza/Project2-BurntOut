package com.example.gluecode;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class BurntOutDriverUtility {
	public static WebDriver driver;
	public static final String url = "http://localhost:4200/";
	
	@Before
	public void setUp() {
		String filePath = "src/test/resources/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", filePath);
		driver = new ChromeDriver();
		driver.get(url);
	}
	
	@After
	public void tearDown() {
		if(driver != null) {
			driver.quit();
		}
	}
}
