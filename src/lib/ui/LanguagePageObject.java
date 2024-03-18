package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class LanguagePageObject extends MainPageObject {

    public LanguagePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    private static final String LANG_BUTTON_ID = "org.wikipedia:id/search_lang_button";

    public void waitForLangButtonToAppear() {
        this.waitForElementPresent(
                LANG_BUTTON_ID,
                "The Lang Button cannot be found using '" + LANG_BUTTON_ID + "'",
                5
        );
    }

    public void waitToLangButtonToDisappear() {
        this.waitForElementNotPresent(
                LANG_BUTTON_ID,
                "The Lang Button is still present on the screen",
                5
        );
    }
}
