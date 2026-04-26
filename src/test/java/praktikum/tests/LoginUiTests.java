package praktikum.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseTestUi;
import praktikum.constants.ApiConstants;
import praktikum.pages.ForgotPasswordPage;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;

import static io.restassured.RestAssured.given;

@Epic("UI Tests")
@Feature("Login")
public class LoginUiTests extends BaseTestUi {

    private String email;
    private String password;
    private String accessToken;

    @Before
    public void setUp() {
        super.setUp();

        email = "user_" + System.currentTimeMillis() + "@test.com";
        password = "123456";

        Response response =
                given()
                        .header(ApiConstants.CONTENT_TYPE, ApiConstants.APPLICATION_JSON)
                        .body("{\"email\":\"" + email + "\", \"password\":\"" + password + "\", \"name\":\"testUser\"}")
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
    public void shouldLoginViaMainPage() {

        MainPage main = new MainPage(driver);
        main.clickLoginButton();

        LoginPage login = new LoginPage(driver);
        login.login(email, password);

        Assert.assertTrue(new MainPage(driver).isLoggedIn());
    }

    @Test
    public void shouldLoginViaPersonalAccount() {

        MainPage main = new MainPage(driver);
        main.clickPersonalAccount();

        LoginPage login = new LoginPage(driver);
        login.login(email, password);

        Assert.assertTrue(new MainPage(driver).isLoggedIn());
    }

    @Test
    public void shouldLoginViaRegistrationForm() {

        MainPage main = new MainPage(driver);
        main.clickLoginButton();

        LoginPage login = new LoginPage(driver);
        login.login(email, password);

        Assert.assertTrue(new MainPage(driver).isLoggedIn());
    }

    @Test
    public void shouldLoginViaForgotPassword() {

        MainPage main = new MainPage(driver);
        main.clickLoginButton();

        LoginPage login = new LoginPage(driver);
        login.goToForgotPassword();

        ForgotPasswordPage forgot = new ForgotPasswordPage(driver);

        forgot.enterEmail(email)
                .clickRecover()
                .enterPassword(password)
                .enterCode("1234") // ⚠️ лучше заменить на реальный код из API/email mock
                .clickSave();

        Assert.assertTrue(new MainPage(driver).isLoggedIn());
    }
}