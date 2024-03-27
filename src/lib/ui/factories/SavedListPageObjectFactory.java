package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.SavedListsPageObject;
import lib.ui.android.AndroidSavedListPageObject;
import lib.ui.ios.iOSSavedListPageObject;

public class SavedListPageObjectFactory {

    public static SavedListsPageObject get(AppiumDriver driver) {

        if (Platform.getInstance().isAndroid()) {
            return new AndroidSavedListPageObject(driver);
        } else {
            return new iOSSavedListPageObject(driver);
        }
    }
}
