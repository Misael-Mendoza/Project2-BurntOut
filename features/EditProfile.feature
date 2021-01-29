Feature: BurntOut Profile Edit 
	As a User, I wish to edit my profile to a different username, and then back to what
	it was before.
	Scenario Outline: Editing user profile 
		Given a user is at the welcome page of BurntOut 
		When a user inputs clicks the Sign in button
		Then the user is redirected to the login page
		When the user is at the Log in page 
		And  the user enters their "<username>" and "<password>"
		Then the user is redirected to the welcome screen
		When the user is at the welcome screen
		Then the user clicks the profile button
		And the user is redirected to their profile page
		When the user is at their profile
		Then the user clicks the edit button
		And the user's profile can be edited
		When the user enters a "<newUsername>"
		Then the user submits the changes
		And the user's changes are submitted
		Then the user can see the reflected changes
		And the user can change them back
		When the user clicks the edit buttons again
		Then the user re-enters their "<oldUsername>"
		And the user user submits the information again
		When the user submits the old info
		And the user has the same info as when they logged in
	
	Examples: 
		|username      |password |newUsername |oldUsername   |
		|johnjacobelli |password |johnjacob   |johnjacobelli |