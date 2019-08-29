package edu.udacity.java.nano;

import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class WebSocketChatApplicationTest {

    public static void main(String[] args) {
        //WebDriver driver = new FirefoxDriver();

        System.setProperty("webdriver.chrome.driver","/Users/shunsun/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();


        // test login;
        String loginUrl = "localhost:8080";
        driver.get(loginUrl);
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
        WebElement msgSent = driver.findElement(By.id("msg"));
        msgSent.sendKeys("Hello");
        WebElement sendMsg = driver.findElement(By.id("sendMsgToServer"));
        sendMsg.click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        List<WebElement> msgReceived = driver.findElements(By.className("message-content"));
        Assert.assertEquals(msgReceived.get(0).getText(), "Helen: Hello");

        // open a new window
        WebDriver driver2 = new ChromeDriver();
        driver2.get(loginUrl);
        WebElement loginField2 = driver2.findElement(By.name("username"));

        loginField2.sendKeys("Mary");
        loginField2.submit();
        WebElement username2 = driver2.findElement(By.id("username"));
        WebElement userCount = driver2.findElement(By.className("chat-num"));
        Assert.assertEquals(userCount.getText(), "2");

        // test user2 send message
        WebElement msgSent2 = driver2.findElement(By.id("msg"));
        msgSent2.sendKeys("World");
        WebElement sendMsg2 = driver2.findElement(By.id("sendMsgToServer"));
        sendMsg2.click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        msgReceived = driver.findElements(By.className("message-content"));
        Assert.assertEquals(msgReceived.size(), 2);
        Assert.assertEquals(msgReceived.get(1).getText(), "Mary: World");

        // test user leave
        WebElement userExit = driver.findElement(By.id("exit"));
        userExit.click();
        userCount = driver2.findElement(By.className("chat-num"));
        Assert.assertEquals(userCount.getText(), "1");



        // finish test
        System.out.println("tests passed!");
        driver.quit();
    }
}