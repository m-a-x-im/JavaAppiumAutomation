package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SavedListsPageObject;

public class iOSSavedListPageObject extends SavedListsPageObject {

    public iOSSavedListPageObject(AppiumDriver driver) {
        super(driver);
    }

    static {
        SAVED_LIST_TEMPLATE = "xpath://XCUIElementTypeCell//" +
                "descendant::XCUIElementTypeStaticText[@name='{LIST_NAME}']";
        SAVED_ARTICLE_TEMPLATE = "xpath://XCUIElementTypeCell//" +
                "descendant::XCUIElementTypeStaticText[@name='{ARTICLE_TITLE}']";
        SWIPE_ACTION_DELETE_BUTTON = "xpath://XCUIElementTypeButton[@name='swipe action delete']";
        SNACKBAR_ACTION = "xpath:";
    }
}
