package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.client.UserClient;
import praktikum.model.User;
import praktikum.util.UserCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ChangeCredentialsParameterTest {
    private UserClient userClient;
    private User user;
    private UserCredentials credentials;
    private String accessToken;
    private int expectedStatusCode;

    @Before
    public void setUp() {

        user = User.getRandomUserCredentials();
        System.out.println(user);
        userClient = new UserClient();
        System.out.println(userClient);
        System.out.println(userClient.createUser(user));
    }

    public ChangeCredentialsParameterTest(UserCredentials credentials, int expectedStatusCode) {
        this.credentials = credentials;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1}")
    public static Object[][] getData() {
        return new Object[][]{
                {UserCredentials.changeEmail(new User()), 200},
                {UserCredentials.changePassword(new User()), 200},
                {UserCredentials.changeName(new User()), 200},
        };
    }

    @Test
    public void changeCredentialsWithAuthorizationTest() {
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        System.out.println(accessToken);
        accessToken = accessToken.replaceAll("Bearer ", "");
        System.out.println(accessToken);
        ValidatableResponse response = userClient.changeUserData(UserCredentials.changeCredentials(), accessToken);
        System.out.println(UserCredentials.changeCredentials());
        int actualStatusCode = response.extract().statusCode();
        System.out.println(expectedStatusCode);
        boolean isChangedSuccessfully = response.extract().path("success");
        System.out.println(isChangedSuccessfully);
        assertThat("Неверный код статуса", actualStatusCode, equalTo(expectedStatusCode));
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

