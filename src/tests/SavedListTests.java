package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.NavigationUI;
import lib.ui.SavedListsPageObject;
import lib.ui.SearchPageObject;

public class SavedListTests extends CoreTestCase {

    private static final String
            DEFAULT_QUERY = "Java",
            DEFAULT_ARTICLE_TITLE = "Java (programming language)",
            DEFAULT_ARTICLE_DESCRIPTION = "Object-oriented programming language",
            DEFAULT_LIST_NAME = "Java Appium Automation";


    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести запрос.
     * 3. Выбрать (открыть) статью в результатах поиска.
     * 4. Сохранить статью в новый список.
     * 5. Вернуться на главный экран тапами стрелки "назад".
     * 6. Открыть экран со списками сохранённых статей.
     * 7. Перейти в созданный список.
     * 8. Свайпом удалить добавленную строку из списка и проверить, что она исчезла.
     */
    public void testAddArticleToSaved()
    {
        String list_name = "Languages";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        NavigationUI navigationUI = new NavigationUI(driver);
        SavedListsPageObject savedListsPageObject = new SavedListsPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(DEFAULT_QUERY);
        searchPageObject.openArticleWithDescription(DEFAULT_ARTICLE_DESCRIPTION);

        articlePageObject.initSavingArticle();
        articlePageObject.saveArticleToNewList(list_name);

        for (int i = 0; i < 2; i++) navigationUI.clickBackArrow();
        navigationUI.openSavedListsView();

        savedListsPageObject.openSavedListByName(list_name);
        savedListsPageObject.swipeArticleToDelete(DEFAULT_ARTICLE_TITLE);

    }

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести слово.
     * 3. Выбрать (открыть) статью в результатах поиска.
     * 4. Сохранить статью в новый список.
     * 5. Найти ещё одну статью и добавить её в этот же список.
     * 6. Открыть список.
     * 7. Удалить первую статью свайпом и проверить, что её нет в списке.
     * 8. Проверить, что вторая статья всё ещё в списке.
     * 9. Открыть вторую статью и проверить заголовок.
     */
    public void testSaveTwoArticles()
    {
        String
                second_query = "Appium",
                second_article_description = "Automation for Apps";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        NavigationUI navigationUI = new NavigationUI(driver);
        SavedListsPageObject savedListsPageObject = new SavedListsPageObject(driver);

        // Сохранение первой статьи в новый список
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(DEFAULT_QUERY);
        searchPageObject.openArticleWithDescription(DEFAULT_ARTICLE_DESCRIPTION);
        articlePageObject.initSavingArticle();
        articlePageObject.saveArticleToNewList(DEFAULT_LIST_NAME);

        // Сохранение второй статьи в тот же список
        searchPageObject.typeArticleToolbarSearchLine(second_query);
        searchPageObject.openArticleWithDescription(second_article_description);
        String article_title_before_saving = articlePageObject.getArticleTitleText();
        articlePageObject.initSavingArticle();
        articlePageObject.saveArticleToExistingList(DEFAULT_LIST_NAME);

        // Выход на главный экран и открытие списка
        for (int i = 0; i < 3; i++) navigationUI.clickBackArrow();
        navigationUI.openSavedListsView();

        // Удаление первой статьи
        savedListsPageObject.openSavedListByName(DEFAULT_LIST_NAME);
        savedListsPageObject.swipeArticleToDelete(DEFAULT_ARTICLE_TITLE);
        savedListsPageObject.waitForSnackbarToDisappear();

        boolean is_first_article_not_in_list = savedListsPageObject.isArticlesInListByTitle(DEFAULT_ARTICLE_TITLE);
        assertTrue(
                "The Article '" + DEFAULT_ARTICLE_TITLE + "' is still present in the list '" + DEFAULT_LIST_NAME + "'",
                is_first_article_not_in_list
        );

        // Проверка второй статьи
        boolean is_second_article_in_list = !savedListsPageObject.isArticlesInListByTitle(article_title_before_saving);
        assertTrue(
                "The Article '" + article_title_before_saving + "' not in the list '" + DEFAULT_LIST_NAME + "'",
                is_second_article_in_list);

        searchPageObject.openArticleWithDescription(second_article_description);

        String article_title_after_saving = articlePageObject.getArticleTitleText();
        assertEquals(
                "The Title of the Article doesn't match '" + article_title_before_saving + "'",
                article_title_before_saving,
                article_title_after_saving
        );
    }
}
