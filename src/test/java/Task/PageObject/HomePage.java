package Task.PageObject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage{
    @FindAll({
            @FindBy(xpath = "//div[contains(@class, 'Post')]")
    })
    private List<WebElement> _posts;

    private String _homeURL = "https://www.reddit.com/";

    public void hoHome(){
        driver.get(_homeURL);
    }

    public int postCount(){
        return _posts.size();
    }

    public String getPostId(int index){
        return _posts.get(index).getAttribute("id").substring(3);
    }

    public void selectPost(int index){
        Actions actions = new Actions(driver);
        actions.moveToElement(_posts.get(index)).click().build().perform();
    }
}
