package praktikum.tests;

import io.qameta.allure.*;
import org.junit.Assert;
import org.junit.Test;
import praktikum.BaseTestUi;
import praktikum.pages.ConstructorPage;

@Epic("UI Tests")
@Feature("Constructor")
public class ConstructorUiTests extends BaseTestUi {

    @Test
    @Story("Switch to sauces tab")
    @Description("Проверяет, что при клике на вкладку 'Соусы' она становится активной")
    public void shouldOpenSaucesTab() {
        ConstructorPage constructor = new ConstructorPage(driver);

        constructor.clickSauces();

        Assert.assertTrue("Sauces tab is not active",
                constructor.isTabActive("Соусы"));
    }

    @Test
    @Story("Switch to fillings tab")
    @Description("Проверяет, что при клике на вкладку 'Начинки' она становится активной")
    public void shouldOpenFillingsTab() {
        ConstructorPage constructor = new ConstructorPage(driver);

        constructor.clickFillings();

        Assert.assertTrue("Fillings tab is not active",
                constructor.isTabActive("Начинки"));
    }

    @Test
    @Story("Switch to buns tab")
    @Description("Проверяет, что при клике на вкладку 'Булки' она становится активной")
    public void shouldOpenBunsTab() {
        ConstructorPage constructor = new ConstructorPage(driver);

        constructor.clickBuns();

        Assert.assertTrue("Buns tab is not active",
                constructor.isTabActive("Булки"));
    }
}