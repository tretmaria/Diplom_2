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

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UserCreationParameterTest {
    private UserClient userClient;
    private User user;
    private String accessToken;
    private int statusCode;
    private String errorMessage;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }
    public UserCreationParameterTest(User user, int statusCode, String errorMessage) {
        this.user = user;
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2}")
    public static Object[][] getData() {
        return new Object[][]{
                {User.getUserWithEmailOnly(), 403, "Email, password and name are required fields"},
                {User.getUserWithPasswordOnly(), 403, "Email, password and name are required fields"},
                {User.getUserWithNameOnly(), 403, "Email, password and name are required fields"},
        };
    }

    @Test
    @DisplayName("Create a user")
    @Description("Create a user with empty fields")
    public void checkUserDataTest() {
        ValidatableResponse response = userClient.createUser(user);
        accessToken = response.extract().path("accessToken");
        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");

        assertEquals("Неверный код статуса", statusCode, actualStatusCode);
        assertEquals("Неверное сообщение об ошибке", errorMessage, actualMessage);
    }
}
