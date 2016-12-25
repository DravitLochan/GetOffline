package nd.com.getoffline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import android.webkit.WebViewClient;
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
        webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );//loads from cache if offline else online
     //   webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ONLY );//loads from cache only

        //Toast.makeText(this,"page opening",Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"page opening. Due to heavy file, page may load in only readable form",Toast.LENGTH_LONG).show();
        webView.loadUrl(url);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                webView.loadData(src,"text/html",null);
            }

            @Override
            public void onReceivedHttpError(
                    WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                webView.loadData(src,"text/html",null);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                           SslError error) {
                webView.loadData(src,"text/html",null);
            }
        });

//        p.loadData(str,"text/html",null);
    }
}
