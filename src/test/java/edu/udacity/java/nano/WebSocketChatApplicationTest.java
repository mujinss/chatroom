package edu.udacity.java.nano;

import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.net.MalformedURLException;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class WebSocketChatApplicationTest {

    @Autowired
    private Environment environment;
    private static String BASE_URL;
    private static String CHAT_URL;


    private WebDriver driver;
    private WebDriver driver2;
    @PostConstruct
    public void initUrls() {
        BASE_URL = "localhost:8080";
        CHAT_URL = "localhost:8080/chat";
    }

    @Before
    public void init() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
        if (driver2 != null) {
            driver2.quit();
        }
    }

    @Test
    public void testLogin() throws MalformedURLException {

        // test login;
        //String loginUrl = "localhost:8080";
        driver.get(BASE_URL);
        Assert.assertEquals(driver.getTitle(), "Chat Room Login");
        WebElement loginField = driver.findElement(By.name("username"));

        loginField.sendKeys("Helen");
        loginField.submit();
        Assert.assertEquals(driver.getTitle(), "Chat Room");
        System.out.println("Page title is: " + driver.getTitle());
        WebElement username = driver.findElement(By.id("username"));

        Assert.assertEquals(username.getText(), "Helen");
        System.out.println("Welcome: " + username.getText());

        // test send message;
        WebElement msgSent = driver.findElement(By.id("msg"));
        msgSent.sendKeys("Hello");
        WebElement sendMsg = driver.findElement(By.id("sendMsgToServer"));
        sendMsg.click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        List<WebElement> msgReceived = driver.findElements(By.className("message-content"));
        Assert.assertEquals(msgReceived.get(0).getText(), "Helen: Hello");

        // open a new window
        driver2 = new ChromeDriver();
        driver2.get(BASE_URL);
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
        //driver.quit();
    }
}