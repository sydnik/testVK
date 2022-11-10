package tests;

import aquality.selenium.core.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FeedPage;
import pages.MainPage;
import pages.ProfilePage;
import utils.ImageUtil;
import utils.JsonUtil;
import utils.StringUtil;
import utils.VkApiUtils;

import java.util.List;

public class VkTest extends BaseTest{

    @Test
    public void testVkWall(){
        Logger.getInstance().info("Start test NAME with parameters:\n");
        Logger.getInstance().info("Step 1: open " + JsonUtil.getConfiguration("urlForBrowser"));
        int userId = JsonUtil.getInt("id");
        getBrowser().goTo(JsonUtil.getConfiguration("urlForBrowser"));
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.state().isDisplayed(), "Open main page");

        Logger.getInstance().info("Step 2: signIn");
        mainPage.signIn(JsonUtil.getString("login"), JsonUtil.getString("password"));
        FeedPage feedPage = new FeedPage();
        Assert.assertTrue(feedPage.state().waitForDisplayed(), "open feed page");

        Logger.getInstance().info("Step 3: go to \"My profile\"");
        feedPage.clickProfile();
        ProfilePage profilePage = new ProfilePage();
        Assert.assertTrue(profilePage.state().isDisplayed(), "open My profile");

        Logger.getInstance().info("Step 4: add text on the wall");
        String randomText = StringUtil.randomText();
        int postId = VkApiUtils.sendPostOnWall(userId, randomText);

        Logger.getInstance().info("Step 5: check post with text \"" + randomText + "\"");
        Assert.assertEquals(profilePage.getTextFromPost(userId, postId), randomText, "check text from post");

        Logger.getInstance().info("Step 6: edit post and add photo");
        randomText = StringUtil.randomText();
        VkApiUtils.editPostAndAddPhotoOnWall(userId, postId, randomText, JsonUtil.getString("photoForUpload"));

        Logger.getInstance().info("Step 7: check text and photo after changes");
        getBrowser().refresh();
        Assert.assertEquals(profilePage.getTextFromPost(userId, postId), randomText , "check text after changes");
        Assert.assertTrue(ImageUtil.equalsImage(JsonUtil.getString("photoForUpload"), profilePage.getUrlsOfImage(userId, postId)),
                "check images with disk and wall");
        getBrowser().getScreenshot();
        Logger.getInstance().info("Step 8: add comment to post " + postId);
        randomText = StringUtil.randomText();
        int commentId = VkApiUtils.sendComment(postId, userId, randomText);

        Logger.getInstance().info("Step 9: check comment with text \"" + randomText + "\"");
        Assert.assertEquals(profilePage.getTextCommentUnderPost(userId, postId, commentId), randomText);

        Logger.getInstance().info("Step 10: like on the post " + postId);
        profilePage.likePost(postId);

        Logger.getInstance().info("Step 11: check like on the post " + postId);
        List<Integer> users = VkApiUtils.getUsersWhoLikePost(userId, postId);
        Assert.assertTrue(users.contains(userId), "has post like from user " + userId);

        Logger.getInstance().info("Step 12: delete post " + postId);
        VkApiUtils.deletePost(userId, postId);

        Logger.getInstance().info("Step 13: check deleted post" + postId);
        Assert.assertTrue(profilePage.hasPost(userId, postId));}
}
