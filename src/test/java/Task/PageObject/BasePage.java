package Task.PageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage implements IPage{
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor js;

    @Override
    public void init(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 5);
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }
}
