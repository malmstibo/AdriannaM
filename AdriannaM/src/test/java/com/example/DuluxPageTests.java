package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class DuluxPageTests {


    private WebDriver driver;
    private static WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.dulux.co.uk/");
        driver.manage().window().setSize(new Dimension(1300, 1300));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        acceptCookiesIfPresent(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void shouldOpenVisualizerAppInNewTabWhenClickedFromColorPage() {
        //1. On page www.dulux.co.uk search for any color in the search engine e.g. "Gentle Lavender" and go to the given colour page
        WebElement search = driver.findElement(By.cssSelector("[aria-label='Search']"));
        search.click();

        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[placeholder='Search For']")));
        searchBox.sendKeys("Gentle Lavender");
        searchBox.submit();

        //2. And from the color page go to "Try our Visualizer App" and verify that it opened in a new tab.
        WebElement visualizerLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.a122-primary-button.is-small.visualizer-desktop-link")));
        visualizerLink.click();

        Set<String> windowHandles = driver.getWindowHandles();
        Set<String> windowUrls = new HashSet<>();

        for (String handle : windowHandles) {
            driver.switchTo().window(handle);
            windowUrls.add(driver.getCurrentUrl());
        }

        boolean visualiser = windowUrls.stream()
                .anyMatch(url -> url.contains("dulux-visualizer-app"));
        Assertions.assertTrue(visualiser, "New tab has not been open");
    }

    private void acceptCookiesIfPresent(WebDriver driver) {
        WebElement acceptButton = driver.findElement(By.cssSelector("#onetrust-accept-btn-handler"));
        if (acceptButton.isDisplayed()) {
            acceptButton.click();
        }
    }
}
