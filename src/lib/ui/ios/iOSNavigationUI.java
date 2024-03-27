package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class iOSNavigationUI extends NavigationUI {
    public iOSNavigationUI(AppiumDriver driver) {
        super(driver);
    }

    static {
        BACK_ARROW = "id:Back";
        SAVED_TAB = "xpath://XCUIElementTypeButton[@name='Saved']";
        CANCEL_BUTTON = "xpath://XCUIElementTypeStaticText[@name='Cancel']";
        READING_LISTS_TAB = "xpath://XCUIElementTypeStaticText[@name='Reading lists']";
        CLOSE_LOG_IN_DIALOG_BUTTON = "id:Close";
    }
}
