package tests.iOS;

import lib.CoreTestCase;
import lib.ui.WelcomePageObject;

public class GetStartedTest extends CoreTestCase {

    /**
     * 1. Дождаться появления ссылки "Learn more about Wikipedia".
     * 2. Тапнуть кнопку Next.
     * 3. Дождаться появления заголовка "New ways to explore".
     * 4. Тапнуть кнопку Next.
     * 5. Дождаться появления ссылки "Add or edit preferred languages".
     * 6. Тапнуть кнопку Next.
     * 7. Дождаться появления ссылки "Learn more about data collected".
     * 8. Тапнуть кнопку Get started.
     */
    public void testPassThroughWelcome() {
        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

        welcomePageObject.waitForLearnMoreWikipediaLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForNewWaysToExploreTitle();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitAddOrEditLanguagesLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForLearnMoreDataCollectedLink();
        welcomePageObject.clickGetStartedButton();
    }
}
