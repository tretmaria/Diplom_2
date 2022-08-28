package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.client.UserClient;
import praktikum.model.User;
import praktikum.util.UserCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class CreateUserTest {
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
        userClient.deleteUser(accessToken);
    }

    @Test
    @Description("Create a user successfully")
    @DisplayName("Create a user successfully")
    public void shouldCreateUserTest() {
        ValidatableResponse response = userClient.createUser(user);
        System.out.println(user);
        boolean isUserCreated = response.extract().path("success");
        System.out.println(isUserCreated);
        int statusCode = response.extract().statusCode();
        System.out.println(statusCode);
        accessToken = response.extract().path("accessToken");
        System.out.println(accessToken);

        assertTrue("Пользователь не создан", isUserCreated);
        assertThat("Неверный код статуса", statusCode, equalTo(200));
    }

    @Test
    @Description("Create an identical user")
    @DisplayName("Create an identical user")
    public void shouldNotCreateIdenticalCourierTest() {
        userClient.createUser(user);
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        int statusCode = userClient.createUser(user).extract().statusCode();
        boolean isCourierIdentical = userClient.createUser(user).extract().path("message").toString().contains("User already exists");

        assertTrue("Создано два одинаковых курьера", isCourierIdentical);
        assertThat("Неверный код статуса", statusCode, equalTo(403));
    }
}
