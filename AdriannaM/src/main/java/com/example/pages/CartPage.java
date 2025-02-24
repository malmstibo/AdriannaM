package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private final WebDriver driver;
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean assertColorAdded(String color) {
        try {
            driver.findElement(By.xpath("//span[contains(text(), '" + color + "')]"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
