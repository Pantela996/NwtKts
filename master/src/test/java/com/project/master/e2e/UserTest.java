package com.project.master.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
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

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mihaj\\Desktop\\chromedriver_win32\\chromedriver.exe");
        browser = new ChromeDriver();

        browser.manage().window().maximize();;

        browser.navigate().to("http://localhost:4200");

        mainPage = PageFactory.initElements(browser, MainPage.class);
        loginPage = PageFactory.initElements(browser, LogInPage.class);
    }

    @Test
    public void testLogin(){
        //mainPage.ensureIsDisplayed();

        if (mainPage.getLogoutButton() == null) {
            mainPage.getLogoutButton().click();
        }

        mainPage.getLogInButton().click();

        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

        loginPage.ensureIsDisplyed();
        //entering empty credentials
        loginPage.getSingInButton().click();
        loginPage.ensureErrorIsDisplayed();
        assertEquals("Wrong username or password.", loginPage.getErrorMessage().getText());

        //entering wrong credentials
        loginPage.getUsernameInput().sendKeys("miki");
        loginPage.getPasswordInput().sendKeys("m");
        loginPage.getSingInButton().click();

        loginPage.ensureErrorIsDisplayed();
        assertEquals("Wrong username or password.", loginPage.getErrorMessage().getText());

        loginPage.getPasswordInput().clear();
        loginPage.getPasswordInput().sendKeys("user");
        loginPage.getSingInButton().click();


       // mainPage.ensureIsDisplayed();
        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());
    }

    @Test
    public void testRegister(){

        if(mainPage.getLogoutButton() == null) {
            mainPage.getLogoutButton().click();
        }

        //mainPage.ensureLoginIsDisplayed();

        mainPage.getLogInButton().click();

        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

       // loginPage.ensureIsDisplyed();
        loginPage.getSignUpButton().click();

        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //registerPage.getUsernameInput().clear();
        registerPage.getUsernameInput().sendKeys("Test1");
        //registerPage.getPasswordInput().clear();
        registerPage.getPasswordInput().sendKeys("TestPass");
        //registerPage.getEmailInput().clear();
        registerPage.getEmailInput().sendKeys("test@test@gmail.com");
        //registerPage.getNameInput().clear();
        registerPage.getNameInput().sendKeys("TestName1");
        //registerPage.getLastNameInput().clear();
        registerPage.getLastNameInput().sendKeys("TestLastName1");
        registerPage.getCreateButton().click();

        assertEquals("http://localhost:4200/register", browser.getCurrentUrl());





    }

    @AfterMethod
    public void closeSelenium(){
        browser.close();
    }


}
