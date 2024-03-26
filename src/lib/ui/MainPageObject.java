package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;


public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    /**
     * Найти элемент (с явным ожиданием)
     * @param locator_with_type – строка, локатор с типом, например: "id:some_id"
     * @param error_message – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания в секундах
     * @return найденный элемент
     */
    public WebElement waitForElementPresent(String locator_with_type, String error_message, long timeOutInSeconds) {
        By locator = getLocatorByString(locator_with_type);

        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage("\n" + error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Проверить, что элемента нет на экране
     * @param locator_with_type – локатор
     * @param error_message – сообщение об ошибке
     * @param timeoutInSeconds – время ожидания элемента в секундах
     */
    public void waitForElementNotPresent(String locator_with_type, String error_message, long timeoutInSeconds) {
        By locator = getLocatorByString(locator_with_type);

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Подождать появления элемента и тапнуть его
     * @param locator_with_type          – локатор
     * @param error_message    – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания в секундах
     * @return найденный элемент
     */
    public WebElement waitForElementAndClick(String locator_with_type, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeOutInSeconds);
        element.click();
        return element;
    }

    /**
     * Подождать появления элемента и отправить ему значение
     * @param locator_with_type локатор
     * @param value отправляемое значение
     * @param error_message сообщение об ошибке
     * @param timeOutInSeconds время ожидания в секундах
     * @return элемент
     */
    public WebElement waitForElementAndSendKeys(String locator_with_type, String value, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }

    /**
     * Подождать появления элемента и получить значение его атрибута
     * @param locator_with_type – локатор
     * @param attribute – название атрибута
     * @param error_message – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания элемента в секундах
     * @return значение атрибута
     */
    public String waitForElementAndGetAttribute(String locator_with_type, String attribute, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeOutInSeconds);
        return element.getAttribute(attribute);
    }

    /**
     * Получить количество элементов, найденных по локатору
     * @param locator_with_type локатор
     * @return размер списка с найденными элементами
     */
    public int getNumberOfElements(String locator_with_type) {
        By locator = getLocatorByString(locator_with_type);

        List elements = driver.findElements(locator);
        return elements.size();
    }

    /**
     * Проскроллить страницу снизу вверх по центру экрана
     *
     * @param timeOfScroll время скролла в миллисекундах
     */
    public void scrollUp(int timeOfScroll) {
        Dimension size = driver.manage().window().getSize();
        int center_x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence actions = new Sequence(finger, 1);
        actions
                // Стартовая позиция
                .addAction(finger.createPointerMove(
                                Duration.ofSeconds(0),
                                PointerInput.Origin.viewport(),
                                center_x,
                                start_y
                        )
                )

                // Тап по экрану
                .addAction(finger.createPointerDown(0))

                // Движение к конечной точке
                .addAction(finger.createPointerMove(
                                Duration.ofMillis(timeOfScroll),
                                PointerInput.Origin.viewport(),
                                center_x,
                                end_y
                        )
                )

                // Поднять палец
                .addAction(finger.createPointerUp(0));

        // Выполнить действия
        driver.perform(Collections.singletonList(actions));
    }

    /**
     * Свайпнуть элемент влево
     * @param locator_with_type локатор элемента
     * @param error_message сообщение об ошибке
     */
    public void swipeElementToLeft(String locator_with_type, String error_message) {
        WebElement element = waitForElementPresent(locator_with_type, error_message, 10);

        // Крайние точки элемента по осям X и Y
        int left_x = element.getLocation().x;
        int right_x = left_x + element.getSize().width;
        int upper_y = element.getLocation().y;
        int lower_y = upper_y + element.getSize().height;

        int middle_y = (upper_y + lower_y) / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence actions = new Sequence(finger, 1);
        actions
                .addAction(finger.createPointerMove(
                        Duration.ofSeconds(0),
                        PointerInput.Origin.viewport(),
                        right_x,
                        middle_y)
                )
                .addAction(finger.createPointerDown(0))
                .addAction(finger.createPointerMove(
                        Duration.ofMillis(100),
                        PointerInput.Origin.viewport(),
                        left_x,
                        middle_y)
                )
                .addAction(finger.createPointerUp(0));

        driver.perform(Collections.singletonList(actions));
    }

    /**
     * Быстро проскроллить страницу снизу вверх
     */
    public void scrollUpQuick() {
        scrollUp(150);
    }

    /**
     * Скроллить страницу, пока не найден элемент или достигнут максимум свайпов (Android)
     *
     * @param locator_with_type локатор элемента
     * @param error_message сообщение об ошибке
     * @param max_swipes максимальное количество свайпов, после которого скролл прекратится
     */
    public void scrollUpToFindElement(String locator_with_type, String error_message, int max_swipes) {
        By locator = getLocatorByString(locator_with_type);

        int swipes = 0;

        while (driver.findElements(locator).isEmpty()) {
            if (swipes >= max_swipes) {
                waitForElementPresent(locator_with_type, error_message, 0);
            }
            scrollUpQuick();
            ++swipes;
        }
    }

    /**
     * Скроллить экран, пока не появится элемент или достигнут максимум свайпов (iOS)
     * @param locator локатор элемента
     * @param error_message сообщение об ошибке
     * @param max_swipes максимальное количество свайпов, после которого скролл прекратится
     */
    public void scrollUpUntilTheElementAppears(String locator, String error_message, int max_swipes) {

        int swipes = 0;
        while (!this.isElementLocatedOnTheScreen(locator)) {
            if (swipes >= max_swipes) {
                Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
            }
            scrollUp(3000);
            ++swipes;
        }
    }

    /**
     * Виден ли элемент на экране
     * @param locator локатор элемента
     * @return boolean
     */
    public boolean isElementLocatedOnTheScreen(String locator) {

        // Расположение элемента по высоте
        int element_location_by_y = this.waitForElementPresent(
                locator,
                "The Element cannot be found using '" + locator + "'",
                5
        ).getLocation().getY();

        // Высота экрана
        int screen_size_by_y = driver.manage().window().getSize().getHeight();

        return element_location_by_y < screen_size_by_y;
    }

    /**
     * Получить локатор из строки
     * @param locator_with_type – строка, локатор + его тип
     * @return – элемент By
     */
    protected By getLocatorByString(String locator_with_type) {
        String[] exploited_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String type = exploited_locator[0];
        String locator = exploited_locator[1];

        if (type.equals("xpath")) return By.xpath(locator);
        else if (type.equals("id")) return By.id(locator);
        else throw new IllegalArgumentException("Unable to get the type of the locator: " + locator);
    }
}
