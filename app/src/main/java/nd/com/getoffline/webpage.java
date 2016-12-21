package nd.com.getoffline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class webpage extends AppCompatActivity {

    WebView p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);
        p= (WebView) findViewById(R.id.webview);
        Bundle bundle = getIntent().getExtras();
        //p.loadData(/*bundle.getString("src")*/"<html><body>You scored <b>hello world</b> points.</body></html>","txt/html",null);
        //p.loadUrl("https://github.com");
        String str=bundle.getString("src");
        p.loadData(str,"text/html",null);
    }
}
