package lib.ui;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Locale;

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
            SEARCH_RESULT_WITH_DESCRIPTION_XPATH_TEMPLATE = "//*[@resource-id='org.wikipedia:id" +
                    "/page_list_item_description'][@text='{DESCRIPTION}']",
            SEARCH_RESULT_WITH_TITLE_AND_DESCRIPTION_XPATH_TEMPLATE = "//android.view.ViewGroup[android.widget.TextView" +
            "[contains(@text, '{TITLE}')] and android.widget.TextView[@text='{DESCRIPTION}']]",
            SEARCH_ARTICLE_TITLE_XPATH = "//*[@resource-id='org.wikipedia:id/page_list_item_title']",
            LABEL_OF_EMPTY_SEARCH_RESULTS_XPATH = "//*[@resource-id='org.wikipedia:id/results_text'][@text='No results']",
            ARTICLE_TOOLBAR_SEARCH_LINE_ID = "org.wikipedia:id/page_toolbar_button_search";


    /* TEMPLATE METHODS */
    /**
     * Получить xpath статьи в результатах поиска, подставив в шаблон подстроку – описание статьи
     * @param article_description – описание (подзаголовок) статьи в результатах поиска
     * @return String, xpath
     */
    private static String getSearchResultWithDescriptionXpath(String article_description)
    {
        return SEARCH_RESULT_WITH_DESCRIPTION_XPATH_TEMPLATE.replace(
                "{DESCRIPTION}",
                article_description
        );
    }

    /**
     * Получить xpath статьи в результатах поиска, подставив в шаблон две подстроки – заголовок и описание статьи
     * @param article_title – заголовок
     * @param article_description – описание
     * @return String, xpath
     */
    private static String getSearchResultWithTitleAndDescriptionXpath(String article_title, String article_description)
    {
        String xpath_with_title = SEARCH_RESULT_WITH_TITLE_AND_DESCRIPTION_XPATH_TEMPLATE.replace(
                "{TITLE}",
                article_title
        );
        return xpath_with_title.replace("{DESCRIPTION}", article_description);
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
     * Подождать появления статьи с определённым описанием (подзаголовком) в результатах поиска
     * @param description – описание статьи
     */
    public void waitForSearchResultWithDescription(String description)
    {
        String search_result_xpath = getSearchResultWithDescriptionXpath(description);

        this.waitForElementPresent(
                By.xpath(search_result_xpath),
                "The Search Result cannot be found using '" + search_result_xpath + "'",
                10
        );
    }

    /**
     * Открыть статью с определённым описанием (подзаголовком) из результатов поиска
     * @param description – описание статьи
     */
    public void openArticleWithDescription(String description)
    {
        String search_result_xpath = getSearchResultWithDescriptionXpath(description);

        this.waitForElementAndClick(
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

    /**
     * Подождать появления статьи в результатах поиска с определённым заголовком и описанием
     * @param title – заголовок статьи
     * @param description – описание статьи
     */
    public void waitForSearchResultWithTitleAndDescription(String title, String description)
    {
        String search_result_xpath = getSearchResultWithTitleAndDescriptionXpath(title, description);

        this.waitForElementPresent(
                By.xpath(search_result_xpath),
                "The Article with title '" + title + "' and description '" + description + "' cannot be " +
                        "found using '" + search_result_xpath + "'",
                5
        );
    }
}
