package com.rikin.jain.instagramcloner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity {
    private EditText edtUserNameSignUp, edtPasswordSignUp, edtUserNameLogIn, eedtPasswordLogIn;
    private Button btnSignUp, btnLogIn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);
        edtUserNameSignUp = findViewById(R.id.edtUserNameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        edtUserNameLogIn = findViewById(R.id.edtUserNameLogIn);
        eedtPasswordLogIn = findViewById(R.id.edtPasswordLogIn);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSIgnUp);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(edtUserNameLogIn.getText().toString(), eedtPasswordLogIn.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null && user != null){
                            FancyToast.makeText(SignUpLoginActivity.this, user.getUsername() + " is logged in successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                            } else {
                            FancyToast.makeText(SignUpLoginActivity.this, e.getMessage(),FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                        }
                    }
                });

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(edtUserNameSignUp.getText().toString());
                appUser.setPassword(edtPasswordSignUp.getText().toString());
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            FancyToast.makeText(SignUpLoginActivity.this, appUser.getUsername() + " is signed up succesfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                        } else{
                            FancyToast.makeText(SignUpLoginActivity.this, e.getMessage(),FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    }
                });
            }
        });
    }
}
