package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage extends BasePage {

    private final WebDriverWait wait;

    public MainPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalAccount = By.xpath(".//p[text()='Личный Кабинет']");
    private final By constructorHeader = By.xpath(".//h1[text()='Соберите бургер']");
    private final By profileButton = By.xpath(".//p[text()='Личный Кабинет']");
    private final By orderButton = By.xpath(".//button[text()='Оформить заказ']");

    @Step("Проверка открытия главной страницы")
    public boolean isOpened() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(constructorHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Click Login button")
    public LoginPage clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new LoginPage(driver);
    }

    @Step("Click Personal Account button")
    public LoginPage clickPersonalAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccount)).click();
        return new LoginPage(driver);
    }

    @Step("Check user is logged in")
    public boolean isLoggedIn() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(orderButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}