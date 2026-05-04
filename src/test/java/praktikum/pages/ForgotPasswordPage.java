package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage extends BasePage {

    private final WebDriverWait wait;

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By emailInput = By.xpath(".//label[text()='Email']/parent::div/input");
    private final By recoverButton = By.xpath(".//button[text()='Восстановить']");

    private final By loginLink = By.xpath(".//a[text()='Войти']");

    private final By passwordInput = By.xpath(".//label[text()='Пароль']/parent::div/input");
    private final By codeInput = By.xpath(".//label[text()='Введите код из письма']/parent::div/input");
    private final By saveButton = By.xpath(".//button[text()='Сохранить']");

    @Step("Нажать на ссылку 'Войти'")
    public void clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }

    @Step("Ввести email для восстановления")
    public ForgotPasswordPage enterEmail(String email) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        input.clear();
        input.sendKeys(email);
        return this;
    }

    @Step("Нажать кнопку 'Восстановить'")
    public ForgotPasswordPage clickRecover() {
        wait.until(ExpectedConditions.elementToBeClickable(recoverButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        return this;
    }

    @Step("Ввести новый пароль")
    public ForgotPasswordPage enterPassword(String password) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        input.clear();
        input.sendKeys(password);
        return this;
    }

    @Step("Ввести код из письма")
    public ForgotPasswordPage enterCode(String code) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(codeInput));
        input.clear();
        input.sendKeys(code);
        return this;
    }

    @Step("Нажать кнопку 'Сохранить'")
    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
}