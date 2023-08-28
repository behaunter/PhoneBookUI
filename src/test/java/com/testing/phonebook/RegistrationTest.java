package com.testing.phonebook;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class RegistrationTest extends Base {
    @BeforeMethod
    public void setUpRegistration() {
        clickOnElementByXpath("//a[contains(.,'Register new Account')]");
    }



    @Test
    public void RegistrationElementsExist() {
        validateElementsOnRegistrationPage();
    }

    @Test
    public void positiveRegistrationTest() {
        fillRegistrationForm(RANDOM_EMAIL, "12345678", "12345678");

        assertTrue(isElementExistXpath("//div[contains(text(),'noErrorMsg')]"));

        // check that we can log in with RANDOM_EMAIL
        clickOnElementByXpath("//a[text()='Already have an account?']");
        validateElementsOnHomePage();
        fillInputByXpath("//input[@name='email']", RANDOM_EMAIL);
        fillInputByXpath("//input[@name='password']", "12345678");
        clickOnElementByCss("[type='submit']");
        assertTrue(isElementExistXpath("//*[contains(text(), 'Please check your activation or Login " +
                "+ Password combination')]"));
    }

    @Test
    public void notCorrectEmailTest() {
        fillInputByXpath("//input[@name='email']", "1234");
        assertTrue(isElementExistXpath("//div[contains(text(),'Email must be a valid email address.')]"));
    }

    @Test
    public void passwordLessThan8Symbols(){
        fillInputByXpath("//body/app-root[1]/app-registration[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[1]/div[1]/input[1]", "1234567");
        assertTrue(isElementExistXpath("//div[@id='password-error-minlength']"));
    }
    @Test
    public void passwordMoreThan20Symbols(){
        fillInputByXpath("//body/app-root[1]/app-registration[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[1]/div[1]/input[1]", "12345678912345678912345");
        assertTrue(isElementExistXpath("//div[@id='password-error-maxlength']"));
    }
    @Test
    public void passwordDoesNotMatch(){
        fillInputByXpath("//body/app-root[1]/app-registration[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[1]/div[1]/input[1]", "12345678");
        fillInputByXpath("//body/app-root[1]/app-registration[1]/div[1]/div[2]/div[1]/form[1]/div[3]/div[1]/div[1]/input[1]", "1");
        assertTrue(isElementExistXpath("//div[@id='confirm-password-error-matcher']"));
    }
    @Test
    public void fieldsAreRequired (){
        clickOnElementByXpath("//body/app-root[1]/app-registration[1]/div[1]/div[2]/div[1]/form[1]/div[3]/div[1]/div[1]/input[1]");
        clickOnElementByXpath("//body/app-root[1]/app-registration[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[1]/div[1]/input[1]");
        clickOnElementByXpath("//body/app-root[1]/app-registration[1]/div[1]/div[2]/div[1]/form[1]/div[1]/div[1]/div[1]/input[1]");
        clickOnElementByXpath("//body/app-root[1]/app-registration[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[1]/div[1]/input[1]");
        assertTrue(isElementExistXpath("//div[@id='confirm-password-error-required']"));
        assertTrue(isElementExistXpath("//div[@id='email-error-required']"));
        assertTrue(isElementExistXpath("//div[@id='password-error-required']"));
    }
    @Test
    public void successfullRecoveryOfPassword(){
        clickOnElementByXpath("//a[contains(text(),'Already have an account?')]");
        clickOnElementByXpath("//a[contains(text(),'Forgot password?')]");
        fillInputByXpath("//input[@id='defaultRegisterFormEmail']", RANDOM_EMAIL);
        clickOnElementByXpath("//button[contains(text(),'Send')]");
        assertTrue(isElementExistXpath("//body/app-root[1]/app-forgot-password[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/h6[1]"));
    }
    @Test
    public void validateEmailFieldOnForgotPage(){
        clickOnElementByXpath("//a[contains(text(),'Already have an account?')]");
        clickOnElementByXpath("//a[contains(text(),'Forgot password?')]");
        clickOnElementByXpath("//input[@id='defaultRegisterFormEmail']");
        clickOnElementByXpath("//button[contains(text(),'Send')]");
        assertTrue(isElementExistXpath("//div[@id='email-error-required']"));
        fillInputByXpath("//input[@id='defaultRegisterFormEmail']",WRONG_RANDOM_EMAIL);
        assertTrue(isElementExistXpath("//div[@id='email-error-invalid']"));
    }

    @Test
    public void userDoesNotExistInDB(){
        clickOnElementByXpath("//a[contains(text(),'Already have an account?')]");
        clickOnElementByXpath("//a[contains(text(),'Forgot password?')]");
        fillInputByXpath("//input[@id='defaultRegisterFormEmail']", NOT_EXISTING_EMAIL);
        clickOnElementByXpath("//button[contains(text(),'Send')]");
        assertTrue(isElementExistXpath("//div[@id='error-message']"));
    }

    @Test
    public void validateEmailOnLoginPage(){
        clickOnElementByXpath("//a[contains(text(),'Already have an account?')]");
        clickOnElementByXpath("//input[@id='defaultRegisterFormEmail']");
        clickOnElementByXpath("//body/app-root[1]/app-login[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[1]/div[1]/input[1]");
        assertTrue(isElementExistXpath("//div[@id='email-error-required']"));
        fillInputByXpath("//input[@id='defaultRegisterFormEmail']",WRONG_RANDOM_EMAIL);
        assertTrue(isElementExistXpath("//div[@id='email-error-invalid']"));
    }

    @Test
    public void validatePasswordOnLoginPage(){
        clickOnElementByXpath("//a[contains(text(),'Already have an account?')]");
        clickOnElementByXpath("//body/app-root[1]/app-login[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[1]/div[1]/input[1]");
        clickOnElementByXpath("//input[@id='defaultRegisterFormEmail']");
        assertTrue(isElementExistXpath("//div[@id='password-error-required']"));
        fillInputByXpath("//body/app-root[1]/app-login[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[1]/div[1]/input[1]",WRONG_RANDOM_EMAIL);
        assertTrue(isElementExistXpath("//div[@id='password-error-maxlength']"));
        fillInputByXpath("//body/app-root[1]/app-login[1]/div[1]/div[2]/div[1]/form[1]/div[2]/div[1]/div[1]/input[1]","123456");
        assertTrue(isElementExistXpath("//div[@id='password-error-minlength']"));

    }





}