package com.example.registration;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ComposeActivity extends AppCompatActivity {
    EditText edit_text_to;
    EditText edit_text_subject;
    EditText edit_text_message;
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose);
    }
    public void sendEmail(View v){
        edit_text_to = (EditText) findViewById(R.id.edit_text_to);
        edit_text_subject = (EditText) findViewById(R.id.edit_text_subject);
        edit_text_message = (EditText) findViewById(R.id.edit_text_message);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(new Date());
        if(!edit_text_to.getText().toString().trim().isEmpty()) {
            EmailDetails email = new EmailDetails(edit_text_to.getText().toString().trim(), edit_text_subject.getText().toString().trim(),
                    edit_text_message.getText().toString().trim(), date,"sent");
            //addEmail to database.
            ClientDBHelper dbhelper = new ClientDBHelper(ComposeActivity.this);
            dbhelper.addEmail(email, "sent");
            Snackbar.make(v, "Email Sent!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            finish();
        }else Snackbar.make(v, "You need to at least have a recipient!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

    }
}
