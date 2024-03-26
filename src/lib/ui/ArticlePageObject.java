package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


/**
 * Методы работы со статьями
 */
abstract public class ArticlePageObject extends MainPageObject {

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }


    protected static String
            ARTICLE_TITLE,
            ARTICLE_SUBTITLE,
            HEADER,
            FOOTER,
            SAVE_BUTTON,
            SNACKBAR_BUTTON,
            CREATE_LIST_BUTTON_IOS,
            LIST_NAME_INPUT,
            OK_BUTTON,
            LIST_TO_SAVE_ARTICLE_TEMPLATE;


    /* TEMPLATE METHODS */
    /**
     * Получить xpath списка для добавления статьи в этот список
     * @param list_name – название списка
     * @return String, xpath
     */
    private static String getListToSaveArticleXpathByName(String list_name) {
        return LIST_TO_SAVE_ARTICLE_TEMPLATE.replace("{LIST_NAME}", list_name);
    }
    /* TEMPLATE METHODS */


    /**
     * Подождать появления заголовка статьи
     * @return элемент – заголовок статьи
     */
    public WebElement waitTitleOfArticle() {
        return
                this.waitForElementPresent(
                        ARTICLE_TITLE,
                        "The Title of the Article cannot be found using '" + ARTICLE_TITLE + "'",
                        10
                );
    }

    /**
     * Подождать появления подзаголовка статьи
     * @return элемент – подзаголовок статьи
     */
    public WebElement waitSubtitleOfArticle() {
        return
                this.waitForElementPresent(
                        ARTICLE_SUBTITLE,
                "The Subtitle of the Article cannot be found using '" + ARTICLE_SUBTITLE + "'",
                10
        );
    }

    /**
     * Получить текст заголовка статьи
     * @return строку – заголовок
     */
    public String getArticleTitleText() {
        WebElement title_element = waitTitleOfArticle();
        return title_element.getAttribute(SEARCH_INPUT_TEXT_ATTRIBUTE);
    }

    /**
     * Получить текст подзаголовка статьи
     * @return строку – подзаголовок
     */
    public String getArticleDescriptionText() {
        WebElement subtitle_element = waitSubtitleOfArticle();
        return subtitle_element.getAttribute("name");  // OR "contentDescription", but NOT "content-desc"
    }

    /**
     * Проскроллить страницу до конца – до появления футера
     */
    public void scrollToFooter() {

        if (Platform.getInstance().isAndroid()) {
            this.scrollUpToFindElement(
                    FOOTER,
                    "The Footer cannot be found using '" + FOOTER,
                    40
            );
        } else {
            this.scrollUpUntilTheElementAppears(
                    FOOTER,
                    "The Footer cannot be found using '" + FOOTER,
                    40);
        }
    }

    /**
     * Инициировать сохранение статьи
     */
    public void initSavingArticle() {
        this.waitForElementAndClick(
                SAVE_BUTTON,
                "The Save Button cannot be found using '" + SAVE_BUTTON + "'",
                5
        );

        this.waitForElementAndClick(
                SNACKBAR_BUTTON,
                "The Snackbar Button cannot be found using '" + SNACKBAR_BUTTON + "'",
                3
        );
    }

    /**
     * Тапнуть кнопку создания нового списка, если тест выполняется на iOS
     */
    public void clickCreateNewListIOS() {
        if (Platform.getInstance().isAndroid()) return;

        this.waitForElementAndClick(
                CREATE_LIST_BUTTON_IOS,
                "The Create_New_List Button cannot be found using '" + CREATE_LIST_BUTTON_IOS + "'",
                5
        );
    }

    /**
     * Сохранить статью в новый список
     * @param list_name – имя списка
     */
    public void saveArticleToNewList(String list_name) {
        this.waitForElementAndSendKeys(
                LIST_NAME_INPUT,
                list_name,
                "The Text Input Line cannot be found using '" + LIST_NAME_INPUT + "'",
                5
        );

        this.waitForElementAndClick(
                OK_BUTTON,
                "Couldn't tap the OK button ('" + OK_BUTTON + "')",
                5
        );
    }

    /**
     * Сохранить статью в существующий список
     * @param list_name – имя статьи
     */
    public void saveArticleToExistingList(String list_name) {

        String list_to_save_xpath = getListToSaveArticleXpathByName(list_name);

        this.waitForElementAndClick(
                list_to_save_xpath,
                "The List To Save the Article cannot be found using '" + list_to_save_xpath + "'",
                5
        );
    }

    /**
     * Получить заголовок статьи, если он представлен на экране
     * @return String, заголовок статьи
     */
    public String getTitleIfExist() {

        clickHeaderToCloseHint();

        try { return getArticleTitleText(); }
        catch (NoSuchElementException e)  {
            throw new AssertionError("The Title cannot be found using '" + ARTICLE_TITLE + "'");
        }
    }

    /**
     * Тапнуть хедер, чтобы убрать фокус с хинта
     */
    public void clickHeaderToCloseHint() {
        this.waitForElementAndClick(
                HEADER,
                "The Header cannot be found using '" + HEADER + "'",
                5
        );
    }
}
