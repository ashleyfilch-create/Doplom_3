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

    // ===== STEP 1 =====
    private final By emailInput =
            By.xpath("//input[@type='email' or @type='text' or contains(@placeholder,'Email')]");

    private final By recoverButton =
            By.xpath("//button[contains(text(),'Восстанов')]");

    // ===== STEP 2 =====
    private final By passwordInput =
            By.xpath("//form//input[@type='password']");

    private final By codeInput =
            By.xpath("//form//input[contains(@placeholder,'код') or contains(@name,'код') or contains(@placeholder,'Код')]");

    private final By saveButton =
            By.xpath("//button[contains(text(),'Сохран')]");

    // ---------------- STEP 1 ----------------

    @Step("Enter email")
    public ForgotPasswordPage enterEmail(String email) {

        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(emailInput)
        );

        input.clear();
        input.sendKeys(email);

        return this;
    }

    @Step("Click recover button")
    public ForgotPasswordPage clickRecover() {

        wait.until(ExpectedConditions.elementToBeClickable(recoverButton)).click();

        // 🔥 SPA FIX: ждём переход на STEP 2
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        wait.until(ExpectedConditions.visibilityOfElementLocated(codeInput));

        return this;
    }

    // ---------------- STEP 2 ----------------

    @Step("Enter password")
    public ForgotPasswordPage enterPassword(String password) {

        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(passwordInput)
        );

        input.clear();
        input.sendKeys(password);

        return this;
    }

    @Step("Enter code")
    public ForgotPasswordPage enterCode(String code) {

        // 🔥 FIX: SPA-safe (без clickable, он ломается на анимациях)
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(codeInput)
        );

        wait.until(driver -> input.isDisplayed() && input.isEnabled());

        input.clear();
        input.sendKeys(code);

        return this;
    }

    @Step("Click save")
    public void clickSave() {

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
}