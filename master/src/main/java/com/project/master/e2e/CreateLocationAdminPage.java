package com.project.master.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateLocationAdminPage {

    private WebDriver driver;

    @FindBy(id ="firstname")
    private WebElement firstnameInput;

    @FindBy(id = "lastname")
    private WebElement lastnameInput;


    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "create_button")
    private WebElement createButtonInput;

    public WebElement getFirstnameInput() {
        return firstnameInput;
    }

    public void setFirstnameInput(WebElement firstnameInput) {
        this.firstnameInput = firstnameInput;
    }

    public WebElement getLastnameInput() {
        return lastnameInput;
    }

    public void setLastnameInput(WebElement lastnameInput) {
        this.lastnameInput = lastnameInput;
    }

    public WebElement getUsernameInput() {
        return usernameInput;
    }

    public void setUsernameInput(WebElement usernameInput) {
        this.usernameInput = usernameInput;
    }

    public WebElement getEmailInput() {
        return emailInput;
    }

    public void setEmailInput(WebElement emailInput) {
        this.emailInput = emailInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public void setPasswordInput(WebElement passwordInput) {
        this.passwordInput = passwordInput;
    }

    public WebElement getCreateButtonInput() {
        return createButtonInput;
    }

    public void setCreateButtonInput(WebElement createButtonInput) {
        this.createButtonInput = createButtonInput;
    }

    public CreateLocationAdminPage(WebDriver driver) {
        this.driver = driver;


    }
}
