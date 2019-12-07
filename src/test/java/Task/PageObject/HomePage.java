package Task.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

    @FindAll({
            @FindBy(xpath = "//div[@class='_1E9mcoVn4MYnuBQSVDt1gC']")
    })
    private List<WebElement> _voteBlocks;

    private By _upVoteXPath = By.xpath(".//button[@aria-label='upvote' and contains(@id, 'upvote-button')]");
    private By _downVoteXPath = By.xpath(".//button[@aria-label='downvote' and @data-click-id='downvote']");

    private String _homeURL = "https://www.reddit.com/";

    public int postCount(){
        return _posts.size();
    }

    public String getPostId(int index){
        return _posts.get(index).getAttribute("id").substring(3);
    }

    public void selectPost(int index){
        actions.moveToElement(_posts.get(index).findElement(By.tagName("h3"))).click().build().perform();
    }

    public String getActualUsername(){
        wait.until(ExpectedConditions.elementToBeClickable(_actualUsername));

        return _actualUsername.getAttribute("innerText");
    }

    public boolean upVote() {
        return pushVoteBtn(0, _upVoteXPath);
    }

    public boolean downVote(){
        return pushVoteBtn(0, _downVoteXPath);
    }

    private boolean pushVoteBtn(int index, By by){
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));

        WebElement voteBtn = _voteBlocks.get(index).findElement(by);

        js.executeScript("arguments[0].click()", voteBtn);

        Boolean status = Boolean.valueOf(voteBtn.getAttribute("aria-pressed"));

        System.out.println("Post has voted, result: " + status.toString());

        return status;
    }
}
