package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.WelcomePageObject;
import lib.ui.android.AndroidWelcomePageObject;
import lib.ui.ios.iOSWelcomePageObject;

public class WelcomePageObjectFactory {

    public static WelcomePageObject get(AppiumDriver driver) {

        if (Platform.getInstance().isIOS()) {
            return new iOSWelcomePageObject(driver);
        } else {
            return new AndroidWelcomePageObject(driver);
        }
    }
}
