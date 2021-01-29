Feature: Getting to the blog page
	As a user, I wish to be able to access the Blog Page
	Scenario Outline:
		Given the user is at the welcome page 
		When a user clicks the sign in button 
		Then the user is redirected to the login page
		When the user is at the login page
		And the user enters their "<username>" and "<password>"
		Then the user is redirected to the welcome screen
		When the user is at the welcome screen 
		And the user clicks the Blog button 
		Then the user is redirected to the blog page
		When the user is at the blog page 
		And the user clicks the add button
		And the user enters a "<blogTitle>" and a "<message>"
		And the user clicks the post button 
		Then the user will create a new post
	
	Examples:
		|username|password|blogTitle|message|
		|darien  |password|testC    |testC  |