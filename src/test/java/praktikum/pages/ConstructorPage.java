package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ConstructorPage extends BasePage {

    private final By bunsTab = By.xpath(".//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath(".//span[text()='Начинки']/parent::div");

    private final String activeTabClass = "tab_tab_type_current";

    public ConstructorPage(WebDriver driver) {
        super(driver);
    }

    @Step("Кликнуть на вкладку 'Булки'")
    public void clickBuns() {
        driver.findElement(bunsTab).click();
    }

    @Step("Кликнуть на вкладку 'Соусы'")
    public void clickSauces() {
        driver.findElement(saucesTab).click();
    }

    @Step("Кликнуть на вкладку 'Начинки'")
    public void clickFillings() {
        driver.findElement(fillingsTab).click();
    }

    @Step("Проверить, активна ли вкладка: {tabName}")
    public boolean isTabActive(String tabName) {
        By tabLocator;
        switch (tabName) {
            case "Булки": tabLocator = bunsTab; break;
            case "Соусы": tabLocator = saucesTab; break;
            case "Начинки": tabLocator = fillingsTab; break;
            default: throw new IllegalArgumentException("Неизвестная вкладка: " + tabName);
        }
        return driver.findElement(tabLocator).getAttribute("class").contains(activeTabClass);
    }
}