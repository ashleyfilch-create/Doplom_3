package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseTestUi;
import praktikum.constants.ApiConstants;
import praktikum.models.RegisterUserRequest;
import praktikum.pages.ForgotPasswordPage;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegisterPage;

import static io.restassured.RestAssured.given;

@Feature("Авторизация пользователя")
public class LoginUiTests extends BaseTestUi {

    private String email;
    private String password;
    private String accessToken;

    @Before
    public void setUp() {
        super.setUp();

        email = "user_" + System.currentTimeMillis() + "@test.com";
        password = "password123";

        RegisterUserRequest request = new RegisterUserRequest(email, password, "testUser");

        Response response = given()
                .header(ApiConstants.CONTENT_TYPE, ApiConstants.APPLICATION_JSON)
                .body(request)
                .when()
                .post(ApiConstants.BASE_URL + ApiConstants.REGISTER);

        if (response.getStatusCode() == HttpStatus.SC_OK || response.getStatusCode() == HttpStatus.SC_CREATED) {
            accessToken = response.jsonPath().getString("accessToken");
        }
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            given()
                    .header(ApiConstants.AUTH, accessToken)
                    .when()
                    .delete(ApiConstants.BASE_URL + ApiConstants.USER)
                    .then()
                    .statusCode(HttpStatus.SC_ACCEPTED);
        }
        super.tearDown();
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной")
    @Description("Проверка успешной авторизации через кнопку на главном экране")
    public void shouldLoginViaMainPage() {
        driver.get(ApiConstants.BASE_URL);
        MainPage main = new MainPage(driver);
        main.clickLoginButton();

        LoginPage login = new LoginPage(driver);
        login.login(email, password);

        Assert.assertTrue("Пользователь не авторизован через главную страницу", main.isLoggedIn());
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Проверка перехода к логину при попытке зайти в личный кабинет без авторизации")
    public void shouldLoginViaPersonalAccount() {
        driver.get(ApiConstants.BASE_URL);
        MainPage main = new MainPage(driver);
        main.clickPersonalAccount();

        LoginPage login = new LoginPage(driver);
        login.login(email, password);

        Assert.assertTrue("Пользователь не авторизован через Личный кабинет", main.isLoggedIn());
    }

    @Test
    @DisplayName("Вход через ссылку в форме регистрации")
    @Description("Проверка возможности перейти к логину со страницы регистрации")
    public void shouldLoginViaRegistrationForm() {
        driver.get(ApiConstants.BASE_URL + "/register");

        RegisterPage register = new RegisterPage(driver);
        register.clickLoginLink();

        LoginPage login = new LoginPage(driver);
        login.login(email, password);

        Assert.assertTrue("Пользователь не авторизован через форму регистрации", new MainPage(driver).isLoggedIn());
    }

    @Test
    @DisplayName("Вход через ссылку в форме восстановления пароля")
    @Description("Проверка перехода к логину со страницы забытого пароля")
    public void shouldLoginViaForgotPasswordPage() {
        driver.get(ApiConstants.BASE_URL + "/forgot-password");

        ForgotPasswordPage forgot = new ForgotPasswordPage(driver);
        forgot.clickLoginLink();

        LoginPage login = new LoginPage(driver);
        login.login(email, password);

        Assert.assertTrue("Пользователь не авторизован через форму восстановления пароля", new MainPage(driver).isLoggedIn());
    }
}