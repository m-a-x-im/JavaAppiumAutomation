package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import lib.Platform;

/**
 * Методы работы со списками сохранённых статей
 */
abstract public class SavedListsPageObject extends MainPageObject {

    public SavedListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }


    protected static String
            SAVED_LIST_TEMPLATE,
            SAVED_ARTICLE_TEMPLATE,
            SWIPE_ACTION_DELETE_BUTTON,
            SNACKBAR_ACTION;


    /* TEMPLATE METHODS */
    /**
     * Получить xpath списка сохранённых статей
     * @param list_name – название списка
     * @return xpath списка
     */
    private static String getSavedListXpathByName(String list_name) {
        return SAVED_LIST_TEMPLATE.replace("{LIST_NAME}", list_name);
    }

    /**
     * Получить xpath статьи в списке
     * @param article_title – заголовок статьи для поиска в списке
     * @return xpath статьи
     */
    private static String getSavedArticleXpathByTitle(String article_title) {
        return SAVED_ARTICLE_TEMPLATE.replace("{ARTICLE_TITLE}", article_title);
    }
    /* TEMPLATE METHODS */

    /**
     * Открыть список сохранённых статей
     * @param list_name название списка
     */
    public void openSavedListByName(String list_name) {
        String saved_list_xpath = getSavedListXpathByName(list_name);

        this.waitForElementAndClick(
                saved_list_xpath,
                "The Saved List '" + list_name + "' cannot be open using '" + saved_list_xpath + "'",
                5
        );
    }

    /**
     * Подождать появления статьи и получить xpath её заголовка
     * @param article_title заголовок статьи
     * @return String, xpath заголовка
     */
    public String waitForArticleToAppearByTitle(String article_title) {

        String saved_article_xpath = getSavedArticleXpathByTitle(article_title);

        this.waitForElementPresent(
                saved_article_xpath,
                "The Article '" + article_title + "' cannot be found in the saved list using '" + saved_article_xpath + "'",
                10
        );
        return saved_article_xpath;
    }

    /**
     * Подождать, пока статья пропадёт из списка
     * @param article_title заголовок статьи
     */
    public void waitForArticleToDisappearByTitle(String article_title) {
        String saved_article_xpath = getSavedArticleXpathByTitle(article_title);

        this.waitForElementNotPresent(
                saved_article_xpath,
                "The Article '" + article_title + "' is still present in the saved list",
                10
        );
    }

    /**
     * Удалить статью свайпом и проверить, что она исчезла
     * @param article_title заголовок статьи
     */
    public void swipeArticleToDelete(String article_title) {

        String saved_article_xpath = waitForArticleToAppearByTitle(article_title);

        this.swipeElementToLeft(
                saved_article_xpath,
                "Couldn't swipe element with text '" + article_title + "' to delete"
        );

        if (Platform.getInstance().isIOS()) tapDeleteArticleButtonIOS();

        waitForArticleToDisappearByTitle(article_title);
    }

    /**
     * Тапнуть кнопку удаления статьи на iOS
     */
    public void tapDeleteArticleButtonIOS() {
        waitForElementAndClick(
                SWIPE_ACTION_DELETE_BUTTON,
                "The Swipe Action Delete Button cannot be found using '" + SWIPE_ACTION_DELETE_BUTTON + "'",
                5
        );
    }

    /**
     * Подождать, пока исчезнет снэкбар с кнопкой отмены удаления статьи
     */
    public void waitForSnackbarToDisappear() {
        this.waitForElementNotPresent(
                SNACKBAR_ACTION,
                "The Snackbar with the Undo Button is still present on the screen",
                5
        );
    }

    /**
     * Есть ли статья в списке
     * @param article_title – заголовок статьи
     * @return boolean
     */
    public boolean isArticlesInListByTitle(String article_title) {
        String saved_article_xpath = getSavedArticleXpathByTitle(article_title);
        return driver.findElements(By.xpath(saved_article_xpath)).isEmpty();
    }
}
