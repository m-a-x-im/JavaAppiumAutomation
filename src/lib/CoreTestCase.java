package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import java.time.Duration;

/**
 * Set Up & Tear Down methods
 */
public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    /**
     * Повернуть экран в портретный режим
     */
    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    /**
     * Повернуть экран в альбомный режим
     */
    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    /**
     * Свернуть приложение на время
     * @param seconds время в секундах, спустя которое нужно развернуть приложение
     */
    protected void sendAppToBackground(int seconds) {
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }
}
