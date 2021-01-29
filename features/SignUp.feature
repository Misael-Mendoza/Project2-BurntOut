Feature: BurntOut Sign Up
	As a non-User, I wish to create an account
	Scenario Outline: Sign Up for BurntOut
		Given a nonuser is at the welcome page of BurntOut
		When a nonuser clicks the Sign Up button
		Then the nonuser is redirected to the Sign Up page
		When the nonuser is at the Sign Up page
		And the nonuser enters "<firstName>" and "<lastName>" and "<email>" and "<username>" and "<password>"
		Then the user is redirected to the welcome page
		
	Examples:
		|firstName|lastName|email         |username|password|
		|Ryan     |Curley  |r@c.com|ryanc3po|temppass|