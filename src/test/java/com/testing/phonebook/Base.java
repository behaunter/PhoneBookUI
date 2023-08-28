package com.testing.phonebook;

import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;

import static org.testng.Assert.assertTrue;


public class Base {
    String RANDOM_EMAIL = UUID.randomUUID() + "@gmail.com";
    String WRONG_RANDOM_EMAIL = UUID.randomUUID() + "1";

    String NOT_EXISTING_EMAIL = UUID.randomUUID() + "@gmail.com";
    public WebDriver driver = new ChromeDriver();
    String url = "http://phonebook.telran-edu.de:8080/";

    @BeforeClass
    public void setUp() {
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @BeforeMethod
    public void setUpMethod(){
        driver.get(url);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    public boolean isElementExistXpath(String xpath) {
        return driver.findElements(By.xpath(xpath)).size() > 0;
    }

    public boolean isElementExistCssSelector(String selector) {
        return driver.findElements(By.cssSelector(selector)).size() > 0;
    }

    public boolean isElementExistTryCatch(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

    public void clickOnElementByXpath(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    public void clickOnElementByCss(String selector) {
        driver.findElement(By.cssSelector(selector)).click();
    }

    public void fillInputByXpath(String xpath, String value) {
        clickOnElementByXpath(xpath);
        driver.findElement(By.xpath(xpath)).clear();
        driver.findElement(By.xpath(xpath)).sendKeys(value);
    }

    public void pause(int millis) throws InterruptedException {
        Thread.sleep(millis);
    }


    public void validateElementsOnHomePage() {
        Assert.assertTrue(isElementExistXpath(("//h2")));
        Assert.assertTrue(isElementExistXpath("//h3[contains(text(),'Login')]"));
        Assert.assertTrue(isElementExistTryCatch(By.xpath("//form[@id='login-form']")));
    }

    protected void validateElementsOnRegistrationPage() {
        assertTrue(isElementExistXpath("//h2"));
        assertTrue(isElementExistXpath("//h3[contains(.,'Registration page')]"));
        assertTrue(isElementExistTryCatch(By.cssSelector("#registration-form")));
    }

    public void clickWithAction(By locator) {
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(locator);
        actions.moveToElement(element);
        element.click();
    }

    public void fillRegistrationForm(String email, String password, String confirmPassword) {
        // email
        fillInputByXpath("//input[@name='email']", email);

        // password
        fillInputByXpath("//input[@name='password']", password);

        // confirm password
        fillInputByXpath("//input[@name='confirm-password']", confirmPassword);

        // click submit and check noErrorMsg
        clickOnElementByCss("[type='submit']");
    }

    //Cоздать тесты на Забыл пароль
    //Создать негативные тесты на логин пароль
    //Минимум 10 тестов


}