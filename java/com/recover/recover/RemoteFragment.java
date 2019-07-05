package com.recover.recover;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RemoteFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment,container,false);
        TextView appDescriptionTV = view.findViewById(R.id.app_description);
        TextView contactMeTV = view.findViewById(R.id.contact_me);
        contactMeTV.setText(R.string.email);
        TextView srcPath= view.findViewById(R.id.src);
        srcPath.setText(R.string.src);


        contactMeTV.setMovementMethod(LinkMovementMethod.getInstance());
        readAppIntroduction(appDescriptionTV, contactMeTV);
        return view;
    }

    private void readAppIntroduction(TextView textView, TextView contactMeTV){
        String passage = "";
        String text="";
        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(getContext().getAssets().open("introduction")));
            while((text=reader.readLine())!=null){
                if(text.startsWith("About"))
                    text = text +'\n';
                if(text.startsWith("When the"))
                    text = '\n'+'\n' + text;
                passage = passage + text;
            }
            textView.setText(passage);
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
