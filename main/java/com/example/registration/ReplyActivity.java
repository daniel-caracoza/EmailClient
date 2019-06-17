package com.example.registration;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReplyActivity extends AppCompatActivity {

    private ClientDBHelper db;
    private TextView text_to;
    private EditText edit_text_subject;
    private EditText edit_text_message;
    private TextView title;
    private TextView icon;
    private TextView sender;
    private TextView time;
    private TextView details;
    private EmailDetails email;
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply_layout);
        icon = findViewById(R.id.ricon);
        sender = findViewById(R.id.rsender);
        title = findViewById(R.id.rtitle);
        details = findViewById(R.id.rdetails);
        time = findViewById(R.id.rtime);
        text_to = findViewById(R.id.reply_text_to);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            icon.setText(bundle.getString("icon"));
            sender.setText(bundle.getString("sender"));
            text_to.setText(bundle.getString("sender"));
            title.setText(bundle.getString("title"));
            details.setText(bundle.getString("details"));
            time.setText(bundle.getString("time"));
        }
    }

    public void replyEmail(View v){
        edit_text_subject = (EditText) findViewById(R.id.reply_edit_text_subject);
        edit_text_message = (EditText) findViewById(R.id.reply_edit_text_message);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(new Date());
            EmailDetails email = new EmailDetails(text_to.getText().toString().trim(), edit_text_subject.getText().toString().trim(),
                    edit_text_message.getText().toString().trim(), date,"sent");
            //addEmail to database.
            ClientDBHelper dbhelper = new ClientDBHelper(ReplyActivity.this);
            dbhelper.addEmail(email, "sent");
            Snackbar.make(v, "Email Sent!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            finish();

    }


}
