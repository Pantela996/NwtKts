package com.project.master.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogInPage {

    private WebDriver driver;

    @FindBy(id ="username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;


    @FindBy(id = "sign_in")
    private WebElement singInButton;

    @FindBy(id = "register")
    private WebElement signUpButton;

    @FindBy(id = "errorMsg")
    private WebElement errorMessage;

    public LogInPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getSignUpButton() {
        return signUpButton;
    }

    public void setSignUpButton(WebElement signUpButton) {
        this.signUpButton = signUpButton;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
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

    public WebElement getSingInButton() {
        return singInButton;
    }

    public void setSingInButton(WebElement singInButton) {
        this.singInButton = singInButton;
    }

    public WebElement getRegisterButton() {
        return signUpButton;
    }

    public void setRegisterButton(WebElement registerButton) {
        this.signUpButton = registerButton;
    }

    public WebElement getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(WebElement errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void ensureIsDisplyed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(usernameInput));
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(passwordInput));
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(singInButton));
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(signUpButton));

    }

    public void ensureErrorIsDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(errorMessage));
    }


}
