package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchResultPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final By TRY_VISUALIZER_APP_BUTTON = By.cssSelector("a.a122-primary-button.is-small.visualizer-desktop-link");

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickTryVisualizerApp() {
        WebElement visualizerLink = wait.until(ExpectedConditions.elementToBeClickable(TRY_VISUALIZER_APP_BUTTON));
        visualizerLink.click();
    }

}
