import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTest extends CoreTestCase {

    private MainPageObject mainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }

    private static final String
            default_query = "Java",
            default_article = "Java (programming language)",
            default_article_xpath = "//*[@content-desc='Java (programming language)']",
            default_article_subtitle = "Object-oriented programming language",
            search_results_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_title']",
            back_arrow_xpath = "//android.widget.ImageButton[@content-desc='Navigate up']",
            save_tab_id = "org.wikipedia:id/page_save",
            snackbar_button_id = "org.wikipedia:id/snackbar_action";

    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести поисковый запрос.
     * 4. Перейти на искомую страницу из результатов поиска.
     */
    public void testSearch()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(default_query);
        searchPageObject.selectSearchResult(default_article);
    }

    /**
     * 1. Скипнуть онбординг и тапнуть строку поиска.
     * 2. Ввести поисковый запрос.
     * 3. Тапнуть кнопку очистки строки поиска.
     * 4. Проверить, что кнопки очистки больше нет.
     */
    public void testCancelSearch()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(default_query);
        searchPageObject.waitForCancelSearchButtonToAppear();
        searchPageObject.clickCancelSearchButton();
        searchPageObject.waitForCancelSearchButtonToDisappear();
    }

    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести текст в строку поиска.
     * 4. Проверить, что текст введён.
     * 5. Очистить строку поиска.
     * 6. Проверить, что строка пуста.
     * 7. Тапнуть стрелку назад.
     * 8. Проверить, что возврат состоялся – на экране нет кнопки смены языка.
     */
    // TODO: отрефакторить
    public void testClearSearch()
    {
        String lang_button_id = "org.wikipedia:id/search_lang_button";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        WebElement search_bar = searchPageObject.typeSearchLine(default_query);

        String search_bar_text = search_bar.getAttribute("text");
        Assert.assertEquals(
                "The Search Bar Text is not equal '" + default_query + "'",
                default_query,
                search_bar_text);

        search_bar.clear();

        search_bar_text = search_bar.getAttribute("text");
        Assert.assertEquals(
                "The Search Bar Text is not empty",
                "Search Wikipedia",
                search_bar_text);

        mainPageObject.waitForElementAndClick(
                By.xpath(back_arrow_xpath),
                "The Back Arrow cannot be found",
                5
        );

        mainPageObject.waitForElementNotPresent(
                By.id(lang_button_id),
                "The Lang Button is still on the screen",
                5
        );
    }

    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести поисковый запрос.
     * 4. Перейти на искомую страницу из результатов поиска.
     * 5. Найти подзаголовок статьи.
     * 6. Проверить, что текст подзаголовка соответствует ожиданиям.
     */
    public void testCompareArticleSubtitle()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(default_article);
        searchPageObject.selectSearchResult(default_article);

        String subtitle_text = articlePageObject.getArticleSubtitleText();

        Assert.assertEquals(
                "The Text of the Subtitle is not equal to '" + default_article_subtitle + "'",
                default_article_subtitle,
                subtitle_text
        );
    }


    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести слово.
     * 4. Тапнуть нужную строку в результатах поиска.
     * 5. Несколько раз проскролить страницу и найти элемент футера
     */
    public void testScrollArticle()
    {
        String
                query = "Appium",
                article_title = "Appium";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(query);
        searchPageObject.selectSearchResult(article_title);

        articlePageObject.waitSubtitleOfArticle();
        articlePageObject.scrollToFooter();
    }

    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Проверить, что в ней есть плейсхолдер "Search Wikipedia".
     */
    public void testSearchBarText()
    {
        String search_input_id = "org.wikipedia:id/search_src_text";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();

        mainPageObject.assertElementHasText(
                By.id(search_input_id),
                "The Search Bar Text is not equal to 'Search Wikipedia'",
                "Search Wikipedia",
                5
        );
    }


    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести текст (слово).
     * 4. Проверить, что в результатах поиска больше одного элемента.
     * 5. Отменить поиск.
     * 6. Проверить, что результаты поиска пусты.
     */
    public void testSearchResults()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(default_query);
        searchPageObject.waitForSearchResultsList();

