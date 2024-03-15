package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


/**
 * Методы работы со статьями
 */
public class ArticlePageObject extends MainPageObject {

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }


    private static final String
            ARTICLE_TITLE_XPATH = "//*[@resource-id='org.wikipedia:id/page_contents_container']/android.webkit.WebView" +
            "/android.webkit.WebView/android.view.View/android.view.View[1]/android.widget.TextView[1]",
            ARTICLE_SUBTITLE_XPATH = "//*[@resource-id='pcs-edit-section-title-description']",
            FOOTER_XPATH = "//android.view.View[@content-desc='View article in browser']",
            SAVE_BUTTON_ID = "org.wikipedia:id/page_save",
            SNACKBAR_BUTTON_ID = "org.wikipedia:id/snackbar_action",
            LIST_NAME_INPUT_ID = "org.wikipedia:id/text_input",
            OK_BUTTON_XPATH = "//*[@text='OK']",
            HEADER_ID = "org.wikipedia:id/page_header_view",
            LIST_TO_SAVE_ARTICLE_XPATH_TEMPLATE = "//*[@resource-id='org.wikipedia:id/item_title'][@text='{LIST_NAME}']";


    /* TEMPLATE METHODS */
    /**
     * Получить xpath списка в шторке (для добавления статьи в этот список)
     * @param list_name – название списка
     * @return String, xpath
     */
    private static String getListToSaveArticleXpathByName(String list_name)
    {
        return LIST_TO_SAVE_ARTICLE_XPATH_TEMPLATE.replace("{LIST_NAME}", list_name);
    }
    /* TEMPLATE METHODS */


    /**
     * Подождать появления заголовка статьи
     * @return элемент – заголовок статьи
     */
    public WebElement waitTitleOfArticle()
    {
        return
                this.waitForElementPresent(
                        By.xpath(ARTICLE_TITLE_XPATH),
                        "The Title of the Article cannot be found using '" + ARTICLE_TITLE_XPATH + "'",
                        10
                );
    }

    /**
     * Подождать появления подзаголовка статьи
     * @return элемент – подзаголовок статьи
     */
    public WebElement waitSubtitleOfArticle()
    {
        return
                this.waitForElementPresent(
                By.xpath(ARTICLE_SUBTITLE_XPATH),
                "The Subtitle of the Article cannot be found using '" + ARTICLE_SUBTITLE_XPATH + "'",
                10
        );
    }

    /**
     * Получить текст заголовка статьи
     * @return строку – заголовок
     */
    public String getArticleTitleText()
    {
        WebElement title_element = waitTitleOfArticle();
        return title_element.getAttribute("text");
    }

    /**
     * Получить текст подзаголовка статьи
     * @return строку – подзаголовок
     */
    public String getArticleSubtitleText()
    {
        WebElement subtitle_element = waitSubtitleOfArticle();
        return subtitle_element.getAttribute("name");  // OR "contentDescription", but NOT "content-desc"
    }

    /**
     * Проскроллить страницу до конца – до появления футера
     */
    public void scrollToFooter()
    {
        this.scrollUpToFindElement(
                By.xpath(FOOTER_XPATH),
                "The Footer cannot be found using '" + FOOTER_XPATH + "' with 20 swipes",
                20
        );
    }

    /**
     * Инициировать сохранение статьи
     */
    public void initSavingArticle()
    {
        this.waitForElementAndClick(
                By.id(SAVE_BUTTON_ID),
                "The 'Save' Button cannot be found using '" + SAVE_BUTTON_ID + "'",
                5
        );

        this.waitForElementAndClick(
                By.id(SNACKBAR_BUTTON_ID),
                "The 'Add to list' Snackbar Button cannot be found using '" + SNACKBAR_BUTTON_ID + "'",
                3
        );
    }

    /**
     * Сохранить статью в новый список
     * @param list_name – имя списка
     */
    public void saveArticleToNewList(String list_name)
    {
        this.waitForElementAndSendKeys(
                By.id(LIST_NAME_INPUT_ID),
                list_name,
                "The Text Input Line cannot be found using '" + LIST_NAME_INPUT_ID + "'",
                5
        );

        this.waitForElementAndClick(
                By.xpath(OK_BUTTON_XPATH),
                "Couldn't tap the OK button ('" + OK_BUTTON_XPATH + "')",
                5
        );
    }

    /**
     * Сохранить статью в существующий список
     * @param list_name – имя статьи
     */
    public void saveArticleToExistingList(String list_name)
    {
        String list_to_save_xpath = getListToSaveArticleXpathByName(list_name);

        this.waitForElementAndClick(
                By.xpath(list_to_save_xpath),
                "The List To Save the Article cannot be found using '" + list_to_save_xpath + "'",
                5
        );
    }

    /**
     * Получить заголовок статьи, если он представлен на экране
     * @return String, заголовок статьи
     */
    public String getTitleIfExist()
    {
        // Нужно закрыть всплывающую подсказку, чтобы появился заголовок
        this.waitForElementAndClick(
                By.id(HEADER_ID),
                "The Header cannot be found using '" + HEADER_ID + "'",
                2
        );

        try { return getArticleTitleText(); }
        catch (NoSuchElementException e)  {
            throw new AssertionError("The Title cannot be found using '" + ARTICLE_TITLE_XPATH + "'");
        }
    }
}
