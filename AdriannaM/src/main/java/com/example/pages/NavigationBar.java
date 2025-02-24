package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class NavigationBar {

    public final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public NavigationBar(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }
    private static final By SHOPPING_CART_BUTTON = By.cssSelector("[aria-label='Shopping Cart']");
    private static final By SEARCH_BUTTON = By.cssSelector("[aria-label='Search']");
    private static final By SEARCH_INPUT_FIELD = By.cssSelector("input[placeholder='Search For']");
    private static final By FIND_A_COLOUR_DROPDOWN = By.xpath("//button//span[contains(text(), 'Find a colour')]");
    private static final By FIND_A_COLOUR_DROPDOWN_OPTION = By.xpath("//a[contains(text(), 'Find a colour')]");
    private static final By SUCCESS_POP_UP = By.cssSelector(".a41-alert");
    private static final By RESULT_LIST_POP_UP = By.cssSelector(".result-list");
    private static final String COLOUR_DETAILS_URL = "/colour-details/";
    private static final String CART_URL = "/store/cart";

    public ColorDetailsPage searchForAndSelectColorByName(String colorName) {
        WebElement searchButton = driver.findElement(SEARCH_BUTTON);
        searchButton.click();

        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(SEARCH_INPUT_FIELD));
        searchBox.sendKeys(colorName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(RESULT_LIST_POP_UP));

        WebElement colorNameButton = driver.findElement(By.xpath("//span[contains(text(), '" + colorName + "')]"));
        colorNameButton.click();

        return new ColorDetailsPage(driver);
    }

    public void expandFindAColourDropdownOnHover() {
        WebElement colourDropdown = driver.findElement(FIND_A_COLOUR_DROPDOWN);
        actions.moveToElement(colourDropdown).perform();
    }

    public ColorDetailsPage clickOnFindAColourDropdownOption() {
        WebElement findAColorOption = wait.until(ExpectedConditions.elementToBeClickable(FIND_A_COLOUR_DROPDOWN_OPTION));
        findAColorOption.click();
        wait.until(ExpectedConditions.urlContains(COLOUR_DETAILS_URL));

        return new ColorDetailsPage(driver);
    }

    public CartPage clickOnCart() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(SUCCESS_POP_UP));
        WebElement cart = driver.findElement(SHOPPING_CART_BUTTON);
        cart.click();
        wait.until(ExpectedConditions.urlContains(CART_URL));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".container-page-cart")));

        return new CartPage(driver);
    }
    public void verifyCartItemAmount(int amount) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(SHOPPING_CART_BUTTON, String.valueOf(amount)));
    }
}
