package com.recover.recover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class RemoteFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment,container,false);
        TextView selfIntroView = view.findViewById(R.id.self_intro);
        readAppIntroduction(selfIntroView);
        return view;
    }

    private void readAppIntroduction(TextView textView){
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
//                if(text.startsWith("Any advice")|text.startsWith("Contact"))
//                    text = '\n'+ text;
//                contactTV.
            }
            textView.setText(passage);
//            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
