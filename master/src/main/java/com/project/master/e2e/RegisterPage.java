package com.project.master.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RegisterPage {

    private WebDriver webDriver;

    @FindBy(id = "//*[@id=\"username\"]")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "lastName")
    private WebElement lastNameInput;

    @FindBy(id = "create_button")
    private WebElement createButton;

    public RegisterPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement getUsernameInput() {
        return usernameInput;
    }

    public void setUsernameInput(WebElement usernameInput) {
        this.usernameInput = usernameInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public void setPasswordInput(WebElement passwordInput) {
        this.passwordInput = passwordInput;
    }

    public WebElement getEmailInput() {
        return emailInput;
    }

    public void setEmailInput(WebElement emailInput) {
        this.emailInput = emailInput;
    }

    public WebElement getNameInput() {
        return nameInput;
    }

    public void setNameInput(WebElement nameInput) {
        this.nameInput = nameInput;
    }

    public WebElement getLastNameInput() {
        return lastNameInput;
    }

    public void setLastNameInput(WebElement lastNameInput) {
        this.lastNameInput = lastNameInput;
    }

    public WebElement getCreateButton() {
        return createButton;
    }

    public void setCreateButton(WebElement createButton) {
        this.createButton = createButton;
    }
}
