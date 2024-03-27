package lib.ui;

import io.appium.java_client.AppiumDriver;

/**
 * Методы навигации по приложению
 */
public class NavigationUI extends MainPageObject {

    public NavigationUI(AppiumDriver driver)
    {
        super(driver);
    }

    protected static String
            BACK_ARROW,
            SAVED_TAB,
            CANCEL_BUTTON;

    /**
     * Тапнуть стрелку "назад"
     */
    public void clickBackArrow() {
        this.waitForElementAndClick(
            BACK_ARROW,
            "The Back Arrow cannot be found using '" + BACK_ARROW + "'",
            5
        );
    }

    /**
     * Открыть экран со списками сохранённых статей
     */
    public void openSavedListsView() {
        this.waitForElementAndClick(SAVED_TAB,
            "The tab 'Saved' cannot be found using '" + SAVED_TAB + "'",
            5
        );
    }
}
