package Task.PageObject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LogInPage extends BasePage{
    private String _logInURL = "https://www.reddit.com/login/";

    @FindBy(xpath = "//input[@name='username']")
    private WebElement _usernameField;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement _passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement _logInBtn;

    public void logIn(String username, String password){
        try {
            wait.until(ExpectedConditions.visibilityOf(_usernameField));
        }catch (Exception e){}
        _usernameField.sendKeys(username);
        _passwordField.sendKeys(password);

        _logInBtn.click();
    }

    public void goToLogInPage(){
        driver.get(_logInURL);
    }

}
