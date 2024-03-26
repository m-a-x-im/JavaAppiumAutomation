package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.WelcomePageObject;

public class iOSWelcomePageObject extends WelcomePageObject {

    public iOSWelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    static {
        FIRST_PAGE_LINK = "xpath://XCUIElementTypeStaticText[@name='Learn more about Wikipedia']";
        NEXT_BUTTON = "xpath://XCUIElementTypeButton[@name='Next']";
        SECOND_PAGE_TITLE = "id:New ways to explore";
        THIRD_PAGE_LINK = "xpath://XCUIElementTypeStaticText[@name='Add or edit preferred languages']";
        FOURTH_PAGE_LINK = "xpath://XCUIElementTypeStaticText[@name='Learn more about data collected']";
        GET_STARTED_ACCEPT_BUTTON = "xpath://XCUIElementTypeButton[@name='Get started']";
    }
}
