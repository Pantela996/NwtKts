package com.project.master.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LocationPage {

    private WebDriver driver;

    @FindBy(id ="name")
    private WebElement nameInput;

    @FindBy(id = "cityLocation")
    private WebElement cityLocationInput;


    @FindBy(id = "NoH")
    private WebElement nohInput;

    @FindBy(id = "create_button")
    private WebElement getCreateButtonInput;

    public LocationPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getNameInput() {
        return nameInput;
    }

    public void setNameInput(WebElement nameInput) {
        this.nameInput = nameInput;
    }

    public WebElement getCityLocationInput() {
        return cityLocationInput;
    }

    public void setCityLocationInput(WebElement cityLocationInput) {
        this.cityLocationInput = cityLocationInput;
    }

    public WebElement getNohInput() {
        return nohInput;
    }

    public void setNohInput(WebElement nohInput) {
        this.nohInput = nohInput;
    }

    public WebElement getGetCreateButtonInput() {
        return getCreateButtonInput;
    }

    public void setGetCreateButtonInput(WebElement getCreateButtonInput) {
        this.getCreateButtonInput = getCreateButtonInput;
    }
}
