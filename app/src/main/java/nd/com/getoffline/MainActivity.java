
package nd.com.getoffline;

import android.os.Environment;
import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import android.speech.tts.TextToSpeech;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context context;
    String url,name;
    //SharedPreferences pref;
    public static int SDK_INT = android.os.Build.VERSION.SDK_INT;
    URL urlget;
    DbHandler dbase= new DbHandler(this);
    private List<PageInfo>pageList =new ArrayList<>();
    private RecyclerView recyclerview;
    private MyAdap adap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        //pref =getSharedPreferences("GOPref",Context.MODE_PRIVATE);
        recyclerview=(RecyclerView)findViewById(R.id.recview);
        adap=new MyAdap(pageList);
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(pLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adap);
        recyclerview.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerview ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Toast.makeText(context,pageList.get(position).getName()+" clicked",Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                        Toast.makeText(context,pageList.get(position).getName()+" long pressed",Toast.LENGTH_LONG).show();
                    }
                })
        );
        setPageList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "add the link to the webpage in the following prompt!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                   if(checkInternet())
                    givePrompt();
                    else
                       Snackbar.make(view,"You need a working internet connection!",Snackbar.LENGTH_LONG)
                               .setAction("Action",null).show();
            }
        });
    }
    private void setPageList()
    {
        pageList=dbase.getAllPages(pageList);
        
        adap.notifyDataSetChanged();
    }
    private boolean checkInternet()
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    boolean checkURL(String u)
    {
        try {
            URL url = new URL(u);
            URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            Toast.makeText(context, "the URL is not in a valid form!!!", Toast.LENGTH_LONG).show();// the URL is not in a valid form
            return false;
        } catch (IOException e) {
            Toast.makeText(context, " the connection couldn't be established!!!", Toast.LENGTH_LONG).show();// the connection couldn't be established
            return false;
        }
    }

    boolean checkNameNotExists(String name)
    {
        name=name+"";
        for(int i=0;i<pageList.size();++i)
        {
            if(name.equals(pageList.get(i).getName()))
            {
                //Toast.makeText();
                return false;
            }
        }
        return true;
    }

    public void makeEntry(int id, String name, String code)
    {
        PageInfo p = new PageInfo(id,name,code);
        pageList.add(p);
        adap.notifyDataSetChanged();
    }

    public void givePrompt()
    {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final EditText userInputU = (EditText) promptsView
                .findViewById(R.id.UserInputURL);
        userInputU.setText("");                     //to make sure that the previous url is not selected.
                                                    // that is, if the user is trying to scrape a page (which may be diffrent),
                                                    //  2nd time in succession, the vslue of previous page is not selected
        final EditText userInputN=(EditText) promptsView
                .findViewById(R.id.UserInputName);
        userInputN.setText("");                     //to make sure that the previous url is not selected.
                                                    // that is, if the user is trying to scrape a page (which may be diffrent),
                                                    //  2nd time in succession, the vslue of previous page is not selected
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                url=userInputU.getText().toString();
                                String code="";
                                try {
                                    urlget = new URL(url);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                name=userInputN.getText().toString();
                                boolean temp=checkNameNotExists(name);
                                if(checkURL(url)&&temp)
                                {
                                    BufferedReader reader = null;
                                    try {
                                        reader = new BufferedReader(new InputStreamReader(urlget.openStream(), "UTF-8"));

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        StringBuilder builder=new StringBuilder();
                                        for (String line; (line = reader.readLine()) != null;) {
                                            code=code+line;
                                            builder.append(line.trim());
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
                                    }
                                    int count = dbase.countPages()+1;
                                    dbase.addPage(new PageInfo(count,name,code));
                                    makeEntry(count, name, code);
                                    Log.d("added to the db",code);
                                }
                                else if(!temp)
                                {
                                    Toast.makeText(context,"name of 2 offline pages can't be same!",Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
    //add a function to report a page
}
