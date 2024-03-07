import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
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
import org.openqa.selenium.NoSuchElementException;
import java.net.URL;
import java.util.List;

public class ArticlesTest
{
    private AppiumDriver driver;

    // Common locators
    private final String skip_id = "org.wikipedia:id/fragment_onboarding_skip_button";
    private final String search_container_id = "org.wikipedia:id/search_container";
    private final String search_input_id = "org.wikipedia:id/search_src_text";
    private final String save_tab_id = "org.wikipedia:id/page_save";
    private final String snackbar_button_id = "org.wikipedia:id/snackbar_action";


    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = setCapabilities();
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    static DesiredCapabilities setCapabilities()
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

    @After
    public void tearDown() { driver.quit(); }


    /**
     * 1. Скипнуть онбординг.
     * 2. Найти статью и добавить её в новый список.
     * 3. Найти ещё одну статью и добавить её в этот же список.
     * 4. Открыть список.
     * 5. Удалить первую статью.
     * 6. Открыть вторую статью.
     * 7. Проверить, что заголовок соответствует ожидаемому.
     */
    @Test
    public void testSaveTwoArticles()
    {
        // First Article
        String first_query = "Java";
        String first_article = "Java (programming language)";
        String first_article_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_title']" +
                "[@text='" + first_article + "']";

        // Second Article
        String second_query = "Appium";
        String second_article = second_query;
        String second_article_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_title']" +
                "[@text='" + second_article + "']";
        String second_article_title_xpath = "//*[@class='android.webkit.WebView']/*[@class='android.view.View'][1]" +
                "/*[@class='android.view.View'][1]";

        // Bottom Sheet
        String bottom_sheet_id = "org.wikipedia:id/create_button";
        String close_button_id = "org.wikipedia:id/touch_outside";

        // Name of the Saved List
        String input_list_name_id = "org.wikipedia:id/text_input";
        String list_name = "Java Appium Automation";
        String ok_button_xpath = "//*[@text='OK']";

        // Nav Bar
        String search_bar_article_id = "org.wikipedia:id/page_toolbar_button_search";
        String back_arrow_xpath = "//android.widget.ImageButton[@content-desc='Navigate up']";

        // Saved List & Articles
        String list_xpath = "//*[@resource-id='org.wikipedia:id/item_title'][@text='" + list_name + "']";
        String saved_first_article_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_container']/" +
                "android.widget.TextView[@text='" + first_article + "']";
        String saved_second_article_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_container']/" +
                "android.widget.TextView[@text='" + second_article + "']";


        // Onboarding
        waitForElementAndClick(
                By.id(skip_id),
                "The Skip Button cannot be fund using '" + skip_id + "'",
                5
        );

        // Search Bar
        waitForElementAndClick(
                By.id(search_container_id),
                "The Search Bar Container cannot be found using '" + search_container_id + "'",
                5
        );

        // First Article
        searchArticleAndClickAddButton(first_query, By.xpath(first_article_xpath));

        // Bottom sheet
        waitForElementAndClickIfExists(
                By.id(bottom_sheet_id),
                By.id(close_button_id),
                5
        );

        // Name of the List
        waitForElementAndSendKeys(
                By.id(input_list_name_id),
                list_name,
                "The Text Input Line cannot be found using '" + input_list_name_id + "'",
                5
        );

        // OK Button
        waitForElementAndClick(
                By.xpath(ok_button_xpath),
                "Couldn't tap the OK button using '" + ok_button_xpath + "'",
                1
        );

        // Search Bar (inside the article)
        waitForElementAndClick(
                By.id(search_bar_article_id),
                "The Input Line cannot be found and cleared using '" + search_bar_article_id + "'",
                5
        );

        // Second Article
        searchArticleAndClickAddButton(second_query, By.xpath(second_article_xpath));

        // Choosing the List
        waitForElementAndClick(
                By.xpath(list_xpath),
                "The Saved List cannot be found using '" + list_xpath + "'",
                5
        );

        // Back
        waitForElementAndClickWhileExist(
                By.xpath(back_arrow_xpath),
                "The Back Arrow cannot be found using '" + back_arrow_xpath + "'",
                5
        );

        // 'Saved' tab
        waitForElementAndClick(
                By.id("org.wikipedia:id/nav_tab_reading_lists"),
                "The tab 'Saved' cannot be found",
                5
        );

        // Saved List
        waitForElementAndClick(
                By.xpath(list_xpath),
                "The Saved List cannot be found using '" + list_xpath + "'",
                5
        );

        // Removing First Article
        swipeElementToLeft(
                By.xpath(saved_first_article_xpath),
                "Couldn't swipe to remove an element with text '" + first_article + "'"
        );

        // Snackbar
        waitForElementNotPresent(
                By.id(snackbar_button_id),
                "The Snackbar was found using '" + snackbar_button_id + "', but it shouldn't be",
                10
        );

        // Removed First Article
        assertElementNotPresent(By.xpath(saved_first_article_xpath));

        // Checking Second Article
        waitForElementAndClick(
                By.xpath(saved_second_article_xpath),
                "The Second Article cannot be found in the '" + list_name + "' list using '" +
                        saved_second_article_xpath + "'",
                5
        );

        // Title
        String title = waitForElementAndGetAttribute(
                By.xpath(second_article_title_xpath),
                "name",
                "The Title of the Article cannot be found using '" + second_article_title_xpath + "'",
                10
        );

        Assert.assertEquals(
                "The Title of the Article doesn't match '" + second_article + "'",
                second_article,
                title
        );
    }


