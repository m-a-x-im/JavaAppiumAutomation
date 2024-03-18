package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Методы навигации по приложению
 */
public class NavigationUI extends MainPageObject {

    public NavigationUI(AppiumDriver driver)
    {
        super(driver);
    }


    private static final String
            BACK_ARROW_XPATH = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
            SAVED_TAB_ID = "id:org.wikipedia:id/nav_tab_reading_lists";


    /**
     * Тапнуть стрелку "назад"
     */
    public void clickBackArrow() {
        this.waitForElementAndClick(
            BACK_ARROW_XPATH,
            "The Back Arrow cannot be found using '" + BACK_ARROW_XPATH + "'",
            5
        );
    }

    /**
     * Открыть экран со списками сохранённых статей
     */
    public void openSavedListsView() {
        this.waitForElementAndClick(SAVED_TAB_ID,
            "The tab 'Saved' cannot be found using '" + SAVED_TAB_ID + "'",
            5
        );
    }
}
