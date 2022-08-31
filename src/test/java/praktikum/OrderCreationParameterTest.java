package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.client.IngredientsClient;
import praktikum.client.OrdersClient;
import praktikum.client.UserClient;
import praktikum.model.Ingredients;
import praktikum.model.Orders;
import praktikum.model.User;
import praktikum.util.UserCredentials;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class OrderCreationParameterTest {
    private UserClient userClient;
    private User user;
    private String accessToken;
    private Ingredients orderIngredients;
    private int expectedStatusCode;

    @Before
    public void setUp() {
        user = User.getRandomUserCredentials();
        userClient = new UserClient();
    }

    public OrderCreationParameterTest(Ingredients orderIngredients, int expectedStatusCode) {
        this.orderIngredients = orderIngredients;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1}")
    public static Object[][] getData() {
        return new Object[][]{
                {new Ingredients(""), 400},
                {new Ingredients("12345"), 500},
        };
    }

    @Test
    @DisplayName("Create an order with authorization")
    @Description("Create an order with authorization")
    public void createOrderWithAuthorizationTest() {
        userClient.createUser(user);
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        accessToken = accessToken.replaceAll("Bearer ", "");
        ValidatableResponse response = new OrdersClient().createOrder(orderIngredients, accessToken);
        int actualStatusCode = response.extract().statusCode();

        assertThat("Неверный код статуса", actualStatusCode, equalTo(expectedStatusCode));
    }
}
