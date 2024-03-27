package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class AndroidNavigationUI extends NavigationUI {
    public AndroidNavigationUI(AppiumDriver driver) {
        super(driver);
    }

    static {
        BACK_ARROW = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
        SAVED_TAB = "id:org.wikipedia:id/nav_tab_reading_lists";
    }
}
