package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DuluxHomePage extends NavigationBar {
    private final WebDriverWait wait;
    private static final By ACCEPT_BUTTON = By.cssSelector("#onetrust-accept-btn-handler");

    public DuluxHomePage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void acceptCookiesIfPresent() {
        try {
            WebElement acceptButton = wait.until(ExpectedConditions.presenceOfElementLocated(ACCEPT_BUTTON));

            if (acceptButton.isDisplayed()) {
                acceptButton.click();
            }
        } catch (NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            System.out.println("Cookies popup not found or already accepted.");
        }
    }
}
