package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class iOSNavigationUI extends NavigationUI {
    public iOSNavigationUI(AppiumDriver driver) {
        super(driver);
    }

    static {
        BACK_ARROW = "xpath://XCUIElementTypeButton[@name='Search']";
        SAVED_TAB = "xpath://XCUIElementTypeButton[@name='Saved']";
        CANCEL_BUTTON = "xpath://XCUIElementTypeStaticText[@name='Cancel']";
    }
}
