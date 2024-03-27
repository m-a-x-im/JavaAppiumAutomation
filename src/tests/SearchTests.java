package tests;

import lib.CoreTestCase;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;


public class SearchTests extends CoreTestCase {

    private static final String
            DEFAULT_QUERY = "Java",
            DEFAULT_ARTICLE_DESCRIPTION = "Object-oriented programming language";

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести поисковый запрос.
     * 3. Выбрать по подзаголовку и открыть статью из результатов поиска.
     */
    public void testSearchSimple() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(DEFAULT_QUERY);
        searchPageObject.openArticleWithDescription(DEFAULT_ARTICLE_DESCRIPTION);
    }

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести поисковый запрос.
     * 3. Тапнуть кнопку очистки строки поиска.
     * 4. Проверить, что кнопки очистки больше нет.
     */
    public void testResetSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(DEFAULT_QUERY);
        searchPageObject.waitForCancelSearchButtonToAppear();
        searchPageObject.clickCancelSearchButton();
        searchPageObject.waitForCancelSearchButtonToDisappear();
    }

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести текст в строку поиска.
     * 3. Проверить, что в строке поиска есть введённый текст.
     * 4. Очистить строку поиска.
     * 5. Проверить, что в строке поиска отображается плейсхолдер.
     * 6. Вернуться на главный экран тапом по стрелке "назад".
     * 7. Проверить, что с экрана пропала кнопка смены языка из строки поиска.
     */
    public void testClearSearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);

        searchPageObject.initSearchInput();
        String search_bar_placeholder = searchPageObject.getSearchBarText();

        WebElement search_bar = searchPageObject.typeSearchLine(DEFAULT_QUERY);
        String search_bar_text = searchPageObject.getSearchBarText();

        assertEquals(
                "The Search Bar Text is not equal '" + DEFAULT_QUERY + "'",
                DEFAULT_QUERY,
                search_bar_text);

        search_bar.clear();

        search_bar_text = searchPageObject.getSearchBarText();
        assertEquals(
                "The Search Bar Text is not empty",
                search_bar_placeholder,
                search_bar_text);

        navigationUI.clickBackArrow();
        searchPageObject.waitToLangButtonToDisappear();
    }

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Проверить, что в ней есть плейсхолдер "Search Wikipedia".
     */
    public void testSearchBarPlaceholder() {

        String placeholder = "Search Wikipedia";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();

        String actual_text = searchPageObject.getSearchBarText();
        assertEquals(
                "The Search Bar Placeholder is not equal to '" + placeholder + "'",
                placeholder,
                actual_text
        );
    }

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести слово.
     * 3. Подождать появления результатов поиска.
     * 4. Проверить, что статей в результатах больше, чем одна.
     * 5. Подождать появления кнопки отмены поиска.
     * 6. Отменить поиск.
     * 7. Проверить, что результаты поиска пусты.
     */
    public void testSearchAndCancel() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(DEFAULT_QUERY);
        searchPageObject.waitForSearchResultsList();

        int number_of_search_results = searchPageObject.getNumberOfArticlesFound();
        assertTrue(
                "The Number of Search Results for the query '" + DEFAULT_QUERY + "' isn't greater than 1",
                number_of_search_results > 1
        );

        searchPageObject.waitForCancelSearchButtonToAppear();
        searchPageObject.clickCancelSearchButton();

        int size_of_search_results_list = searchPageObject.getSearchResultsList().size();
        assertEquals("The Search Results List is not empty", 0, size_of_search_results_list);
    }


    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести слово.
     * 3. Подождать появления результатов поиска.
     * 4. Проверить, что результаты поиска не пустые.
     * 5. Проверить, что искомое слово есть в каждом из результатов поиска.
     */
    public void testTextInSearchResults() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(DEFAULT_QUERY);
        searchPageObject.waitForSearchResultsList();

        int number_of_search_results = searchPageObject.getNumberOfArticlesFound();
        assertTrue(
                "The Number of Search Results for the query '" + DEFAULT_QUERY + "' isn't greater than 0",
                number_of_search_results > 0
        );

        List<WebElement> search_result_elements = searchPageObject.getSearchResultsList();

        for (WebElement element : search_result_elements) {
            String text = element.getAttribute("text");
            assertTrue(
                    "The Article titled '" + text + "' doesn't contain '" + DEFAULT_QUERY + "'",
                    text.contains(DEFAULT_QUERY)
            );
        }
    }

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести поисковый запрос.
     * 3. Подождать появления результатов поиска.
     * 4. Получить количество статей в результатах.
     * 5. Проверить, что количество больше нуля.
     */
    public void testNumberOfSearchResults() {
        String query = "System of a Down discography";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(query);
        searchPageObject.waitForSearchResultsList();

        int number_of_search_results = searchPageObject.getNumberOfArticlesFound();
        assertTrue(
                "The Number of Search Results for the query '" + query + "' isn't greater than 0",
                number_of_search_results > 0
        );
    }

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести поисковый запрос, на который вернётся пустой список.
     * 3. Проверить, что есть плейсхолдер пустого списка.
     * 4. Проверить, что список с результатами пустой.
     */
    public void testEmptySearchResultsList() {

        String query = "zxcvbnmasdfghjk";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(query);
        searchPageObject.waitForEmptyResultsLabel();

        int size_of_search_results_list = searchPageObject.getSearchResultsList().size();
        assertEquals("The Search Results List is not empty", 0, size_of_search_results_list);
    }

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести поисковый запрос, на который вернётся минимум 3 результата.
     * 3. Проверить, что 3 результата содержат ожидаемый заголовок и описание.
     */
    public void testSearchByTitleAndDescription() {

        String query = "Jav", title = "Java";
        String[] descriptions = {
                "Object-oriented programming language",
                "High-level programming language",
                "List of versions of the Java programming language"
        };

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(query);

        String[] not_found = new String[3];
        int count = 0;

        for (String description : descriptions) {
            try {
                searchPageObject.waitForSearchResultWithTitleAndDescription(title, description);
            } catch (Exception e) {
                not_found[count] = description;
            }
            ++count;
        }

        String[] empty_array = new String[3];
        assertEquals(
                String.format("\nNot found:\n%s", Arrays.toString(not_found)),
                Arrays.toString(empty_array),
                Arrays.toString(not_found)
        );
    }
}
