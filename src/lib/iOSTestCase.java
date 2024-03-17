package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

/**
 * Set Up & Tear Down methods
 */
public class iOSTestCase extends TestCase {

    protected AppiumDriver driver;
    private static final String AppiumURL = "http://127.0.0.1:4723/";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = setCapabilities();
        driver = new IOSDriver(new URL(AppiumURL), capabilities);
        this.rotateScreenPortrait();
    }

    private static DesiredCapabilities setCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("appium:deviceName", "iPhone 15 Pro Max");
        capabilities.setCapability("appium:platformVersion", "17.2");
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("appium:app", "/Users/maxim/git/m-a-x-im/OldJavaAppiumAutomation/apk_files/Wikipedia693.app");
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
