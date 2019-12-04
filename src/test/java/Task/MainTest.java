package Task;

import Task.DriverFactory.DriverFactory;
import Task.DriverFactory.DriverType;
import Task.Helpers.FileHelper;
import Task.Helpers.GsonHelper;
import Task.Models.User;
import Task.PageObject.HomePage;
import Task.PageObject.LogInPage;
import Task.PageObject.PostPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class MainTest {
    private LogInPage _logInPage;
    private HomePage _homePage;
    private PostPage _postPage;

    private WebDriver _driver;

    private User u;

    @BeforeSuite
    public void setUp() throws Exception {
        _driver = DriverFactory.getDriver(DriverType.CHROME);
        _driver.manage().window().maximize();

        u = GsonHelper.deserialize(FileHelper.read());
    }


    @Test//(dataProvider = "getOptions", dataProviderClass = DataProviders.class)
    public void logInCase(){

        _logInPage = new LogInPage();
        _logInPage.init(_driver);
        _logInPage.goToLogInPage();
        _logInPage.logIn(u.getUsername(), u.getPassword());

        String expected = "";

        expected = _logInPage.getElement(By.xpath("//span[text()='" + u.getUsername().toLowerCase() + "']")).getAttribute("innerText");

        Assert.assertEquals(
                u.getUsername().toLowerCase(),
                expected);
        System.out.println(
                "Actual username: " + u.getUsername().toLowerCase()
                + "\r\nExpected username: " + expected);
    }

    @Test
    public void seeSubscribedsSubreddits(){
        _homePage = new HomePage();
        _homePage.init(_driver);
        //_homePage.hoHome();

        int countOfPosts = _homePage.postCount();

        Assert.assertTrue(countOfPosts > 0);
        System.out.println("Count of posts: " + countOfPosts);
    }

    @Test
    public void viewOneOfMySubscribedSubreddits(){
        int randomPost = 1;
        System.out.println("Number of post is: '" + randomPost + "'");

        String expectedPost = _homePage.getPostId(randomPost);

        _homePage.selectPost(randomPost);

        String currentUrl = _driver.getCurrentUrl();
        System.out.println("Current id is " + expectedPost + "\r\nLink: " + currentUrl);
        Assert.assertTrue(currentUrl.contains(expectedPost));
    }

    @Test(priority = 1)
    public void createComment(){
        _postPage = new PostPage();
        _postPage.init(_driver);

        try {
            _postPage.createComment(":D");
        }catch (Exception ex){}
        WebElement post = _postPage.getCommentByUsername(u.getUsername().toLowerCase());
        if (post != null)
            System.out.println(post.getAttribute("outerHTML"));
        else
            System.out.println("User hasn't found!");
    }

    @Test(priority = 2)
    public void upVotePost() {
        String actionResult = _postPage.upVote();
        Assert.assertTrue(Boolean.valueOf(actionResult));
    }

    @Test(priority = 2)
    public void downVotePost(){
        String actionResult = _postPage.downVote();
        Assert.assertTrue(Boolean.valueOf(actionResult));
    }

    @AfterSuite
    public final void tearDown(){
        _driver.quit();
    }
}
