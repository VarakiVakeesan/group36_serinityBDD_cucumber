Feature: Update Book
  As an admin
  I want to update the author name of an existing book
  So that the changes are reflected in the library system

  As an admin
  I want to update the author name of an existing book
  So that the changes are reflected in the library system

  Scenario: Successfully update the book author name
    Given I am logged in as an admin
    And  I send a POST request to "/api/books" with:
     """
      {
        "title": "Test Book Title Existing",
        "author": "Previous Author"
      }
      """
    When I send a PUT request with new author name with:
      """
      {
        "id": 100,
        "title": "Test Book Title Existing",
        "author": "Updated Author"
      }
      """
    Then I should receive a 200 response code

  Scenario: User tries to update the book name
    Given I am logged in as a user
    And a book exists in the database with ID 1
    When I send a PUT request to "/api/books/1" with:
      """
      {
        "id": 1,
        "title": "Updated Book Title",
        "author": "Existing Author"
      }
      """
    Then I should receive a 403 response code

  Scenario: Admin tries to update the book name and Book Author Name
    Given I am logged in as an admin
    And a book exists in the database with ID 1
    When I send a PUT request to "/api/books/1" with:
      """
      {
        "id": 1,
        "title": "Updated Book Title",
        "author": "Updated Author"
      }
      """
    Then I should receive a 200 response code
    
  Scenario: Admin tries to update the book without author details
    Given I am logged in as an admin
    And a book exists in the database with ID 1
    When I send a PUT request to "/api/books/1" with:
    """
    {
      "id": 1,
      "title":"Updated Book Title"
    }
    """
    Then I should receive a 400 response code