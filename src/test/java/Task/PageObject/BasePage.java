package Task.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage implements IPage{
    protected WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(xpath = "//a[text()='log in']")
    private WebElement _logInNavBtn;

    private final String _homeUrl = "https://www.reddit.com/";

    public void goToReddit(){
        driver.get(_homeUrl);
    }
    public void clickLogIn(){
        _logInNavBtn.click();
    }
    @Override
    public void init(WebDriver driver) {
        PageFactory.initElements(driver, this);

        this.driver = driver;
        this.driver.manage().window().maximize();

        wait = new WebDriverWait(this.driver, 3);
    }
}
