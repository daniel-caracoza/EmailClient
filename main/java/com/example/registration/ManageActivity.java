package com.example.registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ManageActivity extends AppCompatActivity implements PasswordDialog.DialogListener, NameDialog.NameDialogListener  {

    private TextView username_text;
    private TextView name_text;
    private ClientDBHelper db;
    private String username;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        Bundle bundle = getIntent().getExtras();
        db = new ClientDBHelper(ManageActivity.this);
        username = bundle.get("username").toString();
        username_text = (TextView) findViewById(R.id.username_text);
        name_text = (TextView)findViewById(R.id.name_text);
        name = db.getName(username);
        if(bundle!=null){
            username_text.setText(bundle.getString("username"));
            name_text.setText(name);
        }
    }

    public void changeName(View v){
        NameDialog nameDialog = new NameDialog();
        nameDialog.show(getSupportFragmentManager(), "namediag");
    }
    public void changePassword(View v){
        PasswordDialog passwordDialog = new PasswordDialog();
        passwordDialog.show(getSupportFragmentManager(), "passdiag");
    }
    @Override
    public void applyText(String cpass, String npass, String confirm){
        User user = db.getUser(username);
        user.setPassword(npass);
        db.updateUser(user);
    }
    @Override
    public void nameApplyText(String name){
        User user = db.getUser(username);
        user.setName(name);
        db.updateUser(user);
    }
}
