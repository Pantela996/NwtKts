 package com.project.master.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class LocationAdminTest {

    private WebDriver browser;

    MainPage mainPage;

    LogInPage logInPage;

    CreateLocationPage createLocationPage;

    LocationPage locationPage;

    @BeforeMethod
    public void setupSelenium() {
        //setup chrome driver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Korisnik\\Desktop\\chromedriver.exe");
        browser = new ChromeDriver();

        //maximize window
        browser.manage().window().maximize();

        //navigate
        browser.navigate().to("http://localhost:4200");

        //factory
        mainPage = PageFactory.initElements(browser, MainPage.class);
        logInPage = PageFactory.initElements(browser, LogInPage.class);
        createLocationPage = PageFactory.initElements(browser, CreateLocationPage.class);
        locationPage = PageFactory.initElements(browser, LocationPage.class);
    }

    @Test
    public void createLocation() throws InterruptedException{

        //ensure main page is displayed
        WebDriverWait wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getMainPageLink()));

        assertEquals("http://localhost:4200/", browser.getCurrentUrl());

        //ensure login button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getLogInButton()));

        mainPage.getLogInButton().click();

        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

        logInPage.getUsernameInput().sendKeys("user");
        logInPage.getPasswordInput().sendKeys("user");
        logInPage.getSingInButton().click();

        //ensure all users button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getCreateLocationsButton()));

        mainPage.getCreateLocationsButton().click();

        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(createLocationPage.getAddInput()));

        assertEquals("http://localhost:4200/read_update_location", browser.getCurrentUrl());

        WebElement tableProducts = browser.findElement(By.id("mark"));
        List<WebElement> tableRows = new WebDriverWait(browser, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableProducts, By.tagName("tr")));

        int before = tableRows.size();

        createLocationPage.getAddInput().click();

        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable((locationPage.getGetCreateButtonInput())));
        assertEquals("http://localhost:4200/create_location", browser.getCurrentUrl());

        locationPage.getNameInput().sendKeys("Noooovi3");
        locationPage.getCityLocationInput().sendKeys("Noooooovi Sad3");
        locationPage.getNohInput().sendKeys("4");
        locationPage.getGetCreateButtonInput().click();

        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(createLocationPage.getAddInput()));

        assertEquals("http://localhost:4200/read_update_location", browser.getCurrentUrl());

        tableProducts = browser.findElement(By.id("mark"));
        tableRows = new WebDriverWait(browser, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableProducts, By.tagName("tr")));

        int after = tableRows.size();

        assertEquals(before+1, after);


    }

    @Test
    public void createEvent() throws InterruptedException{


    }





    @AfterMethod
    public void closeSelenium(){
        browser.close();
    }

}
