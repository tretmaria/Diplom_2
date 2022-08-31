package praktikum;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.client.UserClient;
import praktikum.model.User;
import praktikum.util.UserCredentials;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UserLoginParameterTest {
    private UserClient userClient;
    private static User user;
    private UserCredentials credentials;
    private String accessToken;
    private int statusCode;
    private String errorMessage;

    public UserLoginParameterTest(UserCredentials credentials, int statusCode, String errorMessage) {
        this.credentials = credentials;
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }
    @Before
    public void setUp(){
        userClient = new UserClient();
        user = User.getRandomUserCredentials();
    }
    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2}")
    public static Object[][] getData() {
        return new Object[][]{
                {UserCredentials.authorizeWithEmail(user), 401, "email or password are incorrect"},
                {UserCredentials.authorizeWithPassword(user), 401, "email or password are incorrect"},
                {UserCredentials.authorizeWithRandomCredentials(user), 401, "email or password are incorrect"}
        };
    }
    @Test
    @DisplayName("Login a user")
    @Description("Login a user with invalid data")
    public void loginWithInvalidDadaTest() {
        userClient.createUser(user);
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        int actualStatusCode = new UserClient().loginUser(credentials).extract().statusCode();
        String actualErrorMessage = new UserClient().loginUser(credentials).extract().path("message");

        assertEquals("Неверный код статуса", statusCode, actualStatusCode);
        assertEquals("Неверное сообщение об ошибке", errorMessage, actualErrorMessage);
    }
}
