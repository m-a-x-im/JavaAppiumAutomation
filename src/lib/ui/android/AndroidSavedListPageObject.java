package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SavedListsPageObject;

public class AndroidSavedListPageObject extends SavedListsPageObject {

    public AndroidSavedListPageObject(AppiumDriver driver) {
        super(driver);
    }

     static {
         SAVED_LIST_TEMPLATE = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{LIST_NAME}']";
         SAVED_ARTICLE_TEMPLATE = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']/" +
                         "android.widget.TextView[@text='{ARTICLE_TITLE}']";
         SNACKBAR_ACTION = "id:org.wikipedia:id/snackbar_action";
     }
}
