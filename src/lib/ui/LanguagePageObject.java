package lib.ui;

import io.appium.java_client.AppiumDriver;

public class LanguagePageObject extends MainPageObject {

    public LanguagePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    private static final String LANG_BUTTON = "id:org.wikipedia:id/search_lang_button";

    public void waitForLangButtonToAppear() {
        this.waitForElementPresent(
                LANG_BUTTON,
                "The Lang Button cannot be found using '" + LANG_BUTTON + "'",
                5
        );
    }

    public void waitToLangButtonToDisappear() {
        this.waitForElementNotPresent(
                LANG_BUTTON,
                "The Lang Button is still present on the screen",
                5
        );
    }
}
