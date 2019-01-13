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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity  {
    private EditText edtEmail, edtUsername, edtPassword;
    private Button btnSignUp, btnLogIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Sign Up");
        setContentView(R.layout.activity_sign_up);
        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    btnSignUp.callOnClick();

                }
                return false;
            }
        });
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( edtEmail.getText().toString().equals("") || edtUsername.getText().toString().equals("")|| edtPassword.getText().toString().equals("")){
                    FancyToast.makeText(SignUp.this,"Email, Username, Password cannot be empty!",FancyToast.LENGTH_LONG,FancyToast.INFO,true).show();

                } else {
                    final ParseUser instaUser = new ParseUser();
                    instaUser.setEmail(edtEmail.getText().toString());
                    instaUser.setUsername(edtUsername.getText().toString());
                    instaUser.setPassword(edtPassword.getText().toString());
                    final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                    progressDialog.setMessage("Signing Up");
                    progressDialog.show();

                    instaUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null && ParseUser.getCurrentUser() != null) {
                                FancyToast.makeText(SignUp.this, instaUser.getUsername() + " is signed up successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                  transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                            }
                            progressDialog.dismiss();
                        }
                    });

                }

            }

        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, LogIn.class);
                startActivity(intent);
            }
        });








    }
    public void signUpLayoutIsTapped(View view){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void transitionToSocialMediaActivity(){
        Intent intent = new Intent(SignUp.this, SocialMedia.class);
        startActivity(intent);
        finish();
    }




}
