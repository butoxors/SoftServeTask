package Task.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;

public class PostPage extends BasePage{
    @FindBy(xpath = "//div[contains(@class, 'Post')]")
    private WebElement _mainPost;

    @FindBy(xpath = "//div[@role='textbox']")
    private WebElement _commentField;

    @FindBy(xpath = "//button[@type='submit' and text()='comment']")
    private WebElement _sendCommentBtn;

    @FindAll({
            @FindBy(xpath = "//div[contains(@class, 'Comment')]")
    })
    private List<WebElement> _actualComments;

    private By _commentAuthor = By.xpath(".//a[contains(@href, '/user/')]");
    private By _commentTimePosted = By.xpath(".//a[@class='_1sA-1jNHouHDpgCp1fCQ_F']");
    private By _tooltipCommentTime = By.xpath("//div[@class='HQ2VJViRjokXpRbJzPvvc']");

    public void createComment(String commentText) {
        wait.until(ExpectedConditions.visibilityOf(_commentField));

        WebElement postBottom = driver.findElement(By.xpath("//div[@class='_3-miAEojrCvx_4FQ8x3P-s']"));

        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth'});", postBottom);

        actions
                .moveToElement(_commentField)
                .click().sendKeys(commentText)
                .pause(1000)
                .click(_sendCommentBtn)
                .pause(1000)
                .build().perform();
    }

    public Map<String, String> getCommentsByUsername(String username) {
        Map<String, String> userInfo = new HashMap<>();

        System.out.println("Post is have " + _actualComments.size() + " comments");

        for (WebElement comment:
                _actualComments) {

            String author = comment.findElement(_commentAuthor).getText().trim().toLowerCase();

            if (author.contains(username)){
                System.out.println("User " + username + " has found");
                WebElement commentTime = comment.findElement(_commentTimePosted);

                actions.moveToElement(comment).moveToElement(commentTime).clickAndHold().pause(1000).build().perform();

                String time = driver.findElement(_tooltipCommentTime).getAttribute("innerText");

                time = time.substring(0, time.indexOf('G') - 4);

                userInfo.put(time, " '" + author + "'");
            }
        }
        return userInfo;
    }

}
