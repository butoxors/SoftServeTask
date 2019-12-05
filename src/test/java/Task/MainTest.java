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
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class MainTest {
    private LogInPage _logInPage;
    private HomePage _homePage;
    private PostPage _postPage;

    private WebDriver _driver;

    private User localUser;

    @BeforeSuite
    public void setUp() throws Exception {
        _driver = DriverFactory.getDriver(DriverType.CHROME);
        _driver.manage().window().maximize();

        localUser = GsonHelper.deserialize(FileHelper.read());

    }


    @Test//(dataProvider = "getOptions", dataProviderClass = DataProviders.class)
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

    @Test
    public void seeSubscribedsSubreddits(){
        _homePage = new HomePage();
        _homePage.init(_driver);

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
    public void createComment() {
        _postPage = new PostPage();
        _postPage.init(_driver);

        try {
            _postPage.createComment(":D");
        }catch (Exception ex){}
        Map<String, String> userInfo = _postPage.getCommentByUsername(localUser.getUsername().toLowerCase());

        Assert.assertNotNull(userInfo);

        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US);
        Date date = new Date(System.currentTimeMillis());
        String currentDate = format.format(date) + "'" + localUser.getUsername().toLowerCase() + "'";

        for (Map.Entry<String, String> info: userInfo.entrySet()){
            System.out.println("User: " + info.getKey() + "; Time to posted comment: " + info.getValue());
            Assert.assertEquals(currentDate, info.getValue());
        }

    }

    @Test(priority = 2)
    public void upVotePost() {
        boolean actionResult = _postPage.upVote();
        Assert.assertTrue(actionResult);
    }

    @Test(priority = 3)
    public void downVotePost() {
        boolean actionResult = _postPage.downVote();
        Assert.assertTrue(actionResult);
    }

    @AfterSuite
    public final void tearDown(){
        _driver.quit();
    }
}