//        mainPageObject.waitForElementPresent(
//                By.id("org.wikipedia:id/search_results_list"),
//                "The Search Results element cannot be found",
//                10
//        );

        // Количество элементов в результатах поиска
        int number_of_elements = driver.findElementsByXPath(
                "//*[@resource-id='org.wikipedia:id/search_results_list']/descendant::android.view.ViewGroup"
        ).size();

        assertTrue("There is no more than one item in the Search Results List", number_of_elements > 1);

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "The Close Button cannot be found",
                5
        );

        number_of_elements = driver.findElementsByXPath(
                "//*[@resource-id='org.wikipedia:id/search_results_list']/descendant::android.view.ViewGroup"
        ).size();

        assertEquals("The Search Results List is not empty", 0, number_of_elements);
    }


    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести слово.
     * 4. Проверить, что искомое слово есть в каждом из результатов поиска.
     */
    public void testWordInSearchResults()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(default_query);
        searchPageObject.waitForSearchResultsList();

        List<WebElement> search_result_elements = driver.findElementsByXPath(search_results_xpath);

        for (WebElement element : search_result_elements)
        {
            String text = element.getAttribute("text");
            assertTrue(
                    "The text '" + text + "' doesn't contain the word 'Java'",
                    text.contains(default_query)
            );
        }
    }


    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести поисковый запрос.
     * 4. Убедиться, что список результатов поиска не пустой.
     * 5. Посчитать количество статей в результатах поиска.
     * 6. Проверить, что количество больше нуля.
     */
    public void testNumberOfSearchResults()
    {
        String query = "System of a Down discography";

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(query);
        searchPageObject.waitForSearchResultsList();

        int number_of_search_results = mainPageObject.getNumberOfElements(By.xpath(search_results_xpath));

        Assert.assertTrue(
                "The number of search results for the query '" + query + "'is less than 1",
                number_of_search_results > 0
        );
    }

    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести поисковой запрос, на который вернётся пустой список.
     * 4. Проверить, что есть плейсхолдер пустого списка.
     * 5. Проверить, что список с результатами пустой.
     */
    public void testEmptySearchResultsList()
    {
        String
                query = "zxcvbnmasdfghjk",
                label_of_empty_search_results = "//*[@resource-id='org.wikipedia:id/results_text'][@text='No results']";

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(query);

        // Label
        mainPageObject.waitForElementPresent(
                By.xpath(label_of_empty_search_results),
                "The Label of Empty Search Results cannot be found using '" + label_of_empty_search_results + "'",
                15
        );

        mainPageObject.assertElementNotPresent(
                By.xpath(search_results_xpath),
                "The Number of Elements is greater than 0"
        );
    }

    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести слово.
     * 4. Выбрать нужную строку в результатах поиска.
     * 5. Тапнуть таб "Save".
     * 6. В снэкбаре тапнуть кнопку "Add to list".
     * 7. Ввести имя списка.
     * 8. Тапнуть кнопку "OK".
     * 9. Тапнуть стрелку "назад" 2 раза.
     * 10. Тапнуть таб "Saved".
     * 11. Перейти в созданный список.
     * 12. Свайпом удалить добавленную строку из списка.
     * 13. Проверить, что строки больше нет.
     */
    /*
    public void testAddArticleToSaved()
    {
        String
                snackbar_input_id = "org.wikipedia:id/text_input",
                list_name = "Languages",
                ok_button_xpath = "//*[@text='OK']",
                saved_tab_id = "org.wikipedia:id/nav_tab_reading_lists",
                saved_list_xpath = "//*[@resource-id='org.wikipedia:id/item_title'][@text='" + list_name + "']",
                saved_article_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_container']/" +
                "android.widget.TextView[@text='" + default_article + "']",

                // Bottom Sheet
                bottom_sheet_id = "org.wikipedia:id/create_button",
                close_button_id = "org.wikipedia:id/touch_outside";

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        mainPageObject.searchArticleAndClickAddButton(
                search_input_id,
                default_query,
                save_tab_id,
                snackbar_button_id,
                By.xpath(default_article_xpath)
        );

        // Bottom sheet
        mainPageObject.waitForElementAndClickIfExists(
                By.id(bottom_sheet_id),
                By.id(close_button_id),
                5
        );

        // Name of this list
        mainPageObject.waitForElementAndSendKeys(
                By.id(snackbar_input_id),
                list_name,
                "The Text Input Line cannot be found",
                5
        );

        // OK
        mainPageObject.waitForElementAndClick(
                By.xpath(ok_button_xpath),
                "Couldn't tap the OK button",
                5
        );

        // Back
        mainPageObject.waitForElementAndClickWhileExist(
                By.xpath(back_arrow_xpath),
                "The Back Arrow cannot be found",
                5
        );

        // 'Saved' tab
        mainPageObject.waitForElementAndClick(
                By.id(saved_tab_id),
                "The tab 'Saved' cannot be found",
                5
        );

        // Open saved list
        mainPageObject.waitForElementAndClick(
                By.xpath(saved_list_xpath),
                "The list '" + list_name + "' cannot be found",
                5
        );

        // Remove article
        mainPageObject.swipeElementToLeft(
                By.xpath(saved_article_xpath),
                "Couldn't swipe element with text '" + default_article + "'"
        );

        mainPageObject.waitForElementNotPresent(
                By.xpath(saved_article_xpath),
                "The '" + default_article + "' article has not been removed",
                5
        );
    }

     */

    // TODO: переписать. После разворачивания МП результаты поиска стираются. А если открыта статья, то она пропадает
    /**
     * 1. Скипнуть онбординг.
     * 2. Тапнуть строку поиска.
     * 3. Ввести запрос.
     * 4. Найти элемент в результатах поиска.
     * 5. Свернуть МП.
     * 6. Развернуть МП.
     * 7. Проверить, что в результатах поиска по-прежнему отображается тот элемент.
     */
    /*
    public void testArticleInBackground()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(default_query);

        // Article
        mainPageObject.waitForElementPresent(
                By.xpath(default_article_xpath),
                "The Article cannot be found using '" + default_article_xpath + "'",
                10
        );

        driver.runAppInBackground(2);

        mainPageObject.waitForElementPresent(
                By.xpath(default_article_xpath),
                "The Article cannot be found using '" + default_article_xpath + "'",
                10
        );
    }

     */

    /**
     * 1. Скипнуть онбординг.
     * 2. Найти статью и добавить её в новый список.
     * 3. Найти ещё одну статью и добавить её в этот же список.
     * 4. Открыть список.
     * 5. Удалить первую статью.
     * 6. Открыть вторую статью.
     * 7. Проверить, что заголовок соответствует ожидаемому.
     */
    /*
    public void testSaveTwoArticles()
    {
        // First Article
        String
                first_query = "Java",
                first_article = "Java (programming language)",
                first_article_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_title']" +
                "[@text='" + first_article + "']",
                // Second Article
                second_query = "Appium",
                second_article = second_query,
                second_article_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_title']" +
                "[@text='" + second_article + "']",
                second_article_title_xpath = "//*[@class='android.webkit.WebView']/*[@class='android.view.View'][1]" +
                "/*[@class='android.view.View'][1]";

        // Bottom Sheet
        String
                bottom_sheet_id = "org.wikipedia:id/create_button",
                close_zone_id = "org.wikipedia:id/touch_outside";

        // Name of the Saved List
        String
                input_list_name_id = "org.wikipedia:id/text_input",
                list_name = "Java Appium Automation",
                ok_button_xpath = "//*[@text='OK']";

        // Nav Bar
        String
                search_bar_article_id = "org.wikipedia:id/page_toolbar_button_search",
                back_arrow_xpath = "//android.widget.ImageButton[@content-desc='Navigate up']";

        // Saved List & Articles
        String
                list_xpath = "//*[@resource-id='org.wikipedia:id/item_title'][@text='" + list_name + "']",
                saved_first_article_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_container']/" +
                "android.widget.TextView[@text='" + first_article + "']",
                saved_second_article_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_container']/" +
                "android.widget.TextView[@text='" + second_article + "']";


        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();

        // First Article
        mainPageObject.searchArticleAndClickAddButton(
                search_input_id,
                first_query,
                save_tab_id,
                snackbar_button_id,
                By.xpath(first_article_xpath));

        // Bottom sheet
        mainPageObject.waitForElementAndClickIfExists(
                By.id(bottom_sheet_id),
                By.id(close_zone_id),
                5
        );

        // Name of the List
        mainPageObject.waitForElementAndSendKeys(
                By.id(input_list_name_id),
                list_name,
                "The Text Input Line cannot be found using '" + input_list_name_id + "'",
                5
        );

        // OK Button
        mainPageObject.waitForElementAndClick(
                By.xpath(ok_button_xpath),
                "Couldn't tap the OK button using '" + ok_button_xpath + "'",
                1
        );

        // Search Bar (inside the article)
        mainPageObject.waitForElementAndClick(
                By.id(search_bar_article_id),
                "The Input Line cannot be found and cleared using '" + search_bar_article_id + "'",
                5
        );

        // Second Article
        mainPageObject.searchArticleAndClickAddButton(
                search_input_id,
                second_query,
                save_tab_id,
                snackbar_button_id,
                By.xpath(second_article_xpath));

        // Choosing the List
        mainPageObject.waitForElementAndClick(
                By.xpath(list_xpath),
                "The Saved List cannot be found using '" + list_xpath + "'",
                5
        );

        // Back
        mainPageObject.waitForElementAndClickWhileExist(
                By.xpath(back_arrow_xpath),
                "The Back Arrow cannot be found using '" + back_arrow_xpath + "'",
                5
        );

        // 'Saved' tab
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/nav_tab_reading_lists"),
                "The tab 'Saved' cannot be found",
                5
        );

        // Saved List
        mainPageObject.waitForElementAndClick(
                By.xpath(list_xpath),
                "The Saved List cannot be found using '" + list_xpath + "'",
                5
        );

        // Removing First Article
        mainPageObject.swipeElementToLeft(
                By.xpath(saved_first_article_xpath),
                "Couldn't swipe to remove an element with text '" + first_article + "'"
        );

        // Snackbar
        mainPageObject.waitForElementNotPresent(
                By.id(snackbar_button_id),
                "The Snackbar was found using '" + snackbar_button_id + "', but it shouldn't be",
                10
        );

        // Removed First Article
        mainPageObject.assertElementNotPresent(By.xpath(saved_first_article_xpath));

        // Checking Second Article
        mainPageObject.waitForElementAndClick(
                By.xpath(saved_second_article_xpath),
                "The Second Article cannot be found in the '" + list_name + "' list using '" +
                        saved_second_article_xpath + "'",
                5
        );

        // Title
        String title = mainPageObject.waitForElementAndGetAttribute(
                By.xpath(second_article_title_xpath),
                "name",
                "The Title of the Article cannot be found using '" + second_article_title_xpath + "'",
                10
        );

        Assert.assertEquals(
                "The Title of the Article doesn't match '" + second_article + "'",
                second_article,
                title
        );
    }

     */

    // TODO: тест ожидаемо падает, потому что страница загрузиться не успевает
    /**
     * 1. Скипнуть онбординг.
     * 2. Найти и открыть статью.
     * 3. Сразу проверить, есть ли на экране элемент title.
     */
    /*
    public void testArticleTitle()
    {
        String
                query = "System of a Down",
                article_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + query + "']",
                article_title_xpath = "//android.webkit.WebView[@content-desc='System of a Down']/" +
                        "android.view.View[1]/android.view.View[1]";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();

        mainPageObject.searchArticleAndOpen(search_input_id, query, By.xpath(article_xpath));
        mainPageObject.assertElementPresent(By.xpath(article_title_xpath), query);
    }

     */

    /**
     * 1. Скипнуть онбординг.
     * 2. Найти и открыть статью.
     * 3. Найти подзаголовок.
     * 4. Развернуть экран в landscape.
     * 5. Найти подзаголовок и сравнить с предыдущим.
     * 6. Вернуть экран в portrait.
     * 7. Снова сравнить подзаголовок с первоначальным.
     */
    /*
    public void testArticleSubtitleAfterRotation()
    {
        String
                query = "System of a Down",
                article_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + query + "']",
                article_title_xpath = "//android.webkit.WebView[@content-desc='System of a Down']/" +
                        "android.view.View[1]/android.view.View[1]",

                subtitle_id = "pcs-edit-section-title-description",
                attribute = "name";  // content-desc

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();

        mainPageObject.searchArticleAndOpen(
                search_input_id,
                query,
                By.xpath(article_xpath));

        // Subtitle
        String subtitle_before_rotation = mainPageObject.waitForElementAndGetAttribute(
                By.id(subtitle_id),
                attribute,
                "The Subtitle of Article cannot be found using '" + subtitle_id + "'",
                10
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String subtitle_after_rotation = mainPageObject.waitForElementAndGetAttribute(
                By.id(subtitle_id),
                attribute,
                "The Subtitle of Article cannot be found using '" + subtitle_id + "'",
                10
        );

        Assert.assertEquals(
                "The Subtitle of Article changed after the screen rotation was changed to landscape",
                subtitle_before_rotation,
                subtitle_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String subtitle_after_second_rotation = mainPageObject.waitForElementAndGetAttribute(
                By.id(subtitle_id),
                attribute,
                "The Subtitle of Article cannot be found using '" + subtitle_id + "'",
                10
        );

        Assert.assertEquals(
                "The Subtitle of Article changed after the screen rotation was changed to portrait",
                subtitle_before_rotation,
                subtitle_after_second_rotation
        );
    }

     */
}
