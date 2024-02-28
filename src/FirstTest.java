import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class FirstTest {

    private AppiumDriver driver;

    /**
     * Запуск драйвера и тестируемого приложения перед каждым тестом
     */
    @Before
    public void setUp() throws Exception {

        String apk = getApkFilePath();
        DesiredCapabilities capabilities = getDesiredCapabilities(apk);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    /**
     * Получить из конфига путь к apk-файлу тестируемого приложения
     */
    private static String getApkFilePath() throws IOException {
        String configFilePath = "src/config.properties";
        FileInputStream propertiesInput = new FileInputStream(configFilePath);
        Properties properties = new Properties();
        properties.load(propertiesInput);
        return properties.getProperty("APK_FILE");
    }

    /**
     * Установить параметры запуска тестов
     * @param apk – путь к apk-файлу тестируемого приложения
     */
    private static DesiredCapabilities getDesiredCapabilities(String apk) {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
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

    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести поисковый запрос.
     * 4. Перейти на искомую страницу из результатов поиска.
     */
    @Test
    public void testSearch() {

        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "The Skip Button cannot be fund",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "The Search Bar Container cannot be found",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "The search bar cannot be found",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@class='android.view.ViewGroup']//*[@text='Object-oriented programming language']"),
                "The 'Object-oriented programming language' element cannot be found when searching for 'Java'",
                10
        );
    }

    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести текст в строку поиска.
     * 4. Проверить, что текст введён.
     * 5. Очистить строку поиска.
     * 6. Проверить, что строка пуста.
     * 7. Тапнуть стрелку назад.
     * 8. Проверить, что возврат состоялся – на экране нет кнопки смены языка.
     */
    @Test
    public void testCancelSearch() {

        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "The Skip Button cannot be fund",
                5
        );

         waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "The Search Bar Container cannot be found",
                5
        );

        WebElement search_bar = waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "The Search Bar cannot be found",
                5
        );

        String search_bar_text = search_bar.getAttribute("text");
        Assert.assertEquals("The Search Bar Text is not equal 'Java'", "Java", search_bar_text);

        search_bar.clear();
        search_bar_text = search_bar.getAttribute("text");
        Assert.assertEquals("The Search Bar Text is not empty", "Search Wikipedia", search_bar_text);

        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "The Back Arrow cannot be found",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_lang_button"),
                "The Lang Button is still on the screen",
                5
        );
    }

    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести поисковый запрос.
     * 4. Перейти на искомую страницу из результатов поиска.
     * 5. Найти подзаголовок статьи.
     * 6. Проверить, что текст подзаголовка соответствует ожиданиям.
     */
    @Test
    public void testCompareArticleTitle() {

        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "The Skip Button cannot be fund",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "The Search Bar Container cannot be found",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "The Search Bar cannot be found",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@class='android.view.ViewGroup']//*[@text='Object-oriented programming language']"),
                "The 'Object-oriented programming language' element cannot be found when searching for 'Java'",
                5
        );

        // Клик по заголовку, чтобы скрыть подсказку
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Java (programming language)']"),
                "The Title cannot be found",
                5
        );

        WebElement subtitle_element = waitForElementPresent(
                By.id("pcs-edit-section-title-description"),
                "The Subtitle of the Article 'Java' cannot be found by id",
                15
        );

        String subtitle_text = subtitle_element.getAttribute("contentDescription");  // OR "name", but NOT "content-desc"

        Assert.assertEquals(
                "The Text of the Subtitle is not equal to 'Object-oriented programming language'",
                "Object-oriented programming language",
                subtitle_text
        );
    }

    /**
     * Найти элемент с явным ожиданием появления
     * @param locator – локатор (XPath || id etc.)
     * @param error_message – сообщение об ошибке
     * @param timeoutInSeconds – таймаут ожидания в секундах
     * @return найденный элемент
     */
    private WebElement waitForElementPresent(By locator, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Найти элемент с таймаутом по умолчанию
     */
    private WebElement waitForElementPresent(By locator, String error_message) {
        return waitForElementPresent(locator, error_message, 5);
    }

    /**
     * Найти элемент и кликнуть по нему
     *
     * @return
     */
    private WebElement waitForElementAndClick(By locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    /**
     * Найти элемент и отправить ему какое-то значение
     *
     * @param value – значение, которое нужно отправить элементу
     * @return
     */
    private WebElement waitForElementAndSendKeys(By locator, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    /**
     * Найти элемент и очистить его
     *
     * @return
     */
    private WebElement waitForElementAndClear(By locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    /**
     * Проверить с явным ожиданием, что элемента нет на экране
     */
    private void waitForElementNotPresent(By locator, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
