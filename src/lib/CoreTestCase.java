package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

/**
 * Set Up & Tear Down methods
 */
public class CoreTestCase extends TestCase {
    private static final String
            PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android";

    protected AppiumDriver driver;
    private static final String AppiumURL = "http://127.0.0.1:4723/";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = this.setCapabilitiesByPlatformEnv();

        driver = this.getDriverByPlatformEnv(new URL(AppiumURL), capabilities);

        this.rotateScreenPortrait();
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

    /**
     * Установить capabilities в зависимости от платформы
     * @return capabilities
     */
    private DesiredCapabilities setCapabilitiesByPlatformEnv() throws Exception
    {
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("appium:deviceName", "AndroidTestDevice");
            capabilities.setCapability("appium:platformVersion", "14");
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:appPackage", "org.wikipedia");
            capabilities.setCapability("appium:appActivity", ".main.MainActivity");
            capabilities.setCapability("appium:app",
                    "/Users/maxim/git/m-a-x-im/OldJavaAppiumAutomation/apk_files/org.wikipedia.apk");
        } else if (platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("appium:deviceName", "iPhone 15 Pro Max");
            capabilities.setCapability("appium:platformVersion", "17.2");
            capabilities.setCapability("appium:automationName", "XCUITest");
            capabilities.setCapability("appium:app",
                    "/Users/maxim/git/m-a-x-im/OldJavaAppiumAutomation/apk_files/Wikipedia693.app");
        } else {
            throw new Exception("Unable to get the run platform from the env variable. Platform value: " + platform);
        }
        return capabilities;
    }

    /**
     * Получить драйвер в зависимости от платформы
     * @param remoteAddress – Appium URL
     * @param capabilities – capabilities для платформы
     * @return AndroidDriver или IOSDriver
     */
    private AppiumDriver getDriverByPlatformEnv(URL remoteAddress, Capabilities capabilities) throws Exception
    {
        String platform = System.getenv("PLATFORM");
        AppiumDriver driver;

        if (platform.equals(PLATFORM_ANDROID)) driver = new AndroidDriver(remoteAddress, capabilities);
        else if (platform.equals(PLATFORM_IOS)) driver = new IOSDriver(remoteAddress, capabilities);
        else throw new Exception("Unable to get the run platform from the env variable. Platform value: " + platform);

        return driver;
    }
}
