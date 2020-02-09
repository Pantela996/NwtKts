package com.project.master.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class MainPageSuperAdminTest {

    private WebDriver browser;

    MainPage mainPage;

    @BeforeMethod
    public void setupSelenium(){
        //setup chrome driver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mihaj\\Desktop\\chromedriver_win32\\chromedriver.exe");
        browser = new ChromeDriver();

        //maximize window
        browser.manage().window().maximize();

        //navigate
        browser.navigate().to("http://localhost:4200");

        //factory
        mainPage = PageFactory.initElements(browser, MainPage.class);
    }
}
