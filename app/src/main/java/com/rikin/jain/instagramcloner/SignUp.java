package com.rikin.jain.instagramcloner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private Button btnSave, btnGetAll, btnGetBoxer;
    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    private TextView txtGetData;
    private String allKickBoxers;
    private String allBoxerPunchSpeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSave = findViewById(R.id.btnSave);
        btnGetAll = findViewById(R.id.btnGetAll);
        btnGetBoxer = findViewById(R.id.btnGetBoxer);
        btnSave.setOnClickListener(SignUp.this);
        edtName = findViewById(R.id.edtName);
        edtPunchSpeed=findViewById(R.id.edtPunchSpeed);
        edtPunchPower=findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower= findViewById(R.id.edtKickPower);
        txtGetData = findViewById(R.id.txtGetData);
        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("2heJ3IoCjN", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if ( object != null && e==null){
                            txtGetData.setText(object.get("name") + "-" + " Punch Speed :" + object.get("punch_speed"));

                        }
                    }
                });
            }
        });
        btnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allKickBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e){
                        if (e == null){
                            if(objects.size() >0){
                                for (ParseObject kickBoxer: objects){
                                    allKickBoxers = allKickBoxers + kickBoxer.get("name") + "\n";
                                    FancyToast.makeText(SignUp.this, allKickBoxers, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();



                                }

                            }else{
                                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
            }
        });
        btnGetBoxer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allBoxerPunchSpeed="";
                ParseQuery<ParseObject> boxer = ParseQuery.getQuery("Boxer");
                boxer.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e==null){
                            if(objects.size()>0){
                                for(ParseObject boxer : objects){

                                    allBoxerPunchSpeed = allBoxerPunchSpeed + boxer.get("punch_speed")+ "\n";
                                    


                                    FancyToast.makeText(SignUp.this, allBoxerPunchSpeed  +  boxer.getObjectId(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                    }
                            }else{
                                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                            }
                        }
                    }
                });

            }
        });

    }


    @Override
    public void onClick(View v) {
       try {
           final ParseObject kickBoxer = new ParseObject("KickBoxer");
           kickBoxer.put("name", edtName.getText().toString());
           kickBoxer.put("punch_speed", Integer.parseInt(edtPunchSpeed.getText().toString()));
           kickBoxer.put("punch_power", Integer.parseInt(edtPunchPower.getText().toString()));
           kickBoxer.put("kick_speed", Integer.parseInt(edtKickSpeed.getText().toString()));
           kickBoxer.put("kick_power", Integer.parseInt(edtKickPower.getText().toString()));
           kickBoxer.saveInBackground(new SaveCallback() {
               @Override
               public void done(ParseException e) {
                   if (e == null) {
                       FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " is saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                   } else {
                       Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               }
           });
       } catch (Exception e){
           FancyToast.makeText(this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

       }

    }
}
