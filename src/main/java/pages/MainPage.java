package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MainPage extends Form {
    private final ITextBox loginTextBox = getElementFactory().getTextBox(By.id("index_email"), "LoginTextBox");
    private final ITextBox passwordTextBox = getElementFactory().getTextBox(By.id("index_pass"), "PasswordTextBox");
    private final IButton signInButton = getElementFactory().getButton(By.id("index_login_button"), "SignInButton");

    public MainPage() {
        super(By.id("index_rcolumn"), "Main page");
    }

    public void signIn(String login, String password){
        sendLogin(login);
        sendPassword(password);
        clickSignIn();
        AqualityServices.getBrowser().getDriver().
    }

    public void sendLogin(String login){
        loginTextBox.sendKeys(login);
    }

    public void sendPassword(String password){
        passwordTextBox.sendKeys(password);
    }

    public void clickSignIn(){
        signInButton.click();
    }
}
