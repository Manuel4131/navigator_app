package com.recover.recover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.content.Intent;
import android.widget.ImageView;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {
    private ArrayList<String> images = new ArrayList<String>();
    RecyclerViewAdapter pictureAdapter;
    OneItemPerRowRecyclerViewAdapter songAdapter;
    private IntentFilter mIntentFilter;
    public static Intent serviceIntent;
    private String dataType;
    private ImageView detailPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        Bundle b = getIntent().getExtras();
        if(b!=null){
            dataType = String.valueOf(b.getCharSequence("key"));
            Log.d("dataType", dataType);
        }

        setAdapterAndLayoutManager(dataType);
// Service and broadcastreceiver
        setUpBroadcastReceiverIntent();

        serviceIntent = new Intent(this, SearchFileService.class);
        serviceIntent.putExtra("dataType", dataType);
        startService(serviceIntent);
        Intent openPicIntent = new Intent();
        openPicIntent.setAction(Intent.ACTION_VIEW);
    }

    private void setUpBroadcastReceiverIntent() {
        mIntentFilter = new IntentFilter();
        if(dataType.equals("picture")) {
            mIntentFilter.addAction("update-picture");
        }
        else if(dataType.equals("music")) {
            mIntentFilter.addAction("update-music");
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("update-picture")){
                Log.d("mReceiver","update-picture is called");
                pictureAdapter.updateItem(intent.getStringExtra("path"));
            }
            else if(intent.getAction().equals("update-music")){
                Log.d("mReceiver","update-music is called");
                songAdapter.updateItem(intent.getStringExtra("path"));
            }

        }
    };
    @Override
    public void onResume(){
        super.onResume();
        Log.d("called", "onResume");
        registerReceiver(mReceiver,mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
        Log.d("called", "onPause");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("called", "onDestory");
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(serviceIntent);
        Log.d("called", "onStop");
    }

    /**
     * After collectData() was called, run this function to display.
     *
     */
    private void setAdapterAndLayoutManager(String dataType){
        Log.d("dataType","setAdapterAndLayoutManager methods");
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        if(dataType.equals("picture")) {
            pictureAdapter = new RecyclerViewAdapter(this, images);
            recyclerView.setAdapter(pictureAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));//?
        }
        else if (dataType.equals("music")){
            ArrayList<String> testSongs = new ArrayList<String>();
            songAdapter = new OneItemPerRowRecyclerViewAdapter(this,testSongs);
            recyclerView.setAdapter(songAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    // Delete
    // test 2nd thread
    private class runRecyclerViewThread implements Runnable{
        private Context ctx;
        private ArrayList<String> images;
        public  runRecyclerViewThread(Context pctx,ArrayList<String> pimages){
            ctx = pctx;
            images = pimages;
        }


        public void run(){
            RecyclerView recyclerView= findViewById(R.id.recyclerview);
            pictureAdapter = new RecyclerViewAdapter(ctx,images);
            recyclerView.setAdapter(pictureAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        }
    }

}
