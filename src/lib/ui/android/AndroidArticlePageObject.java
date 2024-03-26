package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject {

    static {
        ARTICLE_TITLE = "xpath://*" +
                "[@resource-id='org.wikipedia:id/page_contents_container']/android.webkit.WebView/" +
                "android.webkit.WebView/android.view.View/android.view.View[1]/android.widget.TextView[1]";
        ARTICLE_SUBTITLE = "xpath:" +
                "//android.view.ViewGroup[@resource-id='org.wikipedia:id/page_contents_container']/" +
                "android.webkit.WebView/android.webkit.WebView/android.view.View/" +
                "android.view.View[1]/android.widget.TextView[2]";
        HEADER = "id:org.wikipedia:id/page_header_view";
        FOOTER = "xpath://android.view.View[@content-desc='View article in browser']";
        SAVE_BUTTON = "id:org.wikipedia:id/page_save";
        SNACKBAR_BUTTON = "id:org.wikipedia:id/snackbar_action";
        LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
        OK_BUTTON = "xpath://*[@text='OK']";
        LIST_TO_SAVE_ARTICLE_TEMPLATE = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{LIST_NAME}']";
        SEARCH_INPUT_TEXT_ATTRIBUTE = "text";
    }

    public AndroidArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}
