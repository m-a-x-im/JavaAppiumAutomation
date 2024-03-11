package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    private static final String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = setCapabilities();
        driver = new AndroidDriver(new URL(AppiumURL), capabilities);
    }

    private static DesiredCapabilities setCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
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
}