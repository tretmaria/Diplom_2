package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import praktikum.client.OrdersClient;
import praktikum.client.UserClient;
import praktikum.model.User;
import praktikum.util.UserCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetOrderListTest {
    private OrdersClient ordersClient;
    private User user;
    private UserClient userClient;

    private String accessToken;

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();

    }

    @Test
    @DisplayName("Get a list of orders with authorization")
    @Description("Get a list of orders with authorization")
    public void getOrdersListWithAuthorizationTest() {
        user = User.getRandomUserCredentials();
        userClient = new UserClient();
        userClient.createUser(user);
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        ValidatableResponse response = ordersClient.getOrders(accessToken);
        System.out.println(response);
        int statusCode = response.extract().statusCode();
        System.out.println(statusCode);
        boolean isOrderCreated = response.extract().path("success");

        assertEquals("Неверный код статуса", 200, statusCode);
        assertTrue("Нет заказов", isOrderCreated);
    }

    @Test
    @DisplayName("Get a list of orders without authorization")
    @Description("Get a list of orders without authorization")
    public void getOrdersListWithoutAuthorizationTest() {
        ValidatableResponse response = ordersClient.getOrders("");
        System.out.println(response);
        int statusCode = response.extract().statusCode();
        System.out.println(statusCode);
        String errorMessage = response.extract().path("message");
        System.out.println(errorMessage);

        assertThat("Неверный код статуса", statusCode, equalTo(401));
        assertTrue("Неверное сообщение об ошибке", errorMessage.equals("You should be authorised"));
    }
}
