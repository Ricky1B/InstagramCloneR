package com.rikin.jain.instagramcloner;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {
    private EditText edtProfileName, edtBio, edtProfesssion, edtHobbies, edtFavouriteSport;
    private Button btnUpdateInfo;


    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtBio = view.findViewById(R.id.edtBio);
        edtProfesssion = view.findViewById(R.id.edtProfession);
        edtHobbies = view.findViewById(R.id.edtHobbies);
        edtFavouriteSport = view.findViewById(R.id.edtFavouriteSport);
        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);
        final ParseUser parseUser = ParseUser.getCurrentUser();
        if(parseUser.get("profileName") == null){
            edtProfileName.setText("");
        } else {
            edtProfileName.setText(parseUser.get("profileName").toString());
        }
        if(parseUser.get("bio") == null){
            edtBio.setText("");
        } else {
            edtBio.setText(parseUser.get("bio").toString());
        }
        if(parseUser.get("profession") == null){
            edtProfesssion.setText("");
        } else {
            edtProfesssion.setText(parseUser.get("profession").toString());
        }
        if(parseUser.get("hobbies") == null){
            edtHobbies.setText("");
        } else {
            edtHobbies.setText(parseUser.get("hobbies").toString());
        }
        if(parseUser.get("favouriteSports") == null){
            edtFavouriteSport.setText("");
        } else {
            edtFavouriteSport.setText(parseUser.get("favouriteSports").toString());
        }


        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName", edtProfileName.getText().toString());
                parseUser.put("bio", edtBio.getText().toString());
                parseUser.put("profession", edtProfesssion.getText().toString());
                parseUser.put("hobbies", edtHobbies.getText().toString());
                parseUser.put("favouriteSports", edtFavouriteSport.getText().toString());
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Updating Info");
                progressDialog.show();
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(getContext(),"Info Updated", FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
                        } else{
                            FancyToast.makeText(getContext(),e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();

                        }
                        progressDialog.dismiss();

                    }
                });

            }
        });
        return view;
    }

}
