package com.project.master.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AllLocationsPage {

    private WebDriver driver;

    @FindBy(id = "delete")
    private WebElement deleteButton;

    @FindBy(id = "mark")
    private WebElement row;

    public AllLocationsPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(WebElement deleteButton) {
        this.deleteButton = deleteButton;
    }

    public WebElement getRow() {
        return row;
    }

    public void setRow(WebElement row) {
        this.row = row;
    }
}
