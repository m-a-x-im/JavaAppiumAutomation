package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

/**
 * Set Up & Tear Down methods
 */
public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    private static final String AppiumURL = "http://127.0.0.1:4723/";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = setCapabilities();
        driver = new AndroidDriver(new URL(AppiumURL), capabilities);
        this.rotateScreenPortrait();
    }

    private static DesiredCapabilities setCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "14");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/maxim/git/m-a-x-im/OldJavaAppiumAutomation/apk_files/org.wikipedia.apk");
        return capabilities;
    }

    @Override
    protected void tearDown() throws Exception
    {
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.quit();

        super.tearDown();
    }

    /**
     * Повернуть экран в портретный режим
     */
    protected void rotateScreenPortrait()
    {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    /**
     * Повернуть экран в альбомный режим
     */
    protected void rotateScreenLandscape()
    {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    /**
     * Свернуть приложение на время
     * @param seconds – время в секундах, спустя которое нужно развернуть приложение
     */
    protected void sendAppToBackground(int seconds)
    {
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }
}
