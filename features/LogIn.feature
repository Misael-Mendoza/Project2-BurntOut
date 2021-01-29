Feature: BurntOut Login 
	As a User, I wish to login to BurntOut using proper credentials
	Scenario Outline: Logging into BurntOut 
		Given a user is at the welcome page of BurntOut 
		When a user inputs clicks the Sign in button
		Then the user is redirected to the login page
		When the user is at the Log in page 
		And  the user enters their "<username>" and "<password>"
		Then the user is redirected to the welcome screen
	
	Examples: 
		|username|password|
		|darien	 |password|
