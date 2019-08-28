package edu.udacity.java.nano;

import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



public class WebSocketChatApplicationTest {

    public static void main(String[] args) {
        //WebDriver driver = new FirefoxDriver();

        System.setProperty("webdriver.chrome.driver","/Users/shunsun/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();


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

        // finish test
        driver.quit();
    }
}