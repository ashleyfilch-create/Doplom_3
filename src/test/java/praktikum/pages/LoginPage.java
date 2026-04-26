package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // локаторы
    private By emailField = By.xpath(".//input[contains(@name,'name') or @type='email']");
    private By passwordField = By.xpath(".//input[@type='password' or @name='Пароль']");
    private By loginButton = By.xpath(".//button[contains(text(),'Войти')]");
    private By registerLink = By.xpath(".//a[text()='Зарегистрироваться']");
    private By forgotPasswordLink = By.xpath(".//a[text()='Восстановить пароль']");
    private By primaryButton = By.xpath(
            "//button[contains(@class,'button_button_type_primary')]"
    );

    @Step("Login with email: {email}")
    public void login(String email, String password) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));

        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);

        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    @Step("Enter email only: {email}")
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Click primary action button")
    public void clickPrimaryButton() {
        wait.until(ExpectedConditions.elementToBeClickable(primaryButton)).click();
    }

    @Step("Go to Register page")
    public RegisterPage goToRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
        return new RegisterPage(driver);
    }

    @Step("Go to Forgot Password page")
    public ForgotPasswordPage goToForgotPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
        return new ForgotPasswordPage(driver);
    }
}