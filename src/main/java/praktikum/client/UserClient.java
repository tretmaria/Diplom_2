package praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.model.User;
import praktikum.util.UserCredentials;

import static io.restassured.RestAssured.given;

public class UserClient extends RestAssuredClient{
    private static final String USER_PATH = "api/auth/";

    @Step("Create a user")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH + "register")
                .then();
    }
    @Step("Login a user")
    public ValidatableResponse login(UserCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(USER_PATH + "login")
                .then();
    }
    @Step("Change user data")
    public ValidatableResponse changeUserData(UserCredentials credentials, String accessToken){
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .and()
                .body(credentials)
                .when()
                .patch(USER_PATH + "user")
                .then();
    }
    @Step("Logout a user")
    public ValidatableResponse logout(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .post(USER_PATH + "logout")
                .then();
    }
    @Step("Delete user")
    public ValidatableResponse delete(String accessToken){
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(USER_PATH + "user")
                .then();
    }
}
