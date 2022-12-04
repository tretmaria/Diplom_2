# Diplom_2

Description: you need to test the API handles for Stellar Burgers. API documentation is https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf.

Creating a User: 

User should be unique;

To create a user who is already registered;

To create a user and not fill in one of the required fields.

Login a User:

Login an existing user,

Login with an invalid username and password.

Changing user data:

with authorization,

without authorization,

For both situations, you need to check that any field can be changed. For an unauthorized user - also that the system will return an error.

Creating an order:

with authorization,

without authorization,

with ingredients,

without ingredients,

with a wrong hash of ingredients.

Receiving orders of a specific user:

authorized user,

unauthorized user.
