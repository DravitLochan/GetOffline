
package nd.com.getoffline;

import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import java.net.HttpURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import android.speech.tts.TextToSpeech;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity {

    //private EditText ed_url;
    //if it NEEDS to be final, make the change
    Context context;
    //private EditText result;
    String url,name;
    SharedPreferences pref;
    public static int SDK_INT = android.os.Build.VERSION.SDK_INT;
    URL urlget;

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
        pref =getSharedPreferences("GetOfflinePref",Context.MODE_PRIVATE);


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

    private boolean checkInternet() {

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
            HttpURLConnection.setFollowRedirects(false);        //
            HttpURLConnection con = (HttpURLConnection) new URL(u).openConnection();
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

        final EditText userInputN=(EditText) promptsView
                .findViewById(R.id.UserInputName);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                url=userInputU.getText().toString();
                                String title="";
                                try {
                                    urlget = new URL(url);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                name=userInputN.getText().toString();
                                SharedPreferences.Editor editor =pref.edit();
                                editor.putString(name,url.toString());
                                editor.commit();

                                /*
                                * code for checking the existancec of url goes here
                                 */

                                if(checkURL(url))
                                {
                                   //scrapePage(urlget);

                                    BufferedReader reader = null;
                                    try {
                                        reader = new BufferedReader(new InputStreamReader(urlget.openStream(), "UTF-8"));

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        StringBuilder builder=new StringBuilder();
                                        for (String line; (line = reader.readLine()) != null;) {
                                            title=title+line;
                                            builder.append(line.trim());
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
                                    }

                                    EditText t = (EditText) findViewById(R.id.title);
                                    t.setText(title);

                                }
                                else
                                {
                                    Toast.makeText(context, "Invalid URL!!!", Toast.LENGTH_LONG).show();
                                }
                                //below onwards, for cleaning purpose, add the code to scrapePage() method.
                                //maybe you need to put it in the async's doInBackGround().

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


    public void scrapePage(URL url)
    {
        /*
         * after confirmation of url being correct, scrape the page.
         *  after that make a .html file with the name given by the user and save in the default directory.
         *  on click of the recycler view's entity, open the file in default browser.
         *  also store the path of the .html file in shared preferences.
         */

        EditText title ;
        title= (EditText) findViewById(R.id.title);

    }



}

