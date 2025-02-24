package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ColorDetailsPage extends NavigationBar {
    private final WebDriverWait wait;
    public static final By PAGE_TITLE = By.cssSelector(".header-04.product-title");
    public static final By COLOR_DETAIL_COMPONENT = By.cssSelector("[data-component='m48-color-detail']");
    public static final By BUY_TESTER_BUTTON = By.cssSelector("button.a122-primary-button.is-ecommerce.is-small.js-add-cart");
    public static final By TRY_VISUALIZER_APP_BUTTON = By.cssSelector("a.a122-primary-button.is-small.visualizer-desktop-link");

    public ColorDetailsPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void pickColor(String colorName) {
        By colourButton = By.cssSelector("[data-color-name='" + colorName + "']");
        WebElement button = driver.findElement(colourButton);
        button.click();
    }

    public void clickOnColor(String colorName) {
        WebElement colorCard = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-label='" + colorName + "']")));
        colorCard.click();
    }

    public void clickOnBuyATester() {
        WebElement colorDetail = driver.findElement(COLOR_DETAIL_COMPONENT);
        wait.until(ExpectedConditions.visibilityOf(colorDetail));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable((BUY_TESTER_BUTTON))).click();
    }

    public void clickOnTryVisualizerApp() {
        WebElement colorDetail = driver.findElement(COLOR_DETAIL_COMPONENT);
        wait.until(ExpectedConditions.visibilityOf(colorDetail));
        wait.until(ExpectedConditions.visibilityOfElementLocated(TRY_VISUALIZER_APP_BUTTON)).click();
    }

    public boolean assertColorOpened(String expectedColor) {
        String actualColor = getProductTitle();
        return expectedColor.equals(actualColor);
    }

    private String getProductTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PAGE_TITLE)).getText();
    }
}
