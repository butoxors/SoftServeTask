package Task;

import Task.DriverFactory.DriverFactory;
import Task.DriverFactory.DriverType;
import Task.Helpers.FileHelper;
import Task.Helpers.GsonHelper;
import Task.Helpers.dateTimeHelper;
import Task.Models.User;
import Task.PageObject.HomePage;
import Task.PageObject.LogInPage;
import Task.PageObject.PostPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Map;

public class MainTest {
    private LogInPage _logInPage;
    private HomePage _homePage;
    private PostPage _postPage;

    private WebDriver _driver;

    private User localUser;

    private String _commentText = ":D";

    @BeforeSuite (groups = "UI")
    public void setUp() throws Exception {
        _driver = DriverFactory.getDriver(DriverType.CHROME);
        _driver.manage().window().maximize();

        localUser = GsonHelper.deserialize(FileHelper.read());

    }

    @Test(groups = "UI")//(dataProvider = "getOptions", dataProviderClass = DataProviders.class)
    public void logInCase(){
        _logInPage = new LogInPage();
        _logInPage.init(_driver);
        _logInPage.goToLogInPage();
        _logInPage.logIn(localUser.getUsername(), localUser.getPassword());

        _homePage = new HomePage();
        _homePage.init(_driver);
        String actualUsername = _homePage.getActualUsername();

        Assert.assertEquals(
                localUser.getUsername().toLowerCase(),
                actualUsername);

        System.out.println(
                "Actual username: " + localUser.getUsername().toLowerCase()
                + "\r\nExpected username: " + actualUsername);
    }

    @Test(groups = "UI")
    public void seeSubscribedSubreddits() {
        _homePage = new HomePage();
        _homePage.init(_driver);

        int countOfPosts = _homePage.postCount();

        Assert.assertTrue(countOfPosts > 0);
        System.out.println("Count of posts: " + countOfPosts);
    }

    @Test(dependsOnMethods = "seeSubscribedSubreddits", groups = "UI")
    public void upVotePost() {
        boolean actionResult = _homePage.upVote();
        Assert.assertTrue(actionResult);
    }

    @Test(dependsOnMethods = "seeSubscribedSubreddits", groups = "UI")
    public void downVotePost() {
        boolean actionResult = _homePage.downVote();
        Assert.assertTrue(actionResult);
    }

    @Test(priority = 1, groups = "UI")
    public void viewOneOfMySubscribedSubreddits(){
        int postNumber = 0;
        System.out.println("Number of post is: '" + postNumber + 1 + "'");

        String expectedPost = _homePage.getPostId(postNumber);

        _homePage.selectPost(postNumber);

        String currentUrl = _driver.getCurrentUrl();
        System.out.println("Current `id` is `" + expectedPost + "`\r\nLink: " + currentUrl);
        Assert.assertTrue(currentUrl.contains(expectedPost));
    }

    @Test(priority = 2, groups = "UI")
    public void createComment() {
        _postPage = new PostPage();
        _postPage.init(_driver);

        _postPage.createComment(_commentText);

        String currentInfo = dateTimeHelper.generateCurrentTime() + " '" + localUser.getUsername().toLowerCase() + "'";

        Map<String, String> userInfo = _postPage.getCommentsByUsername(localUser.getUsername().toLowerCase());

        for (Map.Entry<String, String> info : userInfo.entrySet()){
            String commentInfo = dateTimeHelper.makeCommentInfo(info.getKey()) + info.getValue();


            System.out.println("Current time : " + currentInfo);

            System.out.println("Comment time : " + commentInfo);

            Assert.assertEquals(currentInfo, commentInfo);
        }

        Assert.assertTrue(userInfo.size() > 0);
    }

    @AfterSuite(groups = "UI")
    public final void tearDown(){
        _driver.quit();
    }
}
