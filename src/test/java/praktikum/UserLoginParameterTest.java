package praktikum;

import io.qameta.allure.Description;
import org.junit.After;
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
    private UserClient userClient = new UserClient();
    private static User user = User.getRandomUserCredentials();
    private UserCredentials credentials;
    private String accessToken;
    private int statusCode;
    private String errorMessage;

    public UserLoginParameterTest(UserCredentials credentials, int statusCode, String errorMessage) {
        this.credentials = credentials;
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
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
        System.out.println(user);
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        System.out.println(accessToken);
        int actualStatusCode = new UserClient().loginUser(credentials).extract().statusCode();
        System.out.println(actualStatusCode);
        String actualErrorMessage = new UserClient().loginUser(credentials).extract().path("message");
        System.out.println(actualErrorMessage);

        assertEquals("Неверный код статуса", statusCode, actualStatusCode);
        assertEquals("Неверное сообщение об ошибке", errorMessage, actualErrorMessage);
    }
}
