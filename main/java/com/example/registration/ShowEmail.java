package com.example.registration;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowEmail extends AppCompatActivity {

    private EmailDetails email;
    private ClientDBHelper db;
    private String box;
    private int id;
    TextView icon;
    TextView sender;
    TextView title;
    TextView details;
    TextView time;
    ImageView important;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_email);
        db = new ClientDBHelper(ShowEmail.this);
        icon = findViewById(R.id.icon);
        sender = findViewById(R.id.sender);
        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        time = findViewById(R.id.time);
        important = findViewById(R.id.important);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            icon.setText(bundle.getString("icon"));
            ((GradientDrawable) icon.getBackground()).setColor(bundle.getInt("colorIcon"));
            sender.setText(bundle.getString("sender"));
            title.setText(bundle.getString("title"));
            details.setText(bundle.getString("details"));
            time.setText(bundle.getString("time"));
            box = bundle.getString("box");
            id = bundle.getInt("id");
            email = new EmailDetails(sender.getText().toString(), title.getText().toString(),
                    details.getText().toString(), time.getText().toString(), box);
        }

    }
    public void sendToTrash(View view){
        //send to trash table in database
        //delete from this mailbox and add to the other
        db.deleteEmail(id, box);
        db.addEmail(email,"trash");
        displayToast(getString(R.string.sent_to_trash));
    }

    public void reply(View view){
        //start reply activity with "To" field filled out with "From"
        Intent intent = new Intent(ShowEmail.this, ReplyActivity.class);
        intent.putExtra("icon", icon.getText().toString());
        intent.putExtra("sender", sender.getText().toString());
        intent.putExtra("title", title.getText().toString());
        intent.putExtra("details", details.getText().toString());
        intent.putExtra("time", time.getText().toString());
        startActivity(intent);
        finish();
    }

    public void important(View view){
        //send to important table in database
        db.addEmail(email,"important");
        displayToast(getString(R.string.sent_to_important));
    }

    public void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
