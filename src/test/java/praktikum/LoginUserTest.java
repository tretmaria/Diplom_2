package praktikum;

import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import praktikum.client.UserClient;
import praktikum.model.User;
import praktikum.util.UserCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginUserTest {
    private User user;
    private UserClient userClient;
    private String accessToken;
    @Before
    public void setUp() {
        user = User.getRandomUserCredentials();
        userClient = new UserClient();
    }
    @After
    public void tearDown() {
        userClient.delete(accessToken);
    }
    @Test
    @Description("Login a user successfully")
    @DisplayName("Login a user successfully")
    public void courierLoginTest() {
        userClient.create(user);
        int statusCode = userClient.login(UserCredentials.from(user)).extract().statusCode();
        accessToken = userClient.login(UserCredentials.from(user)).extract().path("accessToken");

        assertThat("Неверный код статуса", statusCode, equalTo(200));
        assertThat("Неверный accessToken", accessToken, notNullValue());
    }
}
