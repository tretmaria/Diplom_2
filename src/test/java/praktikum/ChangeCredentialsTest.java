package praktikum;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import praktikum.client.UserClient;
import praktikum.model.User;
import praktikum.util.UserCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ChangeCredentialsTest {
    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        user = User.getRandomUserCredentials();
        System.out.println(user);
        userClient = new UserClient();
        System.out.println(userClient);
        ValidatableResponse response = userClient.createUser(user);
        System.out.println(response);
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @Description("Change credentials as an authorized user")
    @DisplayName("Change credentials as an authorized user")
    public void changeCredentialsWithAuthorizationTest() {
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        System.out.println(accessToken);
        accessToken = accessToken.replaceAll("Bearer ", "");
        System.out.println(accessToken);
        ValidatableResponse response = userClient.changeUserData(UserCredentials.changeCredentials(), accessToken);
        System.out.println(UserCredentials.changeCredentials());
        int statusCode = response.extract().statusCode();
        System.out.println(statusCode);
        boolean isChangedSuccessfully = response.extract().path("success");
        System.out.println(isChangedSuccessfully);

        assertThat("Неверный код статуса", statusCode, equalTo(200));
        assertTrue("Данные не изменены", isChangedSuccessfully);
    }

    @Test
    @Description("Change credentials without authorization")
    @DisplayName("Change credentials without authorization")
    public void changeCredentialsWithoutAuthorizationTest() {
        ValidatableResponse response = userClient.changeUserData(UserCredentials.changeCredentials(), "");
        int statusCode = response.extract().statusCode();
        System.out.println(statusCode);
        String errorMessage = response.extract().path("message");
        System.out.println(errorMessage);

        assertThat("Неверный код статуса", statusCode, equalTo(401));
        assertEquals("Неверное сообщение об ошибке", errorMessage, ("You should be authorised"));
    }
}
