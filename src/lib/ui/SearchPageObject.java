package lib.ui;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Методы, которые используются для поиска
 */
public class SearchPageObject extends MainPageObject {

    private static final String
            SKIP_ONBOARDING_ID = "org.wikipedia:id/fragment_onboarding_skip_button",
            SEARCH_INIT_ELEMENT_ID = "org.wikipedia:id/search_container",
            SEARCH_INPUT_ID = "org.wikipedia:id/search_src_text",
            SEARCH_CANCEL_ID = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULTS_LIST_ID = "org.wikipedia:id/search_results_list",
            SEARCH_RESULT_TITLE_XPATH_TEMPLATE = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING}']";

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }


    /* TEMPLATE METHODS */

    /**
     * Получить xpath заголовка статьи в результатах поиска, подставив подстроку в шаблон
     * @param substring – подстрока, которую нужно подставить в xpath; заголовок статьи в результатах поиска
     * @return xpath искомого заголовка статьи
     */
    private static String getSearchResultXpath(String substring)
    {
        return SEARCH_RESULT_TITLE_XPATH_TEMPLATE.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATE METHODS */

    /**
     * Инициализировать поиск
     */
    public void initSearchInput()
    {
        this.waitForElementAndClick(
                By.id(SKIP_ONBOARDING_ID),
                "The Skip Button cannot be found using '" + SKIP_ONBOARDING_ID + "'",
                5
        );

        WebElement element = this.waitForElementAndClick(
                By.id(SEARCH_INIT_ELEMENT_ID),
                "The Search Bar Container cannot be found using '" + SEARCH_INIT_ELEMENT_ID + "'",
                5
        );
    }

    /**
     * Ввести запрос в строку поиска
     * @param query – поисковый запрос
     * @return элемент строки поиска с введённым в неё текстом
     */
    public WebElement typeSearchLine(String query)
    {
        WebElement element = this.waitForElementAndSendKeys(
                By.id(SEARCH_INPUT_ID),
                query,
                "The Search Line cannot be found using '" + SEARCH_INPUT_ID + "'",
                5
        );
        return element;
    }

    /**
     * Подождать появления кнопки отмены поиска
     */
    public void waitForCancelSearchButtonToAppear()
    {
        this.waitForElementPresent(
                By.id(SEARCH_CANCEL_ID),
                "The Cancel Search Button cannot be found using '" + SEARCH_CANCEL_ID + "'",
                5
        );
    }

    /**
     * Тапнуть кнопку отмены поиска
     */
    public void clickCancelSearchButton()
    {
        this.waitForElementAndClick(
                By.id(SEARCH_CANCEL_ID),
                "The Cancel Search Button cannot be found using '" + SEARCH_CANCEL_ID + "'",
                5
        );
    }

    /**
     * Подождать исчезновения кнопки отмены поиска
     */
    public void waitForCancelSearchButtonToDisappear()
    {
        this.waitForElementNotPresent(
                By.id(SEARCH_CANCEL_ID),
                "The Cancel Search Button is still on the screen",
                5
        );
    }

    /**
     * Подождать появления результатов поиска
     */
    public void waitForSearchResultsList()
    {
        this.waitForElementPresent(
                By.id(SEARCH_RESULTS_LIST_ID),
                "The List of Search Results cannot be found using '" + SEARCH_RESULTS_LIST_ID + "'",
                10
        );
    }

    /**
     * Выбрать (по заголовку) один из результатов поиска
     * @param title – заголовок статьи в результатах поиска
     */
    public void selectSearchResult(String title)
    {
        String search_result_xpath = getSearchResultXpath(title);

        this.waitForElementAndClick(
                By.xpath(search_result_xpath),
                "The Search Result cannot be found using '" + search_result_xpath + "'",
                10
        );
    }
}
