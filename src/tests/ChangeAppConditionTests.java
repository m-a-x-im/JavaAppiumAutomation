package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;

public class ChangeAppConditionTests extends CoreTestCase {

    private static final String
            DEFAULT_QUERY = "Java",
            DEFAULT_ARTICLE_DESCRIPTION = "Object-oriented programming language";


    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести поисковый запрос.
     * 3. Выбрать (открыть) статью из результатов поиска.
     * 4. Получить заголовок статьи.
     * 5. Развернуть экран в landscape.
     * 6. Снова получить заголовок и сравнить его с предыдущим.
     * 7. Развернуть экран в portrait.
     * 8. Снова сравнить заголовок с первоначальным.
     */
    public void testArticleTitleAfterRotation() {
        String
                query = "System of a Down",
                article_description = "American metal band";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(query);
        searchPageObject.openArticleWithDescription(article_description);

        String title_before_rotation = articlePageObject.getArticleTitleText();

        this.rotateScreenLandscape();
        String title_after_rotation = articlePageObject.getArticleTitleText();
        assertEquals(
                "The Subtitle of Article changed after the screen rotation was changed to landscape",
                title_before_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait();
        String title_after_second_rotation = articlePageObject.getArticleTitleText();
        assertEquals(
                "The Subtitle of Article changed after the screen rotation was changed to portrait",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести поисковый запрос.
     * 3. Проверить, что в результатах поиска есть статья с определённым описанием.
     * 4. Свернуть МП на 2 секунды.
     * 5. Проверить, что в результатах поиска по-прежнему отображается та же статья.
     */
    public void testArticleInBackground() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(DEFAULT_QUERY);
        searchPageObject.waitForSearchResultWithDescription(DEFAULT_ARTICLE_DESCRIPTION);

        this.sendAppToBackground(2);

        searchPageObject.waitForSearchResultWithDescription(DEFAULT_ARTICLE_DESCRIPTION);
    }
}
