package com.recover.recover;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;

public class LocalFragment extends Fragment {


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//      For displaying items with Gridview component
//        View view = inflater.inflate(R.layout.local_fragment,container,false);
//        GridView local_grid_view = view.findViewById(R.id.local_grid_view);
//        ItemsAdapter itemsAdapter = new ItemsAdapter(getContext());
//        local_grid_view.setAdapter(itemsAdapter);
//        return view;

        View view = inflater.inflate(R.layout.local_fragment, container, false);
        //Bind buttons with actions
        ImageButton localPicBtn = view.findViewById(R.id.local_pic_btn);
        ImageButton localMusicBtn = view.findViewById(R.id.local_music_btn);
        final Activity activity = getActivity();

        localPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(activity, "picture");

            }//onClick
        });// localPicBtn View.OnClickListener

        localMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(activity, "music");
                Toast.makeText(activity, "Local musics", Toast.LENGTH_SHORT).show();
            }//onClick
        });//View.OnClickListener

        return view;

    }

    private void startActivity(Activity activity, String dataType) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(activity, GridViewActivity.class);
            Bundle b = new Bundle();
            b.putCharSequence("key", dataType);
            intent.putExtras(b);
            startActivity(intent);
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }
}
