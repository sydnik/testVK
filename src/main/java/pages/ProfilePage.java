package pages;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ProfilePage extends Form {

    public ProfilePage() {
        super(By.id("narrow_column"), "Profile Page");
    }

    public String getTextFromPost(int userId, int postId){
        String loc = String.format("//*[@class='wall_text']//div[contains(@id,'%s_%s')]", userId, postId);
        ILabel postLbl = getElementFactory().getLabel(By.xpath(loc), "Text Post " + postId);
        return postLbl.getText();
    }

    public String getTextCommentUnderPost(int userId, int postId, int commentId){
        String loc = String.format("//*[contains(@id,'%d')]//*[@class='reply_text']//div[contains(@id,'%d_%d')]", postId, userId, commentId);
        ILabel postLbl = getElementFactory().getLabel(By.xpath(loc), "Text comment " + postId );
        postLbl.state().waitForDisplayed();
        return postLbl.getText();
    }

    public void likePost(int postId){
        String loc = String.format("//div[contains(@data-reaction-target-object,'%d')]", postId);
        IButton likeBtn = getElementFactory().getButton(By.xpath(loc), "likeBtn idPost=" + postId);
        if(!likeBtn.getAttribute("class").contains("PostButtonReactions--active")){
            likeBtn.click();
        }
    }

    public boolean hasPost(int userId, int postId){
        String loc = String.format("post%d_%d", userId, postId);
        ITextBox post = getElementFactory().getTextBox(By.id(loc), "post"+postId);
        return post.state().waitForNotDisplayed();
    }

    public String getUrlsOfImage(int userId, int postId){
        String loc = String.format("//div[@id='post%d_%d']//a[@data-photo-id]", userId, postId);
        ILabel pic = getElementFactory().getLabel(By.xpath(loc), "pic");
        return pic.getAttribute("onclick");
    }
}
