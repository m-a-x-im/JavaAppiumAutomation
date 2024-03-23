package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.WelcomePageObject;

public class AndroidWelcomePageObject extends WelcomePageObject {

    public AndroidWelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    static {
        FIRST_PAGE_LINK = "id:org.wikipedia:id/addLanguageButton";
        NEXT_BUTTON = "id:org.wikipedia:id/fragment_onboarding_forward_button";
        SECOND_PAGE_TITLE = "id:org.wikipedia:id/primaryTextView";
        THIRD_PAGE_LINK = "xpath://*[@resource-id='org.wikipedia:id/secondaryTextView'][contains(@text, 'Join Wikipedia')]";
        FOURTH_PAGE_LINK = "xpath://*[@resource-id='org.wikipedia:id/secondaryTextView'][contains(@text, 'Learn more')]";
        GET_STARTED_ACCEPT_BUTTON = "id:org.wikipedia:id/acceptButton";
    }
}
