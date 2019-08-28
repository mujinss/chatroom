package edu.udacity.java.nano.chat;

import org.junit.Test;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.seleniumhq.selenium.*;
import org.seleniumhq.selenium.By;
import org.seleniumhq.selenium.WebDriver;
/*import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;*/


@SpringBootTest(classes={WebSocketChatApplication.class})
public class WebSocketChatApplicationTest {

    public static void main(String[] args) {
        //WebDriver driver = new ChromeDriver();
        WebDriver driver = new FirefoxDriver();
        // test login;
        driver.get("localhost:8026");
        Assert.assertEquals(driver.getTitle(), "Chat Room Login");
        WebElement loginField = driver.findElement(By.name("username"));

        loginField.sendKeys("Helen");
        loginField.submit();
        Assert.assertEquals(driver.getTitle(), "Chat Room");
        System.out.println("Page title is: " + driver.getTitle());
        WebElement username = driver.findElement(By.id("username"));

        Assert.assertEquals(username.getText(), "Helen");
        System.out.println("Welcom: " + username.getText());

        // test send message;

        driver.quit();
    }
}