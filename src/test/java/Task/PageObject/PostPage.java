package Task.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.SimpleDateFormat;
import java.util.*;

public class PostPage extends BasePage{
    @FindBy(xpath = "//div[contains(@class, 'Post')]")
    private WebElement _mainPost;

    @FindBy(xpath = "//div[@role='textbox']")
    private WebElement _commentField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement _sendCommentBtn;

    @FindBy(xpath = "//button[@title='Close']")
    private  WebElement _closePost;

    @FindAll({
            @FindBy(xpath = "//div[contains(@class, 'Comment')]")
    })
    private List<WebElement> _actualComments;

    @FindAll({
                @FindBy(xpath = "//div[@class='_1E9mcoVn4MYnuBQSVDt1gC']")
    })
    private List<WebElement> _voteBlocks;

    @FindBy(xpath = "//button[@role='menuitem' and contains(@id, 'Sort')]")
    private WebElement _sortBtn;

    private By _sortMenus = By.xpath("//div[@role='menu']//a[contains(@href, 'new')]");
    private By _commentAuthor = By.xpath(".//a[contains(@href, '/user/')]");
    private By _commentTimePosted = By.xpath(".//*[text()[contains(., 'ago')]]");
    private By _actualCommentTime = By.xpath("//div[@class='HQ2VJViRjokXpRbJzPvvc']");



    private By _upVoteXPath = By.xpath(".//button[@aria-label='upvote' and contains(@id, 'upvote-button')]");
    private By _downVoteXPath = By.xpath(".//button[@aria-label='downvote' and @data-click-id='downvote']");

    private JavascriptExecutor js;

    public void createComment(String commentText) {
        try {
            wait.until(ExpectedConditions.visibilityOf(_commentField));

            /*actions.moveToElement(_commentField).click()
                    .sendKeys(commentText)
                    .moveToElement(_sendCommentBtn)
                    .click().build().perform();*/
        }catch (Exception ex){}
    }

    public Map<String, String> getCommentByUsername(String username) {
        Map<String, String> userInfo = new HashMap<>();

        wait.until(ExpectedConditions.elementToBeClickable(_sortBtn));
        actions.moveToElement(_sortBtn).click().pause(3000).build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(_sortMenus));

        actions.moveToElement(driver.findElement(_sortMenus))
                .click().build().perform();

        for (WebElement comment:
                _actualComments) {

            String author = comment.findElement(_commentAuthor).getText().trim().toLowerCase();

            WebElement commentTime = comment.findElement(_commentTimePosted);

            actions.moveToElement(commentTime).clickAndHold().pause(3).build().perform();

            String time = driver.findElement(_actualCommentTime).getAttribute("innerText");

            time = time.substring(0, time.indexOf('G') - 4);

            if (author.contains(username)){
                userInfo.put(author, " '" + time + "'");
            }
        }
        return userInfo;
    }

    public boolean upVote() {
        return pushVoteBtn(0, _upVoteXPath);
    }

    public boolean downVote(){
        return pushVoteBtn(0, _downVoteXPath);
    }

    private boolean pushVoteBtn(int index, By by){
        WebElement voteBtn = _voteBlocks.get(index).findElement(by);

        js.executeScript("arguments[0].click()", voteBtn);
        Boolean status = Boolean.valueOf(voteBtn.getAttribute("aria-pressed"));

        System.out.println("Post has voted, result: " + status.toString());

        return status;
    }

    @Override
    public void init(WebDriver driver) {
        super.init(driver);
        js = (JavascriptExecutor) driver;
    }
}
