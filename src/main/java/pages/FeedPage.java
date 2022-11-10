package pages;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class FeedPage extends Form {
    private final IButton profileBtn = getElementFactory().getButton(By.id("l_pr"), "MyProfileButton");

    public FeedPage() {
        super(By.id("feed_rows"), "Feed Page");
    }

    public void clickProfile() {
        profileBtn.click();
    }
}
