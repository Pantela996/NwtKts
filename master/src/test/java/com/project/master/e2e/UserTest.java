package com.project.master.e2e;

import com.project.master.constants.UserConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertEquals;

public class UserTest {

    private WebDriver browser;

    MainPage mainPage;
    LogInPage loginPage;
    RegisterPage registerPage;


    @BeforeMethod
    public void setupSelenium(){

    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Korisnik\\Desktop\\chromedriver.exe");
        browser = new ChromeDriver();

        browser.manage().window().maximize();;

        browser.navigate().to("http://localhost:4200");

        mainPage = PageFactory.initElements(browser, MainPage.class);
        loginPage = PageFactory.initElements(browser, LogInPage.class);
        registerPage = PageFactory.initElements(browser, RegisterPage.class);
    }

    @Test
    public void testLogin(){

        //ensure main page is displayed
        WebDriverWait wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getMainPageLink()));

        assertEquals("http://localhost:4200/", browser.getCurrentUrl());

        //ensure login button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getLogInButton()));

        mainPage.getLogInButton().click();

        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

        //ensure signIn button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(loginPage.getSingInButton()));

        //entering empty credentials
        loginPage.getSingInButton().click();
        loginPage.ensureErrorIsDisplayed();
        assertEquals("Wrong username or password.", loginPage.getErrorMessage().getText());

        //entering wrong credentials
        loginPage.getUsernameInput().sendKeys("miki");
        loginPage.getPasswordInput().sendKeys("m");
        loginPage.getSingInButton().click();
        assertEquals("Wrong username or password.", loginPage.getErrorMessage().getText());

        //entering right credentials
        loginPage.getPasswordInput().clear();
        loginPage.getPasswordInput().sendKeys("user");
        loginPage.getSingInButton().click();

        //ensure main page is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getMainPageLink()));

        assertEquals("http://localhost:4200/", browser.getCurrentUrl());
    }

    @Test
    public void testRegister(){

        //ensure main page is displayed
        WebDriverWait wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getMainPageLink()));

        assertEquals("http://localhost:4200/", browser.getCurrentUrl());

        //ensure login button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getLogInButton()));

        mainPage.getLogInButton().click();

        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

        //ensure signUp button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(loginPage.getSignUpButton()));

        loginPage.getSignUpButton().click();
        assertEquals("http://localhost:4200/register", browser.getCurrentUrl());

        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(registerPage.getCreateButton()));

        registerPage.getUsernameInput().sendKeys(UserConstants.NEW_USERNAME);
        registerPage.getPasswordInput().sendKeys(UserConstants.NEW_PASSWORD);
        registerPage.getEmailInput().sendKeys(UserConstants.NEW_EMAIL);
        registerPage.getNameInput().sendKeys(UserConstants.NEW_NAME);
        registerPage.getLastNameInput().sendKeys(UserConstants.NEW_PASSWORD);

        registerPage.getCreateButton().click();

        //ensure main page is displayed
        //wait = new WebDriverWait(browser, 15);
        //wait.until(ExpectedConditions.elementToBeClickable(mainPage.getMainPageLink()));

        //assertEquals("http://localhost:4200/", browser.getCurrentUrl());

    }

    @AfterMethod
    public void closeSelenium(){
        browser.close();
    }


}
