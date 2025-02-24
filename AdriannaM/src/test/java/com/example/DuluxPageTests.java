package com.example;

import com.example.pages.CartPage;
import com.example.pages.ColorDetailsPage;
import com.example.pages.DuluxHomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DuluxPageTests {
    private DuluxHomePage duluxHomePage;
    private WebDriver driver;
    private static final String GENTLE_LAVENDER = "Gentle Lavender";

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1300, 1300));
        driver.get("https://www.dulux.co.uk/");

        duluxHomePage = new DuluxHomePage(driver);
        duluxHomePage.acceptCookiesIfPresent();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void shouldAddColorToCartWhenTesterBought() {
        duluxHomePage.expandFindAColourDropdownOnHover();
        ColorDetailsPage colourDetailsPage = duluxHomePage.clickOnFindAColourDropdownOption();

        String violet = "Violet";
        colourDetailsPage.pickColor(violet);
        colourDetailsPage.clickOnColor(GENTLE_LAVENDER);

        assertTrue(colourDetailsPage.assertColorOpened(GENTLE_LAVENDER),
                String.format("%s has not been opened", GENTLE_LAVENDER));

        colourDetailsPage.clickOnBuyATester();
        colourDetailsPage.verifyCartItemAmount(1);

        CartPage cartPage = duluxHomePage.clickOnCart();

        assertTrue(cartPage.assertColorAdded(GENTLE_LAVENDER),
                String.format("%s tester has not been added to cart", GENTLE_LAVENDER));
    }

    @Test
    public void shouldOpenVisualizerAppInNewTabWhenClickedFromColorPage() {
        ColorDetailsPage colourDetailsPage = duluxHomePage.searchForAndSelectColorByName(GENTLE_LAVENDER);

        colourDetailsPage.clickOnTryVisualizerApp();

        Set<String> windowUrls = getWindowUrls();
        assertTrue(windowUrls.stream()
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
