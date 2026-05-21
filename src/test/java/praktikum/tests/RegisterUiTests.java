package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import praktikum.BaseTestUi;
import praktikum.constants.ApiConstants;
import praktikum.models.RegisterUserRequest;
import praktikum.pages.LoginPage;
import praktikum.pages.RegisterPage;

import static io.restassured.RestAssured.given;

@Feature("Регистрация пользователя")
public class RegisterUiTests extends BaseTestUi {

    private String accessToken;

    @After
    public void tearDown() {
        // Очистка данных после теста через API
        if (accessToken != null) {
            deleteUser(accessToken);
        }
        super.tearDown();
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверка перехода на страницу логина после успешного заполнения формы регистрации")
    public void successRegistrationTest() {
        String name = "Ivan";
        String email = "ivan_" + System.currentTimeMillis() + "@test.com";
        String password = "password123";

        driver.get(ApiConstants.BASE_URL + "/register");

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(name, email, password);

        LoginPage loginPage = new LoginPage(driver);

        boolean isLoginOpened = loginPage.isOpened();
        Assert.assertTrue("Не удалось перейти на страницу входа после регистрации", isLoginOpened);

        accessToken = loginAndGetToken(email, password);
    }

    @Test
    @DisplayName("Регистрация с коротким паролем")
    @Description("Проверка появления ошибки при вводе пароля менее 6 символов")
    public void shouldShowErrorForShortPassword() {
        String name = "Ivan";
        String email = "ivan_" + System.currentTimeMillis() + "@test.com";
        String shortPassword = "123";

        driver.get(ApiConstants.BASE_URL + "/register");

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(name, email, shortPassword);

        Assert.assertTrue("Сообщение об ошибке не отображается", registerPage.isPasswordErrorDisplayed());
    }

    @Step("Получить токен пользователя через API для удаления")
    private String loginAndGetToken(String email, String password) {
        RegisterUserRequest credentials = new RegisterUserRequest(email, password, null);
        Response response = given()
                .header(ApiConstants.CONTENT_TYPE, ApiConstants.APPLICATION_JSON)
                .body(credentials)
                .when()
                .post(ApiConstants.BASE_URL + ApiConstants.LOGIN);

        if (response.getStatusCode() == HttpStatus.SC_OK) {
            return response.jsonPath().getString("accessToken");
        }
        return null;
    }

    @Step("Удалить пользователя через API")
    private void deleteUser(String token) {
        given()
                .header(ApiConstants.AUTH, token)
                .when()
                .delete(ApiConstants.BASE_URL + ApiConstants.USER)
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED); // Ожидаем 202 Accepted
    }
}