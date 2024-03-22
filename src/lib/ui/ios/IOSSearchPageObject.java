package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class IOSSearchPageObject extends SearchPageObject {
    public IOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    static {
        SKIP_ONBOARDING = "xpath://XCUIElementTypeButton[@name='Skip']";
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@label='Search Wikipedia']";
        SEARCH_CANCEL = "xpath://XCUIElementTypeStaticText[@name='Cancel']";
        SEARCH_RESULTS_LIST = "xpath://XCUIElementTypeCollectionView[@visible='true']";
        SEARCH_RESULT_WITH_DESCRIPTION_TEMPLATE = "xpath://XCUIElementTypeStaticText[contains(@name, '{DESCRIPTION}')]";
        SEARCH_RESULT_WITH_TITLE_AND_DESCRIPTION_TEMPLATE = "xpath://" +
                "XCUIElementTypeOther[XCUIElementTypeStaticText[contains(@name, '{TITLE}')] and " +
                "XCUIElementTypeStaticText[contains(@name, '{DESCRIPTION}')]]";
        SEARCH_RESULT_ELEMENT = "xpath:(//XCUIElementTypeCollectionView)[1]/XCUIElementTypeCell";
        LABEL_OF_EMPTY_SEARCH_RESULTS = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        ARTICLE_TOOLBAR_SEARCH_BUTTON = "xpath://XCUIElementTypeButton[@name='Search Wikipedia']";
    }
}
