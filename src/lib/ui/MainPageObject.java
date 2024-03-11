package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver)
    {
        this.driver = driver;
    }

    /**
     * Найти элемент (с явным ожиданием)
     * @param locator – локатор
     * @param error_message – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания в секундах
     * @return найденный элемент
     */
    public WebElement waitForElementPresent(By locator, String error_message, long timeOutInSeconds)
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
    public void waitForElementNotPresent(By locator, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Подождать появления элемента и тапнуть его
     *
     * @param locator          – локатор
     * @param error_message    – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания в секундах
     * @return
     */
    public WebElement waitForElementAndClick(By locator, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeOutInSeconds);
        element.click();
        return element;
    }

//    TODO: (Баг) При сохранении статьи может выходить на первый план шторка вместо диалога
    /**
     * Отправить элемент на задний план
     * @param bottom_sheet_locator – локатор шторки, которую нужно отправить на задний план
     * @param close_locator – локатор зоны вне шторки
     * @param timeOutInSeconds – время ожидания в секундах
     */
    public void waitForElementAndClickIfExists(By bottom_sheet_locator, By close_locator, long timeOutInSeconds)
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
    public void waitForElementAndClickWhileExist(By locator, String error_message, long timeOutInSeconds)
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
     *
     * @param locator          – локатор
     * @param value            – отправляемое значение
     * @param error_message    – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания в секундах
     * @return
     */
    public WebElement waitForElementAndSendKeys(By locator, String value, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }

    /**
     * Найти статью и открыть
     * @param query – поисковый запрос
     * @param article_locator – локатор статьи в результатах поиска
     */
    public void searchArticleAndOpen(String search_input_id, String query, By article_locator)
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
    public void searchArticleAndClickAddButton(
            String search_input_id,
            String query,
            String save_tab_id,
            String snackbar_button_id,
            By article_locator)
    {
        searchArticleAndOpen(search_input_id, query, article_locator);

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
    public void swipeElementToLeft(By locator, String error_message)
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
    public void assertElementNotPresent(By locator)
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
    public String waitForElementAndGetAttribute(By locator, String attribute, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeOutInSeconds);
        return element.getAttribute(attribute);
    }

    /**
     * Проверить, что у статьи есть соответствующий заголовок
     * @param locator – локатор элемента с заголовком
     * @param title – ожидаемый заголовок
     */
    public void assertElementPresent(By locator, String title)
    {
        try {
            WebElement element = driver.findElement(locator);
            String attribute = element.getAttribute("name");

            assertEquals(
                    "The Attribute Value is not equal to '" + title + "'",
                    title, attribute);

        } catch (NoSuchElementException e)  {
            throw new AssertionError("The Title cannot be found using '" + locator + "'");
        }
    }

    /**
     * Получить количество элементов, найденных по локатору
     * @param locator – локатор
     * @return размер списка с найденными элементами
     */
    public int getNumberOfElements(By locator)
    {
        List elements = driver.findElements(locator);
        return elements.size();
    }

    /**
     * Проверить, что не найдено ни одного элемента по локатору
     * @param locator – локатор
     * @param error_message – сообщение об ошибке
     */
    public void assertElementNotPresent(By locator, String error_message)
    {
        int number_of_elements = getNumberOfElements(locator);
        if (number_of_elements > 0) {
            String default_message = "The List of Search Results is not empty when searching using '" + locator + "'";
            throw new AssertionError(default_message + "\n " + error_message);
        }
    }

    /**
     * Проверить наличие определённого текста у элемента
     * @param locator – локатор (XPath || id etc.)
     * @param error_message – сообщение об ошибке
     * @param expected_text – ожидаемый текст
     * @param timeOutInSeconds – время ожидания в секундах
     */
    public void assertElementHasText(By locator, String error_message, String expected_text, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeOutInSeconds);
        String actual_text = element.getAttribute("text");
        assertEquals(error_message, expected_text, actual_text);
    }

    /**
     * Проскроллить страницу снизу вверх по центру экрана
     *
     * @param timeOfScroll – время ожидания скролла в миллисекундах
     */
    public void scrollUp(int timeOfScroll)
    {
        TouchAction action = new TouchAction(driver);

        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int finish_y = (int) (size.height * 0.2);

        action
                .press(x, start_y)
                .waitAction(timeOfScroll)
                .moveTo(x, finish_y)
                .release()
                .perform();
    }

    /**
     * Быстро проскроллить страницу снизу вверх
     */
    public void scrollUpQuick()
    {
        scrollUp(200);
    }

    /**
     * Скроллить страницу, пока не найден элемент или достигнут максимум свайпов
     *
     * @param locator       – локатор элемента
     * @param error_message – сообщение об ошибке
     * @param max_swipes    – максимальное количество свайпов, после которого поиск остановится
     */
    public void scrollUpToFindElement(By locator, String error_message, int max_swipes)
    {
        int swipes = 0;

        while (driver.findElements(locator).isEmpty()) {
            if (swipes >= max_swipes) {
                waitForElementPresent(locator, error_message, 0);
            }
            scrollUpQuick();
            ++swipes;
        }
    }
}
