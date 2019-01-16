package com.rikin.jain.instagramcloner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UserPosts extends AppCompatActivity {
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);
        linearLayout = findViewById(R.id.linearLayout);
        Intent receivedIntentObject = getIntent();
        final String receivedUserName = receivedIntentObject.getStringExtra("username");
        FancyToast.makeText(this,receivedUserName, FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
        setTitle(receivedUserName + "'s Posts");
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username", receivedUserName);
        parseQuery.orderByDescending("createdAt");
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size() >0 && e == null){
                    for(ParseObject posts: objects){
                        final TextView postDescription = new TextView(UserPosts.this);
                        postDescription.setText(posts.get("image_des")+ "");
                        ParseFile postPicture = (ParseFile) posts.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if( data != null && e ==null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,data.length);
                                    ImageView postImageView = new ImageView(UserPosts.this);
                                    LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageParams.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(imageParams);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);
                                    LinearLayout.LayoutParams descriptionParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    descriptionParams.setMargins(5,5,5,15);
                                    postDescription.setLayoutParams(descriptionParams);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setBackgroundColor(Color.BLUE);
                                    postDescription.setTextColor(Color.WHITE);
                                    postDescription.setTextSize(30f);
                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDescription);
                                } else{
                                    FancyToast.makeText(UserPosts.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                                }
                            }
                        });
                    }
                }else{
                    FancyToast.makeText(UserPosts.this,receivedUserName +" doesn't have any posts", FancyToast.LENGTH_SHORT, FancyToast.INFO,true).show();
                    finish();
                }
                dialog.dismiss();

            }

        });
    }
}
