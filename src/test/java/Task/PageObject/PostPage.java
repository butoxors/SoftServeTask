package Task.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PostPage extends BasePage{
    @FindBy(xpath = "//div[@role='textbox']")
    private WebElement _commentField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement _sendCommentBtn;

    @FindAll({
            @FindBy(xpath = "//div[@class='_3tw__eCCe7j-epNCKGXUKk ']")
    })
    private List<WebElement> _actualComments;
    private By commentXPath = By.xpath("//div[@class='_2mHuuvyV9doV3zwbZPtIPG']");

    public void createComment(String commentText){
        _commentField.click();
        _commentField.sendKeys(commentText);

        _sendCommentBtn.click();
    }

    public WebElement getCommentByUsername(String username){
        System.out.println(_actualComments.size());
        for (WebElement comment:
             _actualComments) {
            WebElement e = comment.findElement(commentXPath);
            System.out.println("Exist user: " + e.getAttribute("innerText"));
            if (e.getAttribute("innerText") == username)
                return e;
        }
        return null;
    }
}
