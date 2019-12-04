package Task.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class PostPage extends BasePage{
    @FindBy(xpath = "//div[contains(@class, 'Post')]")
    private WebElement _mainPost;

    @FindBy(xpath = "//div[@role='textbox']")
    private WebElement _commentField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement _sendCommentBtn;

    @FindAll({
            @FindBy(xpath = "//div[contains(@class, 'Comment')]")
    })
    private List<WebElement> _actualComments;

    private By upVote = By.xpath(".//button[@aria-label='upvote' and @data-click-id='upvote']");
    private By downVote = By.xpath(".//button[@aria-label='downvote' and @data-click-id='downvote']");

    public void createComment(String commentText) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(_commentField));

        actions.moveToElement(_commentField).click().sendKeys(commentText).build().perform();

        //_sendCommentBtn.click();
    }

    public WebElement getCommentByUsername(String username){
        for (WebElement comment:
                _actualComments) {

            List<WebElement> commentInfo = comment.findElements(By.xpath(".//a"));

            System.out.println("Exist user: " + commentInfo.get(0).getText()
            + "; his comment time: " + commentInfo.get(1).getText());
        }
        return null;
    }

    public String upVote() {
        WebElement upVoteElement = _mainPost.findElement(upVote);

        upVoteElement.click();
        System.out.println(upVoteElement.getAttribute("aria-pressed"));
        return upVoteElement.getAttribute("aria-pressed");
    }

    public String downVote(){
        WebElement downVoteElement = _mainPost.findElement(downVote);

        downVoteElement.click();
        System.out.println(downVoteElement.getAttribute("aria-pressed"));
        return downVoteElement.getAttribute("aria-pressed");
    }
}
