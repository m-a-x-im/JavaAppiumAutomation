package lib.ui;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Методы поиска в приложении
 */
public class SearchPageObject extends MainPageObject {

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }


    private static final String
            SKIP_ONBOARDING_ID = "org.wikipedia:id/fragment_onboarding_skip_button",
            SEARCH_INIT_ELEMENT_ID = "org.wikipedia:id/search_container",
            SEARCH_INPUT_ID = "org.wikipedia:id/search_src_text",
            SEARCH_CANCEL_ID = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULTS_LIST_ID = "org.wikipedia:id/search_results_list",
            SEARCH_RESULT_XPATH_TEMPLATE = "//*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{SUBSTRING}']",
            SEARCH_ARTICLE_TITLE_XPATH = "//*[@resource-id='org.wikipedia:id/page_list_item_title']",
            LABEL_OF_EMPTY_SEARCH_RESULTS_XPATH = "//*[@resource-id='org.wikipedia:id/results_text'][@text='No results']",
            ARTICLE_TOOLBAR_SEARCH_LINE_ID = "org.wikipedia:id/page_toolbar_button_search";


    /* TEMPLATE METHODS */
    /**
     * Получить xpath заголовка статьи в результатах поиска, подставив подстроку в шаблон
     * @param substring – подстрока, которую нужно подставить в xpath; подзаголовок статьи в результатах поиска
     * @return xpath искомого заголовка статьи
     */
    private static String getSearchResultXpath(String substring)
    {
        return SEARCH_RESULT_XPATH_TEMPLATE.replace("{SUBSTRING}", substring);
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
        return
                this.waitForElementAndSendKeys(
                By.id(SEARCH_INPUT_ID),
                query,
                "The Search Line cannot be found using '" + SEARCH_INPUT_ID + "'",
                5
        );
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
     * Подождать появления статьи в результатах поиска
     * @param article_subtitle – подзаголовок статьи
     */
    public void waitForSearchResultWithSubtitle(String article_subtitle)
    {
        String search_result_xpath = getSearchResultXpath(article_subtitle);

        this.waitForElementPresent(
                By.xpath(search_result_xpath),
                "The Search Result cannot be found using '" + search_result_xpath + "'",
                10
        );
    }

    /**
     * Получить количество статей в результатах поиска
     * @return число – количество найденных статей
     */
    public int getNumberOfArticlesFound()
    {
        this.waitForElementPresent(
                By.xpath(SEARCH_ARTICLE_TITLE_XPATH),
                "No Articles could be found using '" + SEARCH_ARTICLE_TITLE_XPATH + "'",
                5
        );
        return this.getNumberOfElements(By.xpath(SEARCH_ARTICLE_TITLE_XPATH));
    }

    /**
     * Подождать, когда появится лейбл пустых результатов поиска
     */
    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(
                By.xpath(LABEL_OF_EMPTY_SEARCH_RESULTS_XPATH),
                "The Label of Empty Search Results cannot be found using '" + LABEL_OF_EMPTY_SEARCH_RESULTS_XPATH + "'",
                10
        );
    }

    /**
     * Получить результаты поиска в виде списка элементов
     * @return список элементов
     */
    public List getSearchResultsList()
    {
        List elements = driver.findElements(By.xpath(SEARCH_ARTICLE_TITLE_XPATH));
        return elements;
    }

    /**
     * Получить плейсхолдер строки поиска
     */
    public String getSearchBarPlaceholder()
    {
        return
                this.waitForElementAndGetAttribute(
                By.id(SEARCH_INPUT_ID),
                "text",
                "The Search Bar cannot be found using '" + SEARCH_INPUT_ID + "'",
                5
        );
    }

    /**
     * Найти по заголовку и открыть статью из результатов поиска
     * @param article_subtitle – заголовок статьи
     */
    public void openArticleWithSubtitle(String article_subtitle)
    {
        String search_result_xpath = getSearchResultXpath(article_subtitle);

        this.waitForElementAndClick(
                By.xpath(search_result_xpath),
                "The Search Result cannot be found using '" + search_result_xpath + "'",
                10
        );
    }

    /**
     * Ввести запрос в строку поиска на тулбаре статьи
     * @param query – поисковый запрос
     */
    public void typeArticleToolbarSearchLine(String query)
    {
        this.waitForElementAndClick(
                By.id(ARTICLE_TOOLBAR_SEARCH_LINE_ID),
                "The Search Bar on the toolbar cannot be found using '" + ARTICLE_TOOLBAR_SEARCH_LINE_ID + "'",
                5
        );
        this.waitForElementAndSendKeys(
                By.id(SEARCH_INPUT_ID),
                query,
                "The Search Line cannot be found using '" + SEARCH_INPUT_ID + "'",
                5
        );
    }
}
