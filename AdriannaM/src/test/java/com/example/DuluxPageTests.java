package com.example;

import com.example.pages.DuluxHomePage;
import com.example.pages.SearchResultPage;
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


    private DuluxHomePage duluxHomePage;
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1300, 1300));
        driver.get("https://www.dulux.co.uk/");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void shouldOpenVisualizerAppInNewTabWhenClickedFromColorPage() {
        duluxHomePage = new DuluxHomePage(driver);
        String color = "Gentle Lavender";

        duluxHomePage.acceptCookiesIfPresent();
        SearchResultPage searchResultPage = duluxHomePage.searchForColor(color);
        
        searchResultPage.clickTryVisualizerApp();

        Set<String> windowUrls = getWindowUrls();
        Assertions.assertTrue(windowUrls.stream()
                .anyMatch(url -> url.contains("dulux-visualizer-app")), "New tab has not been open");
    }

    private Set<String> getWindowUrls() {
        Set<String> windowHandles = driver.getWindowHandles();
        Set<String> windowUrls = new HashSet<>();

        for (String handle : windowHandles) {
            driver.switchTo().window(handle);
            windowUrls.add(driver.getCurrentUrl());
        }
        return windowUrls;
    }
}
