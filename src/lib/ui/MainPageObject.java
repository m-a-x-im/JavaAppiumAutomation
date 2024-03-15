package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
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
        wait.withMessage("\n" + error_message + "\n");
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
     * @param locator          – локатор
     * @param error_message    – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания в секундах
     * @return найденный элемент
     */
    public WebElement waitForElementAndClick(By locator, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeOutInSeconds);
        element.click();
        return element;
    }

    /**
     * Подождать появления элемента и отправить ему значение
     * @param locator          – локатор
     * @param value            – отправляемое значение
     * @param error_message    – сообщение об ошибке
     * @param timeOutInSeconds – время ожидания в секундах
     * @return элемент
     */
    public WebElement waitForElementAndSendKeys(By locator, String value, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
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
     * Проскроллить страницу снизу вверх по центру экрана
     *
     * @param timeOfScroll – время ожидания скролла в миллисекундах
     */
    public void scrollUp(int timeOfScroll)
    {
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

        TouchAction action = new TouchAction(driver);
        action
                .press(PointOption.point(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(10)))
                .moveTo(PointOption.point(left_x, middle_y))
                .release()
                .perform();

//        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
//        Sequence actions = new Sequence(finger, 1);
//        actions
//                .addAction(finger.createPointerMove(
//                        Duration.ofSeconds(0),
//                        PointerInput.Origin.viewport(),
//                        right_x,
//                        middle_y)
//                )
//                .addAction(finger.createPointerDown(0))
//                .addAction(finger.createPointerMove(
//                        Duration.ofMillis(100),
//                        PointerInput.Origin.viewport(),
//                        left_x,
//                        middle_y)
//                )
//                .addAction(finger.createPointerUp(0));
//
//        driver.perform(Collections.singletonList(actions));
    }

    /**
     * Быстро проскроллить страницу снизу вверх
     */
    public void scrollUpQuick()
    {
        scrollUp(150);
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
