package lib.ui;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Методы поиска
 */
abstract public class SearchPageObject extends MainPageObject {

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    protected static String
            SKIP_WELCOME,
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_INPUT_TEXT_ATTRIBUTE,
            SEARCH_CANCEL,
            SEARCH_RESULTS_LIST,
            SEARCH_RESULT_WITH_DESCRIPTION_TEMPLATE,
            SEARCH_RESULT_WITH_TITLE_AND_DESCRIPTION_TEMPLATE,
            SEARCH_RESULT_ELEMENT,
            LABEL_OF_EMPTY_SEARCH_RESULTS,
            ARTICLE_TOOLBAR_SEARCH_BUTTON;


    /* TEMPLATE METHODS */
    /**
     * Получить xpath статьи в результатах поиска, подставив в шаблон подстроку – описание статьи
     * @param article_description описание (подзаголовок) статьи в результатах поиска
     * @return String, xpath
     */
    private static String getSearchResultWithDescriptionXpath(String article_description) {
        return SEARCH_RESULT_WITH_DESCRIPTION_TEMPLATE.replace(
                "{DESCRIPTION}",
                article_description
        );
    }

    /**
     * Получить xpath статьи в результатах поиска, подставив в шаблон две подстроки – заголовок и описание статьи
     * @param article_title заголовок
     * @param article_description описание
     * @return String, xpath
     */
    private static String getSearchResultWithTitleAndDescriptionXpath(String article_title, String article_description) {
        String xpath_with_title = SEARCH_RESULT_WITH_TITLE_AND_DESCRIPTION_TEMPLATE.replace(
                "{TITLE}",
                article_title
        );
        return xpath_with_title.replace("{DESCRIPTION}", article_description);
    }
    /* TEMPLATE METHODS */


    /**
     * Инициализировать поиск
     */
    public void initSearchInput() {
        this.waitForElementAndClick(
                SKIP_WELCOME,
                "The Skip Button cannot be found using '" + SKIP_WELCOME + "'",
                5
        );

        WebElement element = this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "The Search Bar Container cannot be found using '" + SEARCH_INIT_ELEMENT + "'",
                5
        );
    }

    /**
     * Ввести запрос в строку поиска
     * @param query поисковый запрос
     * @return элемент строки поиска с введённым в неё текстом
     */
    public WebElement typeSearchLine(String query) {
        return
                this.waitForElementAndSendKeys(
                        SEARCH_INPUT,
                query,
                "The Search Line cannot be found using '" + SEARCH_INPUT + "'",
                5
        );
    }

    /**
     * Подождать появления кнопки отмены поиска
     */
    public void waitForCancelSearchButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL,
                "The Cancel Search Button cannot be found using '" + SEARCH_CANCEL + "'",
                5
        );
    }

    /**
     * Тапнуть кнопку отмены поиска
     */
    public void clickCancelSearchButton() {
        this.waitForElementAndClick(
                SEARCH_CANCEL,
                "The Cancel Search Button cannot be found using '" + SEARCH_CANCEL + "'",
                5
        );
    }

    /**
     * Подождать исчезновения кнопки отмены поиска
     */
    public void waitForCancelSearchButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL,
                "The Cancel Search Button is still on the screen",
                5
        );
    }

    /**
     * Подождать появления результатов поиска
     */
    public void waitForSearchResultsList() {
        this.waitForElementPresent(
                SEARCH_RESULTS_LIST,
                "The List of Search Results cannot be found using '" + SEARCH_RESULTS_LIST + "'",
                10
        );
    }

    /**
     * Подождать появления статьи с определённым описанием (подзаголовком) в результатах поиска
     * @param description описание статьи
     */
    public void waitForSearchResultWithDescription(String description) {
        String search_result_xpath = getSearchResultWithDescriptionXpath(description);

        this.waitForElementPresent(
                search_result_xpath,
                "The Search Result cannot be found using '" + search_result_xpath + "'",
                10
        );
    }

    /**
     * Открыть статью с определённым описанием (подзаголовком) из результатов поиска
     * @param description описание статьи
     */
    public void openArticleWithDescription(String description) {
        String search_result_xpath = getSearchResultWithDescriptionXpath(description);

        this.waitForElementAndClick(
                search_result_xpath,
                "The Search Result cannot be found using '" + search_result_xpath + "'",
                10
        );
    }

    /**
     * Получить количество статей в результатах поиска
     * @return число количество найденных статей
     */
    public int getNumberOfArticlesFound() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "No Articles could be found using '" + SEARCH_RESULT_ELEMENT + "'",
                5
        );
        return this.getNumberOfElements(SEARCH_RESULT_ELEMENT);
    }

    /**
     * Подождать, когда появится лейбл пустых результатов поиска
     */
    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                LABEL_OF_EMPTY_SEARCH_RESULTS,
                "The Label of Empty Search Results cannot be found using '" + LABEL_OF_EMPTY_SEARCH_RESULTS + "'",
                10
        );
    }

    /**
     * Получить результаты поиска в виде списка элементов
     * @return список элементов
     */
    public List getSearchResultsList() {
        By locator = this.getLocatorByString(SEARCH_RESULT_ELEMENT);

        List elements = driver.findElements(locator);
        return elements;
    }

    /**
     * Получить текст из строки поиска
     * @return String, введённый текст или плейсхолдер
     */
    public String getSearchBarText() {
        return
                this.waitForElementAndGetAttribute(
                        SEARCH_INPUT,
                SEARCH_INPUT_TEXT_ATTRIBUTE,
                "The Search Bar cannot be found using '" + SEARCH_INPUT + "'",
                5
        );
    }

    /**
     * Ввести запрос в строку поиска на тулбаре статьи
     * @param query поисковый запрос
     */
    public void typeArticleToolbarSearchLine(String query) {
        this.waitForElementAndClick(
                ARTICLE_TOOLBAR_SEARCH_BUTTON,
                "The Search Bar on the toolbar cannot be found using '" + ARTICLE_TOOLBAR_SEARCH_BUTTON + "'",
                5
        );
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                query,
                "The Search Line cannot be found using '" + SEARCH_INPUT + "'",
                5
        );
    }

    /**
     * Подождать появления статьи в результатах поиска с определённым заголовком и описанием
     * @param title заголовок статьи
     * @param description описание статьи
     */
    public void waitForSearchResultWithTitleAndDescription(String title, String description) {
        String search_result_xpath = getSearchResultWithTitleAndDescriptionXpath(title, description);

        this.waitForElementPresent(
                search_result_xpath,
                "The Article with title '" + title + "' and description '" + description + "' cannot be " +
                        "found using '" + search_result_xpath + "'",
                5
        );
    }
}
