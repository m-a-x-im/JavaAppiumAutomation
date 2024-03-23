package tests;

import lib.CoreTestCase;
import lib.ui.WelcomePageObject;
import lib.ui.factories.WelcomePageObjectFactory;

public class GetStartedTest extends CoreTestCase {

    /**
     * 1. Дождаться появления ссылки "Learn more about Wikipedia".
     * 2. Нажать кнопку Next.
     * 3. Дождаться появления заголовка "New ways to explore".
     * 4. Нажать кнопку Next.
     * 5. Дождаться появления ссылки "Add or edit preferred languages".
     * 6. Нажать кнопку Next.
     * 7. Дождаться появления ссылки "Learn more about data collected".
     * 8. Нажать кнопку Get started.
     */
    public void testPassThroughWelcome() {

        WelcomePageObject welcomePageObject = WelcomePageObjectFactory.get(driver);

        welcomePageObject.waitForFirstPageLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForSecondPageTitle();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForThirdPageLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForFourthPageLink();
        welcomePageObject.clickGetStartedOrAcceptButton();
    }
}
