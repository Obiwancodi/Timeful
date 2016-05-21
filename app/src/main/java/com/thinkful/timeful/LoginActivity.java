package com.thinkful.timeful;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.LinkedList;

public class LoginActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView emailTV = (TextView) this.findViewById(R.id.emailLogin);


        if(emailTV.requestFocus())

        {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }


        if(ParseUser.getCurrentUser() != null)
        {
            TimefulCore.currentUser = ParseUser.getCurrentUser();
            loginPush();
        }




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
        TextView emailTV = (TextView) this.findViewById(R.id.emailLogin);
        TextView passwordTV = (TextView) this.findViewById(R.id.passwordLogin);



        if (emailTV.getText().toString().isEmpty() && passwordTV.getText().toString().isEmpty()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            this.startActivity(intent);
        }

        else {
            Toast dumbAss = Toast.makeText(getApplicationContext(),"If you have already registered, push login; otherwise clear the fields and push register", Toast.LENGTH_LONG);
            dumbAss.show();
        }
    }


}
