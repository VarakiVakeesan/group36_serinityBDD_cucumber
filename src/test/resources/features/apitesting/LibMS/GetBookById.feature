Feature: Retrieve a book by ID
  As an authenticated user
  I want to retrieve a book by its ID
  So that I can see its details or know if it doesn't exist

  Scenario: Book not found with the given ID
    Given I am logged in as an admin
    When I send a GET request with non existing Book Id
    Then I should receive a 404 response code

  Scenario: Successfully fetch already available book by user
    Given user has logged in
    And Book is already available in Library System
    When I ask for a book with book's ID
    Then I get book as result

  Scenario: Unauthorized access to retrieve books
    Given I am not authenticated
    And a book exists in the database with ID 1 
    When I send a GET request to "/api/books" endpoint
    Then I should receive a 401 response code

  Scenario: Fetch book with Invalid ID format (non-numeric ID)
    Given I am logged in as a user
    When I send a GET request to "/api/books/abc" endpoint
    Then I should receive a 400 response code

  Scenario: Accessing api with valid user credentials
    Given I am logged in as a user
    And a book exists in the database with ID 1
    When I send a GET request to "/api/books/1" endpoint
    Then I should receive a 200 response code
