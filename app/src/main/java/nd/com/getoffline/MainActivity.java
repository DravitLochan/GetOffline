
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

                    givePrompt();
            }
        });
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


}

