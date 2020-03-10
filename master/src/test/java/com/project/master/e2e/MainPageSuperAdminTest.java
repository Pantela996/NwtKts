package com.project.master.e2e;

import org.junit.Before;
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

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class MainPageSuperAdminTest {

    private WebDriver browser;

    MainPage mainPage;

    LogInPage logInPage;

    AllUsersPage allUsersPage;

    AllEventAdmins allEventAdmins;

    AllLocationsPage allLocationsPage;

    CreateLocationAdminPage createLocationAdminPage;

    @BeforeMethod
    public void setupSelenium(){
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
        allUsersPage = PageFactory.initElements(browser, AllUsersPage.class);
        allEventAdmins = PageFactory.initElements(browser, AllEventAdmins.class);
        allLocationsPage = PageFactory.initElements(browser, AllLocationsPage.class);
        createLocationAdminPage = PageFactory.initElements(browser, CreateLocationAdminPage.class);

    }

    @Test
    public void deleteUser() throws InterruptedException{

        //ensure main page is displayed
        WebDriverWait wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getMainPageLink()));

        assertEquals("http://localhost:4200/", browser.getCurrentUrl());

        //ensure login button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getLogInButton()));

        mainPage.getLogInButton().click();

        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

        logInPage.getUsernameInput().sendKeys("miki");
        logInPage.getPasswordInput().sendKeys("user");
        logInPage.getSingInButton().click();

        //ensure all users button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getAllUsersButton()));

        mainPage.getAllUsersButton().click();

        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(allUsersPage.getDeleteButton()));

        assertEquals("http://localhost:4200/read_delete_users", browser.getCurrentUrl());

        WebElement tableProducts = browser.findElement(By.id("mark"));
        List<WebElement> tableRows = new WebDriverWait(browser, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableProducts, By.tagName("tr")));

        int before = tableRows.size();

        allUsersPage.getDeleteButton().click();

        Thread.sleep(500);

        tableProducts = browser.findElement(By.id("mark"));
        tableRows = new WebDriverWait(browser, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableProducts, By.tagName("tr")));

        int after = tableRows.size();

        assertEquals(before-1, after);
    }

    @Test
    public void createLocationAdmin() throws InterruptedException{

        //ensure main page is displayed
        WebDriverWait wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getMainPageLink()));

        assertEquals("http://localhost:4200/", browser.getCurrentUrl());

        //ensure login button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getLogInButton()));

        mainPage.getLogInButton().click();

        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

        logInPage.getUsernameInput().sendKeys("miki");
        logInPage.getPasswordInput().sendKeys("user");
        logInPage.getSingInButton().click();

        //ensure all users button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getEventAdminsButton()));

        mainPage.getEventAdminsButton().click();

        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(allEventAdmins.getAddInput()));

        assertEquals("http://localhost:4200/read_delete_location_admin", browser.getCurrentUrl());

        WebElement tableProducts = browser.findElement(By.id("mark"));
        List<WebElement> tableRows = new WebDriverWait(browser, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableProducts, By.tagName("tr")));

        int before = tableRows.size();

        allEventAdmins.getAddInput().click();
        //ensure create admin page is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable((createLocationAdminPage.getCreateButtonInput())));
        assertEquals("http://localhost:4200/create_location_admin", browser.getCurrentUrl());

        createLocationAdminPage.getUsernameInput().sendKeys("Miskajlo6");
        createLocationAdminPage.getPasswordInput().sendKeys("misk");
        createLocationAdminPage.getEmailInput().sendKeys("miskajlo6@gmail.com");
        createLocationAdminPage.getFirstnameInput().sendKeys("Mihajlo");
        createLocationAdminPage.getLastnameInput().sendKeys("Jovkovic");
        createLocationAdminPage.getCreateButtonInput().click();

        Thread.sleep(500);
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(allEventAdmins.getDeleteButton()));

        assertEquals("http://localhost:4200/read_delete_location_admin", browser.getCurrentUrl());

        tableProducts = browser.findElement(By.id("mark"));
        tableRows = new WebDriverWait(browser, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableProducts, By.tagName("tr")));

        int after = tableRows.size();

        assertEquals(before+1, after);

    }

    @Test
    public void deleteEventAdmin() throws InterruptedException{

        //ensure main page is displayed
        WebDriverWait wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getMainPageLink()));

        assertEquals("http://localhost:4200/", browser.getCurrentUrl());

        //ensure login button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getLogInButton()));

        mainPage.getLogInButton().click();

        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

        logInPage.getUsernameInput().sendKeys("miki");
        logInPage.getPasswordInput().sendKeys("user");
        logInPage.getSingInButton().click();

        //ensure all users button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getEventAdminsButton()));

        mainPage.getEventAdminsButton().click();

        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(allEventAdmins.getDeleteButton()));

        assertEquals("http://localhost:4200/read_delete_location_admin", browser.getCurrentUrl());

        WebElement tableProducts = browser.findElement(By.id("mark"));
        List<WebElement> tableRows = new WebDriverWait(browser, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableProducts, By.tagName("tr")));

        int before = tableRows.size();

        allUsersPage.getDeleteButton().click();

        Thread.sleep(500);

        tableProducts = browser.findElement(By.id("mark"));
        tableRows = new WebDriverWait(browser, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableProducts, By.tagName("tr")));

        int after = tableRows.size();

        assertEquals(before-1, after);
    }

    @Test
    public void deleteLocation() throws InterruptedException{

        //ensure main page is displayed
        WebDriverWait wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getMainPageLink()));

        assertEquals("http://localhost:4200/", browser.getCurrentUrl());

        //ensure login button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getLogInButton()));

        mainPage.getLogInButton().click();

        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

        logInPage.getUsernameInput().sendKeys("miki");
        logInPage.getPasswordInput().sendKeys("user");
        logInPage.getSingInButton().click();

        //ensure all users button is displayed
        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getLocationsButton()));

        mainPage.getLocationsButton().click();

        wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.elementToBeClickable(allLocationsPage.getDeleteButton()));

        assertEquals("http://localhost:4200/read_delete_location", browser.getCurrentUrl());

        WebElement tableProducts = browser.findElement(By.id("mark"));
        List<WebElement> tableRows = new WebDriverWait(browser, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableProducts, By.tagName("tr")));

        int before = tableRows.size();

        allUsersPage.getDeleteButton().click();

        Thread.sleep(500);

        tableProducts = browser.findElement(By.id("mark"));
        tableRows = new WebDriverWait(browser, 10)
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableProducts, By.tagName("tr")));

        int after = tableRows.size();

        assertEquals(before-1, after);
    }

    @AfterMethod
    public void closeSelenium(){
        browser.close();
    }




}
