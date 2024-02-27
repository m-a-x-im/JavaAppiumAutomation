import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
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

        String[] skip_n_search_xpath = {"//*[@text='Skip']", "//*[@text='Search Wikipedia']"};

        for (String i : skip_n_search_xpath)
            waitForElementAndClick(By.xpath(i), "Cannot find an element by xpath '" + i + "'", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "The search input cannot be found",
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
     * 3. Тапнуть стрелку назад.
     * 4. Проверить, что возврат состоялся – на экране нет кнопки смены языка.
     */
    @Test
    public void testCancelSearch() {
        String[] skip_n_search_id = {"org.wikipedia:id/fragment_onboarding_skip_button", "org.wikipedia:id/search_container"};

        for (String i : skip_n_search_id)
            waitForElementAndClick(By.id(i), "Cannot find an element by id '" + i + "'", 5);

        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "The back arrow cannot be found",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_lang_button"),
                "The lang button is still on the screen",
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
        String[] skip_n_search_id = {"org.wikipedia:id/fragment_onboarding_skip_button", "org.wikipedia:id/search_container"};

        for (String i : skip_n_search_id)
            waitForElementAndClick(By.id(i), "Cannot find an element by id '" + i + "'", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Java",
                "The search input cannot be found",
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
                "The title cannot be found",
                5
        );

        WebElement subtitle_element = waitForElementPresent(
                By.id("pcs-edit-section-title-description"),
                "The subtitle of the article 'Java' cannot be found by id",
                15
        );

        String subtitle_text = subtitle_element.getAttribute("content-desc");

        Assert.assertEquals(
                "The text of the subtitle is not equal to 'Object-oriented programming language'",
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
     * @param error_message – сообщение об ошибке
     * @param timeoutInSeconds – таймаут ожидания в секундах
     */
    private void waitForElementAndClick(By locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
    }

    /**
     * Найти элемент и отправить ему какое-то значение
     * @param value – значение, которое нужно отправить элементу
     * @param error_message – сообщение об ошибке
     * @param timeoutInSeconds – таймаут ожидания в секундах
     */
    private void waitForElementAndSendKeys(By locator, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
    }

    /**
     * Проверить с явным ожиданием, что элемента нет на экране
     *
     * @param error_message    – сообщение об ошибке
     * @param timeoutInSeconds – таймаут ожидания в секундах
     */
    private void waitForElementNotPresent(By locator, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
