import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        // Получить путь к apk-файлу из файла config.properties
        String configFilePath = "src/config.properties";
        FileInputStream propertiesInput = new FileInputStream(configFilePath);

        Properties properties = new Properties();
        properties.load(propertiesInput);

        String apk = properties.getProperty("APK_FILE");

        DesiredCapabilities capabilities = getDesiredCapabilities(apk);
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    private static DesiredCapabilities getDesiredCapabilities(String apk) {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", apk);
        return capabilities;
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() throws InterruptedException {
        WebElement skip_onboarding = driver.findElementByXPath("//*[@text='Skip']");
        skip_onboarding.click();

        WebElement search_input = driver.findElementByXPath("//*[@text='Search Wikipedia']");
        search_input.click();
//        Следующая строка вместо ввода текста почему-то нажимает кнопку смены языка:
//        search_input.sendKeys("Appium");
//        А вот эта работает, как ожидалось:
        driver.getKeyboard().sendKeys("Appium");

        Thread.sleep(1000);
    }
}
