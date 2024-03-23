package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {
    private static final String
            PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android",
            APPIUM_URL = "http://127.0.0.1:4723/",
            ANDROID_APP_PATH = "/Users/maxim/git/m-a-x-im/OldJavaAppiumAutomation/apk_files/org.wikipedia.apk",
            IOS_APP_PATH = "/Users/maxim/git/m-a-x-im/OldJavaAppiumAutomation/apk_files/Wikipedia693.app";


    /* Приватный конструктор синглтона */
    private static Platform instance;
    private Platform() {};

    public static Platform getInstance() {
        if (instance == null)
            instance = new Platform();
        return instance;
    }
    /* */

    /**
     * Получить драйвер в зависимости от платформы
     * @return драйвер
     * @throws Exception тип драйвера для платформы не определён
     */
    public AppiumDriver getDriver() throws Exception {
        URL url = new URL(APPIUM_URL);

        if (this.isAndroid()) {
            return new AndroidDriver(url, this.setAndroidCapabilities());
        } else if (this.isIOS()) {
            return new IOSDriver(url, this.setIOSCapabilities());
        } else {
            throw new Exception("The Driver Type cannot be defined for the platform value: " + this.getPlatformVar());
        }
    }

    /**
     * Проверить, что выбрана платформа Android
     * @return boolean
     */
    public boolean isAndroid()
    {
        return isPlatform(PLATFORM_ANDROID);
    }

    /**
     * Проверить, что выбрана платформа iOS
     * @return boolean
     */
    public boolean isIOS()
    {
        return isPlatform(PLATFORM_IOS);
    }

    /**
     * Установить capabilities для запуска тестов на Android
     * @return capabilities для Android
     */
    private DesiredCapabilities setAndroidCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "AndroidTestDevice");
        capabilities.setCapability("appium:platformVersion", "14");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:appPackage", "org.wikipedia");
        capabilities.setCapability("appium:appActivity", ".main.MainActivity");
        capabilities.setCapability("appium:app", ANDROID_APP_PATH);
        return capabilities;
    }

    /**
     * Установить capabilities для запуска тестов на iOS
     * @return capabilities для iOS
     */
    private DesiredCapabilities setIOSCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("appium:deviceName", "iPhone 15 Pro Max");
        capabilities.setCapability("appium:platformVersion", "17.2");
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("appium:app", IOS_APP_PATH);
        return capabilities;
    }

    /**
     * Сравнить значение платформы с переменной окружения
     * @param my_platform платформа
     * @return boolean
     */
    private boolean isPlatform(String my_platform)
    {
        String platform = getPlatformVar();
        return my_platform.equals(platform);
    }

    /**
     * Получить значение переменной окружения "PLATFORM"
     * @return String
     */
    private String getPlatformVar()
    {
        return System.getenv("PLATFORM");
    }
}
