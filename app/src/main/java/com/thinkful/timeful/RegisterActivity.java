package com.thinkful.timeful;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

    public void registerButtonPressed(View v)
    {


        TextView usernameTV = (TextView) this.findViewById(R.id.emailRegister);
        TextView emailTV = (TextView) this.findViewById(R.id.emailRegister);
        TextView passwordTV = (TextView) this.findViewById(R.id.registerPassword);
        TextView confirmPasswordTV = (TextView) this.findViewById(R.id.registerConfirmPassword);


        Users user = new Users();
        user.setUsername( usernameTV.getText().toString());
        user.setEmail(emailTV.getText().toString());
        user.addExp(0);
        user.setLevel(0);

       String password = passwordTV.getText().toString();
        String cPassword =  confirmPasswordTV.getText().toString();

        if (password.equals(cPassword)) {
            user.setPassword(password);
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {

                    if (e == null) {
                        backToLogin();
                    } else if (e.getCode() == ParseException.USERNAME_TAKEN) {
                        Log.i("WRONG USER", "" + e.getCode());
                        Toast wrongUserToast = Toast.makeText(getApplicationContext(), "Account with that e-mail already exists", Toast.LENGTH_SHORT);
                        wrongUserToast.show();
                    } else if (e.getCode() == ParseException.INVALID_EMAIL_ADDRESS) {
                        Log.i("EMAIL WRONG", "" + e.getCode());
                        Toast badEmailToast = Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT);
                        badEmailToast.show();
                    } else {
                        Log.i("WRONG EMAIL", "" + e.getCode());
                    }
                }

            });
    }
        else
        {
            Toast passwordNotMatch = Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT);
            passwordNotMatch.show();
        }



// other fields can be set just like with ParseObject



        }

    public void backToLogin()
    {
        super.onBackPressed();

    }
}


