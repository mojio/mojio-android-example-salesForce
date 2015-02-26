package io.moj.oauth;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class LoginMojioActivity extends ActionBarActivity {

    private static final String REDIRECT_URI = "https://localhost";
    private static final String AUTHORIZE_PATH	= "https://api.moj.io/oauth2/authorize";
    private static final String CLIENT_ID	= "d97111b7-7642-4e63-b1b0-ce4b07efe3f3 ";

    private WebView webView = null;
    private String accessToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mojio);

        webView = (WebView) findViewById(R.id.layout_login_mojio);
        CookieManager.getInstance().removeAllCookie();
        //webView.setVisibility(View.VISIBLE);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith(REDIRECT_URI)) {

                    // extract OAuth2 access_token appended in url
                    if ( url.indexOf("access_token=") != -1 ) {
                        accessToken = mExtractToken(url);

                        // todo
                        MainActivity.accessToken = accessToken;
                        //mMojioAPI.mMojioAPIToken = accessToken;

                        //MojioAuth auth = new MojioAuth(response);
                    }

                    //loginview.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);

                    //mMojioAPI.getTrips("StartTime", true, 0, 10);

                    // don't go to redirectUri
                    setResult(RESULT_OK, getIntent());
                    finish();
                    return true;
                }

                // load the webpage from url (login and grant access)
                return super.shouldOverrideUrlLoading(view, url); // return false;
            }
        });

        // do OAuth2 login
        String authorizationUri = mReturnAuthorizationRequestUri();
        webView.loadUrl(authorizationUri);
    }

    private String mExtractToken(String url) {
        // url has format https://localhost/#access_token=<tokenstring>&token_type=Bearer&expires_in=315359999
        String[] sArray = url.split("access_token=");
        return (sArray[1].split("&token_type=bearer"))[0];
    }

    private String mReturnAuthorizationRequestUri() {
        StringBuilder sb = new StringBuilder();
        sb.append(AUTHORIZE_PATH);
        sb.append("?response_type=token");
        sb.append("&client_id="+CLIENT_ID);
        sb.append("&redirect_uri="+REDIRECT_URI);
        return sb.toString();
    }
}
