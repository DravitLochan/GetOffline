package nd.com.getoffline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class send_mail_test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail_test);

        Button s = (Button) findViewById(R.id.isend);

    }

        public void send(View view)
        {
            EditText et1 = (EditText) findViewById(R.id.to);
            EditText et2 = (EditText) findViewById(R.id.sub);
            EditText et3 = (EditText) findViewById(R.id.body);
            String to= et1.getText().toString();
            String sub= et2.getText().toString();
            String body= et3.getText().toString();
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_USER,to);
            email.putExtra(Intent.EXTRA_SUBJECT,sub);
            email.putExtra(Intent.EXTRA_TEXT,body);

            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email,"choose a client"));
        }
    }

