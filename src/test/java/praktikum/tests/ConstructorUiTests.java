package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseTestUi;
import praktikum.constants.ApiConstants;
import praktikum.pages.ConstructorPage;

@Feature("Конструктор")
public class ConstructorUiTests extends BaseTestUi {

    @Before
    public void setUp() {
        super.setUp();
        driver.get(ApiConstants.BASE_URL);
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    @Description("Проверяет, что при клике на 'Соусы' вкладка становится активной")
    public void shouldSwitchToSauces() {
        ConstructorPage constructor = new ConstructorPage(driver);
        constructor.clickSauces();
        Assert.assertTrue("Вкладка 'Соусы' должна быть активной", constructor.isTabActive("Соусы"));
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    @Description("Проверяет, что при клике на 'Начинки' вкладка становится активной")
    public void shouldSwitchToFillings() {
        ConstructorPage constructor = new ConstructorPage(driver);
        constructor.clickFillings();
        Assert.assertTrue("Вкладка 'Начинки' должна быть активной", constructor.isTabActive("Начинки"));
    }

    @Test
    @DisplayName("Переход к разделу 'Булки'")
    @Description("Проверяет возврат к вкладке 'Булки' после другого раздела")
    public void shouldSwitchToBuns() {
        ConstructorPage constructor = new ConstructorPage(driver);
        constructor.clickSauces();
        constructor.clickBuns();

        Assert.assertTrue("Вкладка 'Булки' должна быть активной", constructor.isTabActive("Булки"));
    }
}