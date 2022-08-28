package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import praktikum.client.IngredientsClient;
import praktikum.client.OrdersClient;
import praktikum.client.UserClient;
import praktikum.model.Ingredients;
import praktikum.model.User;
import praktikum.util.UserCredentials;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class CreateOrderTest {

    List<String> ingredients = new ArrayList<>();
    private IngredientsClient ingredientsClient;
    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        user = User.getRandomUserCredentials();
        userClient = new UserClient();
    }

    @Test
    @Description("Create an order when authorized")
    @DisplayName("Create an order when authorized")
    public void createOrderWithAuthorizationTest() {
        userClient.createUser(user);
        System.out.println(user);
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        accessToken = accessToken.replaceAll("Bearer ", "");
        System.out.println(accessToken);
        ingredients = new IngredientsClient().getIngredients().extract().path("data._id");
        System.out.println(ingredients);
        Ingredients ingredient = new Ingredients(ingredients.get(3));
        System.out.println(ingredient);
        ValidatableResponse response = new OrdersClient().createOrder(ingredient, accessToken);
        System.out.println(response);
        int statusCode = response.extract().statusCode();
        System.out.println(statusCode);
        boolean isOrderCreated = response.extract().path("success");

        assertThat("Неверный код статуса", statusCode, equalTo(200));
        assertTrue("Заказ не создан", isOrderCreated);
    }

    @Test
    @Description("Create an order without authorization")
    @DisplayName("Create an order without authorization")
    public void createOrderWithoutAuthorizationTest() {
        ingredients = new IngredientsClient().getIngredients().extract().path("data._id");
        System.out.println(ingredients);
        Ingredients ingredient = new Ingredients(ingredients.get(3));
        System.out.println(ingredient);
        ValidatableResponse response = new OrdersClient().createOrder(ingredient, "");
        System.out.println(response);
        int statusCode = response.extract().statusCode();
        System.out.println(statusCode);
        boolean isOrderCreated = response.extract().path("success");

        assertThat("Неверный код статуса", statusCode, equalTo(200));
        assertTrue("Заказ не создан", isOrderCreated);
    }

}
