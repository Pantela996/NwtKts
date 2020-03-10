package com.project.master.e2e;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver driver;

    @FindBy(id = "main_page_link")
    private WebElement mainPageLink;

    @FindBy(id = "allUsers")
    private WebElement allUsersButton;

    @FindBy(id = "eventAdmins")
    private WebElement eventAdminsButton;

    @FindBy(id = "locations")
    private WebElement locationsButton;

    @FindBy(id = "createLocations")
    private WebElement createLocationsButton;

    @FindBy(id = "createEvent")
    private WebElement createEventButton;

    @FindBy(id = "login")
    private WebElement logInButton;

    @FindBy(id = "logout")
    private WebElement logoutButton;


    public WebElement getLogInButton() {
        return logInButton;
    }

    public void setLogInButton(WebElement logInButton) {
        this.logInButton = logInButton;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getMainPageLink() {
        return mainPageLink;
    }

    public void setMainPageLink(WebElement mainPageLink) {
        this.mainPageLink = mainPageLink;
    }

    public WebElement getAllUsersButton() {
        return allUsersButton;
    }

    public void setAllUsersButton(WebElement allUsersButton) {
        this.allUsersButton = allUsersButton;
    }

    public WebElement getEventAdminsButton() {
        return eventAdminsButton;
    }

    public void setEventAdminsButton(WebElement eventAdminsButton) {
        this.eventAdminsButton = eventAdminsButton;
    }

    public WebElement getLocationsButton() {
        return locationsButton;
    }

    public void setLocationsButton(WebElement locationsButton) {
        this.locationsButton = locationsButton;
    }

    public WebElement getCreateLocationsButton() {
        return createLocationsButton;
    }

    public void setCreateLocationsButton(WebElement createLocationsButton) {
        this.createLocationsButton = createLocationsButton;
    }

    public WebElement getLogoutButton() {
        return logoutButton;
    }

    public void setLogoutButton(WebElement logoutButton) {
        this.logoutButton = logoutButton;
    }

    public WebElement getCreateEventButton() {
        return createEventButton;
    }

    public void setCreateEventButton(WebElement createEventButton) {
        this.createEventButton = createEventButton;
    }

    public void ensureIsDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(mainPageLink));
    }

    public void ensureLoginIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(logInButton));
    }



}
