package com.example.gluecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import com.example.page.Profile;
import com.example.page.WelcomePage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProfilePageTests {
	
	public WelcomePage wp;
	public Profile prof; 
	
	@When("the user is at their welcome screen")
	public void the_user_is_at_their_welcome_screen() {
		this.wp = new WelcomePage(BurntOutDriverUtility.driver);
	    assertEquals("Welcome to BurntOut!", this.wp.header.getText());
	}
	@Then("the user clicks the profile button")
	public void the_user_clicks_the_profile_button() {
	    this.wp.clickProfileButton();
	}
	@Then("the user is redirected to their profile page")
	public void the_user_is_redirected_to_their_profile_page() {
		assertEquals("http://localhost:4200/profile/johnjacobelli", BurntOutDriverUtility.driver.getCurrentUrl());
	}
	@When("the user is at their profile")
	public void the_user_is_at_their_profile() {
		this.prof = new Profile(BurntOutDriverUtility.driver);
	    assertEquals("Profile", this.prof.header.getText());
	}
	@Then("the user clicks the edit button")
	public void the_user_clicks_the_edit_button() {
		this.prof.editButtonClick();
	}
	@Then("the user's profile can be edited")
	public void the_user_s_profile_can_be_edited() {
		assertEquals(true, this.prof.acceptButton.isDisplayed());
	}
	@When("the user enters a {string}")
	public void the_user_enters_a(String string) {
	    this.prof.inputUsername(string);
	}
	@Then("the user submits the changes")
	public void the_user_submits_the_changes() {
		this.prof.approveChangesButtonClick();
	}
	@When("the user's changes are submitted")
	public void the_user_s_changes_are_submitted() throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		assertEquals("http://localhost:4200/profile/johnjacob", BurntOutDriverUtility.driver.getCurrentUrl());
	}
	@Then("the user can see the reflected changes")
	public void the_user_can_see_the_reflected_changes() {
		assertEquals("johnjacob", this.prof.username.getText());
	}
	@Then("the user can change them back")
	public void the_user_can_change_them_back() {
		assertEquals(true, this.prof.editButton.isDisplayed());
	}
	@When("the user clicks the edit buttons again")
	public void the_user_clicks_the_edit_buttons_again() {
		this.prof.editButtonClick();
	}
	@Then("the user re-enters their {string}")
	public void the_user_re_enters_their(String string) {
		this.prof.inputUsername(string);
	}
	@Then("the user user submits the information again")
	public void the_user_user_submits_the_information_again() {
		this.prof.approveChangesButtonClick();
	}
	@When("the user submits the old info")
	public void the_user_submits_the_old_info() throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		assertEquals("http://localhost:4200/profile/johnjacobelli", BurntOutDriverUtility.driver.getCurrentUrl());
	}
	@Then("the user has the same info as when they logged in")
	public void the_user_has_the_same_info_as_when_they_logged_in() {
		assertEquals("johnjacobelli", this.prof.username.getText());
	}

}
