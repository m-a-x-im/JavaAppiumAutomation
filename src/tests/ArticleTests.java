package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;

public class ArticleTests extends CoreTestCase {

    private static final String
            DEFAULT_QUERY = "Java",
            DEFAULT_ARTICLE_TITLE = "Java (programming language)",
            DEFAULT_ARTICLE_SUBTITLE = "Object-oriented programming language";


    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести поисковый запрос.
     * 3. Выбрать (открыть) статью с нужным подзаголовком.
     * 4. Найти текст этого подзаголовка на странице.
     * 5. Проверить, что текст совпадает с подзаголовком из результатов поиска.
     */
    public void testCompareArticleSubtitle()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(DEFAULT_QUERY);
        searchPageObject.openArticleWithSubtitle(DEFAULT_ARTICLE_SUBTITLE);

        String subtitle_text = articlePageObject.getArticleSubtitleText();

        assertEquals(
                "The Text of the Subtitle is not equal to '" + DEFAULT_ARTICLE_SUBTITLE + "'",
                DEFAULT_ARTICLE_SUBTITLE,
                subtitle_text
        );
    }

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести поисковый запрос.
     * 3. Выбрать (открыть) нужную строку их результатов поиска.
     * 4. Подождать появления подзаголовка.
     * 5. Проскроллить страницу до футера.
     */
    public void testScrollArticle()
    {
        String
                query = "System of a Down",
                article_subtitle = "American metal band";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(query);
        searchPageObject.openArticleWithSubtitle(article_subtitle);
        articlePageObject.waitSubtitleOfArticle();
        articlePageObject.scrollToFooter();
    }

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести поисковый запрос.
     * 3. Выбрать (открыть) статью из результатов поиска.
     * 4. Найти заголовок и проверить, соответствует ли он ожиданиям.
     */
    public void testArticleTitle()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(DEFAULT_QUERY);
        searchPageObject.openArticleWithSubtitle(DEFAULT_ARTICLE_SUBTITLE);
        String title = articlePageObject.getTitleIfExist();

        assertEquals(
                "The Actual Title is not equal to '" + DEFAULT_ARTICLE_SUBTITLE + "'",
                DEFAULT_ARTICLE_TITLE,
                title
        );
    }
}