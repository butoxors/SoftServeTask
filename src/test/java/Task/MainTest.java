package Task;

import Task.DriverFactory.DriverFactory;
import Task.DriverFactory.DriverType;
import Task.PageObject.BasePage;
import Task.PageObject.LogInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class MainTest {
    private LogInPage _logInPage;
    private WebDriver _driver;
    private BasePage _mainPage;

    @Test//(dataProvider = "getOptions", dataProviderClass = DataProviders.class)
    public void logInCase() {
        _driver = DriverFactory.getDriver(DriverType.CHROME);

        _mainPage = new BasePage();
        _mainPage.init(_driver);
        _mainPage.goToReddit();
        _mainPage.clickLogIn();

        /*_logInPage = new LogInPage();
        _logInPage.init(_driver);
        _logInPage.logIn("Butoxors", "pass123");*/
        WebDriverWait wait = new WebDriverWait(_driver, 3);
        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='username']")));
        }catch (Exception ex){}
        WebElement u = _driver.findElement(By.xpath("//input[@name='username']"));
                u.sendKeys("Butoxors");

    }
    /*@AfterMethod
    public final void tearDown(){
        _driver.quit();
    }*/
}
