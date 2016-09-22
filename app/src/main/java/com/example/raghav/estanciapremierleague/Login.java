package com.example.raghav.estanciapremierleague;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void login1(View view)
    {
        EditText username = (EditText) findViewById(R.id.editText1);
        EditText password = (EditText) findViewById(R.id.editText2);
        final TextView c5=(TextView) findViewById(R.id.showmsg);

        if(username.getText().toString().equals("esladmin") && password.getText().toString().equals("admin")){
            c5.setText("Password Accepted");
            Intent intent=new Intent(this,UpdateDB.class);
            startActivity(intent);

            //correct password
        }else{
            c5.setText("Password Denied");

            //wrong password
        }

    }
}
