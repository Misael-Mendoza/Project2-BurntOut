package com.example.gluecode;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.example.page.BlogPage;
import com.example.page.LogIn;
import com.example.page.WelcomePage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.bytebuddy.asm.Advice.This;

public class GettingToTheBlogPageTest {
	
	public WelcomePage wp;
	public LogIn lp;
	public WelcomePage wp2;
	public BlogPage bp;
	public BlogPage bp2;
	String title1;
	
	@Given("the user is at the welcome page")
	public void the_user_is_at_the_welcome_page() throws InterruptedException {
	    TimeUnit.SECONDS.sleep(3);
	    this.wp = new WelcomePage(BurntOutDriverUtility.driver);
	    assertEquals(this.wp.header.getText(), "Welcome to BurntOut!");
	}
	
	@When("a user clicks the sign in button")
	public void a_user_clicks_the_sign_in_button() {
		this.wp.signInButton.click();
	}
	
	@When("the user is at the login page")
	public void the_user_is_at_the_login_page() throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		this.lp = new LogIn(BurntOutDriverUtility.driver);
		assertEquals(this.lp.header.getText(), "Welcome to BurntOut!");
	}
	@When("the user is at the welcome screen")
	public void the_user_is_at_the_welcome_screen() throws InterruptedException {
	    TimeUnit.SECONDS.sleep(3);
	    this.wp2 = new WelcomePage(BurntOutDriverUtility.driver);
	    assertEquals(this.wp2.header.getText(), "Welcome to BurntOut!");
	}
	
	@When("the user clicks the Blog button")
	public void the_user_clicks_the_blog_button() {
		this.wp2.BlogButton.click();
	}
	
	@Then("the user is redirected to the blog page")
	public void the_user_is_redirected_to_the_blog_page() throws InterruptedException {
	   TimeUnit.SECONDS.sleep(3);
	   this.bp = new BlogPage(BurntOutDriverUtility.driver);
	   assertEquals(this.bp.header.getText(), "Blog");
	}
	
	@When("the user is at the blog page")
	public void the_user_is_at_the_blog_page() {
		assertEquals(this.bp.header.getText(), "Blog");
	}
	
	@When("the user clicks the add button")
	public void the_user_clicks_the_add_button() {
		this.bp.addButton.click();
	}
	
	@When("the user enters a {string} and a {string}")
	public void the_user_enters_a_and_a(String string, String string2) {
		this.title1 = string;
		this.bp.enterInfo(string, string2);
	}
	
	@When("the user clicks the post button")
	public void the_user_clicks_the_post_button() {
	    this.bp.postButton.click();
	}
	
	@Then("the user will create a new post")
	public void the_user_will_create_a_new_post() throws InterruptedException {
	    TimeUnit.SECONDS.sleep(3);
	    this.bp2 = new BlogPage(BurntOutDriverUtility.driver);
	    WebElement lastPost = this.bp2.posts.get(0);
	    String title2 = lastPost.findElement(By.id("title")).getText();
	    assertEquals(this.title1, title2);
	}



}
