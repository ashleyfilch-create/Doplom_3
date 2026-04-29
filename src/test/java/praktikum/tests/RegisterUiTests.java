package praktikum.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseTestUi;
import praktikum.constants.ApiConstants;
import praktikum.models.RegisterUserRequest;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegisterPage;

import static io.restassured.RestAssured.given;

@Epic("UI Tests")
@Feature("Registration")
public class RegisterUiTests extends BaseTestUi {

    private String email;
    private String password;
    private String accessToken;

    @Before
    public void setUp() {
        super.setUp();

        email = "user_" + System.currentTimeMillis() + "@test.com";
        password = "123456";

        RegisterUserRequest request =
                new RegisterUserRequest(email, password, "testUser");

        Response response =
                given()
                        .header(ApiConstants.CONTENT_TYPE, ApiConstants.APPLICATION_JSON)
                        .body(request)
                        .when()
                        .post(ApiConstants.BASE_URL + ApiConstants.REGISTER);

        accessToken = response.jsonPath().getString("accessToken");
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            given()
                    .header(ApiConstants.AUTH, accessToken)
                    .when()
                    .delete(ApiConstants.BASE_URL + ApiConstants.USER);
        }

        super.tearDown();
    }

    @Test
    @Story("Successful registration")
    public void registerSuccess() {

        MainPage main = new MainPage(driver);
        main.clickLoginButton();

        LoginPage login = new LoginPage(driver);
        login.goToRegister();

        RegisterPage register = new RegisterPage(driver);
        register.register(email, password);
    }

    @Test
    @Story("Invalid password registration")
    public void registerInvalidPassword() {

        MainPage main = new MainPage(driver);
        main.clickLoginButton();

        LoginPage login = new LoginPage(driver);
        login.goToRegister();

        RegisterPage register = new RegisterPage(driver);
        register.register(email, "123");
    }
}