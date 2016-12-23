package nd.com.getoffline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class webpage extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);
        webView= (WebView) findViewById(R.id.webview);
        Bundle bundle = getIntent().getExtras();
        //p.loadData(/*bundle.getString("src")*/"<html><body>You scored <b>hello world</b> points.</body></html>","txt/html",null);
        //p.loadUrl("https://github.com");
        String str=bundle.getString("src");

        webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
        webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );//loads from cache if offline else online
     //   webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ONLY );//loads from cache only


        webView.loadUrl(str);

        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
//        p.loadData(str,"text/html",null);
    }
}
