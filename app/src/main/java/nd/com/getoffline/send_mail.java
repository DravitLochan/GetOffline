package nd.com.getoffline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by DravitLochan on 28-12-2016.
 */
public class send_mail extends AppCompatActivity{

    send_mail()
    {

    }

    public void send(String brkn_url)
    {
        final String to="dravit.lochan@gmail.com";
        final String sub="page can't be taken offline";
        final String body=brkn_url;
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_USER,to);
        email.putExtra(Intent.EXTRA_SUBJECT,sub);
        email.putExtra(Intent.EXTRA_TEXT,body);

        email.setType("message/rfc822");
        //startActivity(Intent.createChooser(email,"choose a client"));
        startActivity(Intent.createChooser(email,"Choose a client to mail us"));
    }
}
