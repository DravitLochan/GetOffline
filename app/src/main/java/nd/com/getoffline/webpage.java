package nd.com.getoffline;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;
import android.webkit.SslErrorHandler;
import android.net.http.SslError;

import java.util.ArrayList;
import java.util.List;

public class webpage extends AppCompatActivity {

    WebView webView;
    DbHandler dbase;
    String url;
    String src;
    List<PageInfo> pages;
    int position;
    final Activity activity=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);
        dbase=new DbHandler(getApplicationContext());
        webView= (WebView) findViewById(R.id.webview);
        Bundle bundle = getIntent().getExtras();
        //p.loadData(/*bundle.getString("src")*/"<html><body>You scored <b>hello world</b> points.</body></html>","txt/html",null);
        //p.loadUrl("https://github.com");
        //String str=bundle.getString("src");
        pages= new ArrayList<>();
        pages=dbase.getAllPages(pages);
        position=bundle.getInt("position");
        url=pages.get(position).getUrl();
        src=pages.get(position).getSrcCode();
        webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
        webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );//loads from cache if offline else online
     //   webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ONLY );//loads from cache only

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                activity.setTitle("Loading...");
                activity.setProgress(progress * 100);

                if(progress == 100)
                    activity.setTitle(R.string.app_name);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                // Handle the error
                webView.loadData(src,"text/html; charset=UTF-8",null);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });

        Toast.makeText(this,"page opening. Due to heavy file, page may load in only readable form",Toast.LENGTH_LONG).show();
        webView.loadUrl(url);
    }

}
