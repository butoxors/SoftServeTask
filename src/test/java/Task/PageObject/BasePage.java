package Task.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage implements IPage{
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    @Override
    public void init(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 3);
        actions = new Actions(driver);
    }

    public WebElement getElement(By by){
        try{
            WebElement expectedUsername = wait.until(ExpectedConditions.elementToBeClickable(by));
            return  expectedUsername;
        }catch (Exception e){
            return null;
        }
    }
}
