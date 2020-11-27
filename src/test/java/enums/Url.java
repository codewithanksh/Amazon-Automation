package enums;

/** Created by Ankush Sharma on 08/09/2020. */
public enum Url {
    //URL for Data Capture Page
    DATACAPTURE_FORM_URL("file:///"+System.getProperty("user.dir")+"/src/test/resources/index.html"),
    SIGNOUT("/gp/flex/sign-out.html");

    String url;

    Url(String url){
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
