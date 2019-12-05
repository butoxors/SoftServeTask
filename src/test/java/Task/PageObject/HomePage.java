package Task.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage{
    @FindAll({
            @FindBy(xpath = "//div[contains(@class, 'Post')]")
    })
    private List<WebElement> _posts;

    @FindBy(xpath = "//span[@class='_2BMnTatQ5gjKGK5OWROgaG']")
    private WebElement _actualUsername;

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
        //actions.moveToElement(_posts.get(index).findElement(By.tagName("h3"))).click().build().perform();
        driver.get("https://www.reddit.com/r/DotA2/comments/e6fr7q/valve_i_think_its_time/");
    }

    public String getActualUsername(){
        wait.until(ExpectedConditions.elementToBeClickable(_actualUsername));

        return _actualUsername.getAttribute("innerText");
    }
}