    /**
     * 1. Скипнуть онбординг.
     * 2. Найти и открыть статью.
     * 3. Сразу проверить, есть ли на экране элемент title.
     */
    @Test
    public void testArticleTitle()
    {
        String query = "System of a Down";
        String article_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + query + "']";
        String article_title_xpath = "//android.webkit.WebView[@content-desc='System of a Down']/android.view.View[1]/android.view.View[1]";

        waitForElementAndClick(
                By.id(skip_id),
                "The Skip Button cannot be fund using '" + skip_id + "'",
                5
        );

        waitForElementAndClick(
                By.id(search_container_id),
                "The Search Bar Container cannot be found using '" + search_container_id + "'",
                5
        );

        searchArticleAndOpen(query, By.xpath(article_xpath));
        assertElementPresent(By.xpath(article_title_xpath), query);
    }


    /**
     * Найти элемент (с явным ожиданием)
     * @param locator – локатор
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
     * Проверить, что элемента нет на экране
     * @param locator – локатор
     * @param error_message – сообщение об ошибке
     * @param timeoutInSeconds – время ожидания элемента в секундах
     */
    private void waitForElementNotPresent(By locator, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Подождать появления элемента и тапнуть его
     * @param locator          – локатор
     * @param error_message    – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания в секундах
     */
    private void waitForElementAndClick(By locator, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeOutInSeconds);
        element.click();
    }

//    TODO: (Баг) При сохранении статьи может выходить на первый план шторка вместо диалога
    /**
     * Отправить элемент на задний план
     * @param bottom_sheet_locator – локатор шторки, которую нужно отправить на задний план
     * @param close_locator – локатор зоны вне шторки
     * @param timeOutInSeconds – время ожидания в секундах
     */
    private void waitForElementAndClickIfExists(By bottom_sheet_locator, By close_locator, long timeOutInSeconds)
    {
        if (!driver.findElements(bottom_sheet_locator).isEmpty()) {
            waitForElementAndClick(close_locator, "", timeOutInSeconds);
        }
    }

    /**
     * Тапать элемент, пока он есть на экране
     * @param locator – локатор
     * @param error_message – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания в секундах
     */
    private void waitForElementAndClickWhileExist(By locator, String error_message, long timeOutInSeconds)
    {
        while (!driver.findElements(locator).isEmpty()) {
            waitForElementAndClick(
                    locator,
                    error_message,
                    timeOutInSeconds);
        }
    }

    /**
     * Подождать появления элемента и отправить ему значение
     * @param locator – локатор
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
     * Найти статью и открыть
     * @param query – поисковый запрос
     * @param article_locator – локатор статьи в результатах поиска
     */
    private void searchArticleAndOpen(String query, By article_locator)
    {
        // Search
        waitForElementAndSendKeys(
                By.id(search_input_id),
                query,
                "The Search Bar cannot be found using '" + search_input_id + "'",
                5
        );

        // Article
        waitForElementAndClick(
                article_locator,
                "The Article cannot be found using '" + article_locator + "'",
                10
        );
    }

    /**
     * Найти статью и начать сохранение (тапнуть иконку добавления) в список
     * @param query – поисковый запрос
     * @param article_locator – локатор статьи в результатах поиска
     */
    private void searchArticleAndClickAddButton(String query, By article_locator)
    {
        searchArticleAndOpen(query, article_locator);

        // Save Tab
        waitForElementAndClick(
                By.id(save_tab_id),
                "The 'Save' button cannot be found using '" + save_tab_id + "'",
                5
        );

        // Snackbar
        waitForElementAndClick(
                By.id(snackbar_button_id),
                "The 'Add' button cannot be found in a snackbar using '" + snackbar_button_id + "'",
                3
        );
    }

    /**
     * Свайпнуть элемент влево
     * @param locator – локатор элемента
     * @param error_message – сообщение об ошибке
     */
    protected void swipeElementToLeft(By locator, String error_message)
    {
        WebElement element = waitForElementPresent(locator, error_message, 10);

        // Крайние точки элемента по осям X и Y
        int left_x = element.getLocation().x;
        int right_x = left_x + element.getSize().width;

        int upper_y = element.getLocation().y;
        int lower_y = upper_y + element.getSize().height;
        int middle_y = (upper_y + lower_y) / 2;

        // Swipe
        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(150)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    /**
     * Проверить, что не найдено ни одного элемента по локатору
     * @param locator – локатор
     */
    private void assertElementNotPresent(By locator)
    {
        List elements = driver.findElements(locator);
        if (!elements.isEmpty())
            throw new AssertionError("The Elements found using '" + locator + "' have not been removed");
    }

    /**
     * Подождать появления элемента и получить значение его атрибута
     * @param locator – локатор
     * @param attribute – название атрибута
     * @param error_message – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания элемента в секундах
     * @return значение атрибута
     */
    private String waitForElementAndGetAttribute(By locator, String attribute, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeOutInSeconds);
        return element.getAttribute(attribute);
    }

    /**
     * Проверить, что у статьи есть соответствующий заголовок
     * @param locator – локатор элемента с заголовком
     * @param title – ожидаемый заголовок
     */
    private void assertElementPresent(By locator, String title)
    {
        try {
            WebElement element = driver.findElement(locator);
            String attribute = element.getAttribute("name");

            Assert.assertEquals(
                    "The Attribute Value is not equal to '" + title + "'",
                    title, attribute);

        } catch (NoSuchElementException e)  {
            throw new AssertionError("The Title cannot be found using '" + locator + "'");
        }
    }
}
