package io.moj.oauth;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final String REDIRECT_URI = "https://localhost";
    private static final String AUTHORIZE_PATH	= "https://api.moj.io/oauth2/authorize";
    private static final String CLIENT_ID	= "d97111b7-7642-4e63-b1b0-ce4b07efe3f3 ";

    private WebView webView = null;
    public static String accessToken = null;
    //static MojioAPI mMojioAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_login : {
                if (accessToken != null) {
                    Toast toast = Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT);
                    toast.show();
                    return true;
                }

                Toast toast = Toast.makeText(this, "Initiating OAuth2 login", Toast.LENGTH_SHORT);
                toast.show();

                Intent mojioLogin = new Intent(getApplicationContext(), LoginMojioActivity.class);
                startActivity(mojioLogin);

                return true;
            }
            case R.id.action_reset : {
                accessToken = null;

                Toast toast = Toast.makeText(this, "Session cleared", Toast.LENGTH_SHORT);
                toast.show();

                return true;
            }
            case R.id.action_exit: {
                finish();

                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
