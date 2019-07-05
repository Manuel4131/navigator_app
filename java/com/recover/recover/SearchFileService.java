package com.recover.recover;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.util.Stack;

public class SearchFileService extends Service {
    public static Thread searchFileThread;
    public static SearchFileRunnable searchFileRunnable;
    public static Boolean PauseSearching = false;

    @Override
    public void onCreate(){
        Log.d("Service onCreate","is called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        // to judge which btn is clicked
        String dataType = intent.getStringExtra("dataType");
        Log.d("onStartCommand", dataType);

        final Intent broadcastIntent = new Intent();
        SetUpBroadcastAction(dataType, broadcastIntent);
        String targetPath =setSearchTargetPath(dataType);
        String []chkExt = setSearchFileExt(dataType);

        searchFileRunnable = new SearchFileRunnable(broadcastIntent,targetPath,chkExt);
        searchFileThread = new Thread(searchFileRunnable);
        searchFileThread.start();
        return START_REDELIVER_INTENT;
    }

    private void SetUpBroadcastAction(String dataType, Intent broadcastIntent) {
        if(dataType.equals("picture")) {
            broadcastIntent.setAction("update-picture");
        }
        else if (dataType.equals("music")){
            broadcastIntent.setAction("update-music");
        }
    }

    private String setSearchTargetPath(String dataType) {
        String picturePath = "/DCIM";
        String musicPath = "/Music";
        File externalFSRoot = Environment.getExternalStorageDirectory();
        String externalFSRootPath = externalFSRoot.getAbsolutePath();
        if (dataType.equals("picture"))
            return externalFSRootPath += picturePath;
        else
            return externalFSRootPath += musicPath;
        }

    private String[] setSearchFileExt(String dataType) {
        String[] pic_ext = {".jpg",".png", ".bmp",".JPG", ".PNG", ".BMP"};
        String[] song_ext = {".mp3",".3gpp"};
        if(dataType.equals("picture"))
            return pic_ext;
        else
            return song_ext;
    }


    public class SearchFileRunnable implements Runnable{
        private Intent mBroadcastIntent;
        public Boolean runSearch = true;
        private String mStartPath;
        private String[] mChkExt;

        public SearchFileRunnable(Intent broadcastIntent, String startPath,String[] chkExt)
        {
            mBroadcastIntent = broadcastIntent;
            mStartPath = startPath;
            mChkExt = chkExt;
        }
        public void stopSearch(){
            runSearch = false;
        }
        public void run(){
                Log.d("run service","run broadcast intent");
                Log.d("External root path", mStartPath);

                Stack<String> searchingStack= new Stack<String>();
                searchingStack.push(mStartPath);
                Stack<String> visitedStack = new Stack<String>();
                String currentNode= null;

                while(!searchingStack.isEmpty() && runSearch){
                    while(PauseSearching){}

                    currentNode= (String)searchingStack.pop();
                    if(visitedStack.contains(currentNode))
                        continue;
                    visitedStack.push(currentNode);
                    Log.d("Current node", currentNode);
                    File[] files= new File(currentNode).listFiles();
                    int counter =0;
                    for(File f:files){
                        while(PauseSearching){}
                        if(f.isFile()) {
                            if(!runSearch) break;
                            for (String ext : mChkExt) {
                                while(PauseSearching){}
                                Log.d("searching","searching");
                                String filePath = f.getAbsolutePath();
                                if (filePath.endsWith(ext) && !filePath.startsWith(".")) {
                                    try {
                                        Thread.sleep(200);
                                        if(!runSearch) break;
                                        mBroadcastIntent.putExtra("path", filePath);
                                        sendBroadcast(mBroadcastIntent);
                                        if(PauseSearching){
                                            Log.d("ran", "run through: "+ String.valueOf(PauseSearching));
                                        }
                                    }
                                    catch (InterruptedException e){
                                        e.printStackTrace();
                                    }
                                }
                            }//inner for
                        }//files
                        else{
                            if(!runSearch) break;
                            if(!f.getName().startsWith("."))    // hidden directory like .thumbnails is forbidden.
                                searchingStack.push(f.getAbsolutePath());
                        }
                    }//for
                }//while
            if(searchingStack.isEmpty()) {
                Log.d("stack", "As searchingstack is empty.");
                stopSelf(); //when the work is completed.
            }
        }
    }

    /**
     * The method should be called when stopService(intent) or stopSelf() is called.
     * But it's not guaranteed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("service called", "onDestory is called");
//        searchFileThread.interrupt();
//        searchFileRunnable.stopSearch();

    }

    /**
     * As the service is available to be bounded. Otherwise, return null;
     * Certain components can bind the service by bindService() method.
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("onBind","Service is called");
        return null;
    }

}
