package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DuluxHomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final By ACCEPT_BUTTON = By.cssSelector("#onetrust-accept-btn-handler");
    private static final By SEARCH_BUTTON = By.cssSelector("[aria-label='Search']");
    private static final By SEARCH_INPUT_FIELD = By.cssSelector("input[placeholder='Search For']");

    public DuluxHomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public SearchResultPage searchForColor(String colorName) {
        WebElement searchButton = driver.findElement(SEARCH_BUTTON);
        searchButton.click();

        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(SEARCH_INPUT_FIELD));
        searchBox.sendKeys(colorName);
        searchBox.submit();

        return new SearchResultPage(driver);
    }

    public void acceptCookiesIfPresent() {
        WebElement acceptButton = driver.findElement(ACCEPT_BUTTON);
        if (acceptButton.isDisplayed()) {
            acceptButton.click();
        }
    }
}
