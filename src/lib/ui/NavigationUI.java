package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;

/**
 * Методы навигации по приложению
 */
abstract public class NavigationUI extends MainPageObject {

    public NavigationUI(AppiumDriver driver)
    {
        super(driver);
    }

    protected static String
            BACK_ARROW,
            SAVED_TAB,
            READING_LISTS_TAB,
            CLOSE_LOG_IN_DIALOG_BUTTON,
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

    /**
     * Закрыть диалог авторизации на iOS
     */
    public void closeLogInDialogIOS() {
        if (Platform.getInstance().isAndroid()) return;
        else {
            this.waitForElementAndClick(
                    CLOSE_LOG_IN_DIALOG_BUTTON,
                    "The Close Button cannot be found using '" + CLOSE_LOG_IN_DIALOG_BUTTON + "'",
                    5
            );
        }
    }

    /**
     * Перейти на таб "Reading Lists" на iOS
     */
    public void openReadingListsTabIOS() {
        if (Platform.getInstance().isAndroid()) return;
        else {
            this.waitForElementAndClick(
                    READING_LISTS_TAB,
                    "The 'Reading Lists' Tab cannot be found using '" + READING_LISTS_TAB + "'",
                    5
            );
        }
    }
}
