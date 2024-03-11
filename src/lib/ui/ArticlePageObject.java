package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
            ARTICLE_SUBTITLE_ID = "pcs-edit-section-title-description",
            FOOTER_XPATH = "//android.view.View[@content-desc='View article in browser']";

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /**
     * Подождать появления подзаголовка статьи
     * @return элемент – подзаголовок статьи
     */
    public WebElement waitSubtitleOfArticle()
    {
        WebElement element = this.waitForElementPresent(
                By.id(ARTICLE_SUBTITLE_ID),
                "The Subtitle of the Article cannot be found using '" + ARTICLE_SUBTITLE_ID + "'",
                10
        );
        return element;
    }

    /**
     * Получить текст подзаголовка статьи
     * @return строку с текстом поздаголовка
     */
    public String getArticleSubtitleText()
    {
        WebElement subtitle_element = waitSubtitleOfArticle();
        return subtitle_element.getAttribute("contentDescription");  // OR "name", but NOT "content-desc"
    }

    /**
     * Проскроллить страницу до конца – до появления футера
     */
    public void scrollToFooter()
    {
        this.scrollUpToFindElement(
                By.xpath(FOOTER_XPATH),
                "The Footer cannot be found using '" + FOOTER_XPATH + "'",
                10
        );
    }
}
