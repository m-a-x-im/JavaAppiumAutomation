import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
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
import java.util.List;
import java.util.Properties;
import static org.junit.Assert.*;

public class SearchResultsTest
{
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        String apk = getApkFilePath();
        DesiredCapabilities capabilities = getDesiredCapabilities(apk);
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Проверить, что в ней есть плейсхолдер "Search Wikipedia".
     */
    @Test
    public void testSearchBarText()
    {
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

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "The Search Bar Text is not equal to 'Search Wikipedia'",
                "Search Wikipedia",
                5
        );
    }


    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести текст (слово).
     * 4. Проверить, что в результатах поиска больше одного элемента.
     * 5. Отменить поиск.
     * 6. Проверить, что результаты поиска пусты.
     */
    @Test
    public void testSearchResults()
    {
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
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "The Search Bar cannot be found",
                10
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "The Search Results element cannot be found",
                10
        );

        // Количество элементов в результатах поиска
        int number_of_elements = driver.findElementsByXPath(
                "//*[@resource-id='org.wikipedia:id/search_results_list']/descendant::android.view.ViewGroup"
        ).size();

        assertTrue("There is no more than one item in the Search Results List", number_of_elements > 1);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "The Close Button cannot be found",
                5
        );

        number_of_elements = driver.findElementsByXPath(
                "//*[@resource-id='org.wikipedia:id/search_results_list']/descendant::android.view.ViewGroup"
        ).size();

        assertEquals("The Search Results List is not empty", 0, number_of_elements);
    }


    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести слово.
     * 4. Проверить, что искомое слово есть в каждом из результатов поиска.
     */
    @Test
    public void testWordInSearchResults()
    {
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
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "The Search Bar cannot be found",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "The Search Results element cannot be found",
                10
        );

        List<WebElement> search_result_elements = driver.findElementsByXPath(
                "//*[@resource-id='org.wikipedia:id/page_list_item_title']"
        );

        for (WebElement element : search_result_elements)
        {
            String text = element.getAttribute("text");
            assertTrue(
                    "The text '" + text + "' doesn't contain the word 'Java'",
                    text.contains("Java")
            );
        }
    }


    /**
     * Получить из конфига путь к apk-файлу тестируемого приложения
     */
    private static String getApkFilePath() throws IOException
    {
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
    private static DesiredCapabilities getDesiredCapabilities(String apk)
    {
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


    /**
     * Найти элемент (с явным ожиданием)
     * @param locator – локатор (XPath || id etc.)
     * @param error_message – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания в секундах
     * @return найденный элемент
     */
    private WebElement waitForElementPresent(By locator, String error_message, long timeOutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Найти элемент и кликнуть по нему
     * @param locator – локатор (XPath || id etc.)
     * @param error_message – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания в секундах
     */
    private void waitForElementAndClick(By locator, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeOutInSeconds);
        element.click();
    }

    /**
     * Найти элемент и отправить ему значение
     * @param locator – локатор (XPath || id etc.)
     * @param value – отправляемое значение
     * @param error_message – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания в секундах
     */
    private void waitForElementAndSendKeys(By locator, String value, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeOutInSeconds);
        element.sendKeys(value);
    }

    /**
     * Проверить наличие определённого текста у элемента
     * @param locator – локатор (XPath || id etc.)
     * @param error_message – сообщение об ошибке
     * @param expected_text – ожидаемый текст
     * @param timeOutInSeconds – время ожидания в секундах
     */
    private void assertElementHasText(By locator, String error_message, String expected_text, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeOutInSeconds);
        String actual_text = element.getAttribute("text");
        assertEquals(error_message, expected_text, actual_text);
    }
}
