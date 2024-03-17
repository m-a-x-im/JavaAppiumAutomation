package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject {

    private static final String
            LEARN_MORE_WIKIPEDIA_LINK_XPATH = "//XCUIElementTypeStaticText[@name='Learn more about Wikipedia']",
            NEXT_BUTTON_XPATH = "//XCUIElementTypeButton[@name='Next']",
            NEW_WAYS_TITLE_ID = "New ways to explore",
            ADD_EDIT_LANGUAGES_LINK_XPATH = "//XCUIElementTypeStaticText[@name='Add or edit preferred languages']",
            LEARN_MORE_DATA_COLLECTED_LINK_XPATH = "//XCUIElementTypeStaticText[@name='Learn more about data collected']",
            GET_STARTED_BUTTON_XPATH = "//XCUIElementTypeButton[@name='Get started']";


    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreWikipediaLink() {
        this.waitForElementPresent(
                By.xpath(LEARN_MORE_WIKIPEDIA_LINK_XPATH),
                "The 'Learn more about Wikipedia' Link cannot be found using '" + LEARN_MORE_WIKIPEDIA_LINK_XPATH + "'",
                5
        );
    }

    public void clickNextButton() {
        this.waitForElementAndClick(
                By.xpath(NEXT_BUTTON_XPATH),
                "The 'Next' Button cannot be found using '" + NEXT_BUTTON_XPATH + "'",
                5
        );
    }

    public void waitForNewWaysToExploreTitle() {
        this.waitForElementPresent(
                By.id(NEW_WAYS_TITLE_ID),
                "The 'New Ways To Explore' Title cannot be found using '" + NEW_WAYS_TITLE_ID + "'",
                5
        );
    }

    public void waitAddOrEditLanguagesLink() {
        this.waitForElementPresent(
                By.xpath(ADD_EDIT_LANGUAGES_LINK_XPATH),
                "The 'Add or edit preferred languages' Link cannot be found using '" + ADD_EDIT_LANGUAGES_LINK_XPATH + "'",
                5
        );
    }

    public void waitForLearnMoreDataCollectedLink() {
        this.waitForElementPresent(
                By.xpath(LEARN_MORE_DATA_COLLECTED_LINK_XPATH),
                "The 'Learn more about data collected' Link cannot be found using '" + LEARN_MORE_DATA_COLLECTED_LINK_XPATH + "'",
                5
        );
    }

    public void clickGetStartedButton() {
        this.waitForElementAndClick(
                By.xpath(GET_STARTED_BUTTON_XPATH),
                "The 'Get started' Button cannot be found using '" + GET_STARTED_BUTTON_XPATH + "'",
                5
        );
    }
}
