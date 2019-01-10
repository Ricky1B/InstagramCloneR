package com.rikin.jain.instagramcloner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogIn extends AppCompatActivity {
    private Button btnLogIn2, btnSignUp2;
    private EditText edtEmail2, edtPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Log In");
        setContentView(R.layout.activity_log_in);
        edtEmail2 = findViewById(R.id.edtEmail2);
        edtPassword2 = findViewById(R.id.edtPassword2);
        edtPassword2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    btnLogIn2.callOnClick();
                }
                    return false;
            }
        });
        btnLogIn2 = findViewById(R.id.btnLogIn2);
        btnSignUp2 = findViewById(R.id.btnSignUp2);
        btnLogIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(LogIn.this);
                progressDialog.setMessage("Logging In");
                progressDialog.show();
                ParseUser.logInInBackground(edtEmail2.getText().toString(), edtPassword2.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if( user != null && e == null){
                            FancyToast.makeText(LogIn.this, user.getUsername() + " is logged in successfully",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            transitionToSocialMediaActivity();
                        } else {
                            FancyToast.makeText(LogIn.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                        }
                        progressDialog.dismiss();
                    }
                });


            }
        });

        btnSignUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    public void logInLayoutIsTapped(View view){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void transitionToSocialMediaActivity(){
        Intent intent = new Intent(LogIn.this, SocialMedia.class);
        startActivity(intent);
    }

}
