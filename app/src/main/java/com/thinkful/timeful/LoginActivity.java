package com.thinkful.timeful;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void loginButtonClicked(View v)
    {
        TextView emailTV = (TextView) this.findViewById(R.id.emailLogin);
        TextView passwordTV = (TextView) this.findViewById(R.id.passwordLogin);
        ParseUser.logInInBackground(emailTV.getText().toString(), passwordTV.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user != null)
                {
                    Log.i("LOGIN", "" + user.getObjectId());
                    Log.i("CurrentUser", "" + ParseUser.getCurrentUser().getObjectId());
                    TimefulCore.currentUser =  user;
                    loginPush();
                }

                else
                {
                    Log.i("ERROR", "" + e.getCode());
                    Toast badLogin = Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT);
                    badLogin.show();
                }
            }
        });
    }

    public void loginPush()
    {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
    public void registerButtonClicked(View v)
    {
        Intent intent = new Intent(this,RegisterActivity.class);
        this.startActivity(intent);
    }


}
