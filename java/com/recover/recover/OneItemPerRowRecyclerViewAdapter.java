package com.recover.recover;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OneItemPerRowRecyclerViewAdapter extends RecyclerView.Adapter<OneItemPerRowRecyclerViewAdapter.ViewHolderOneItem> {

    private ArrayList<String> mItems= new ArrayList<String>();
    private int totalNumber;
    private Context mCtx;
    private MediaMetadataRetriever mmr =new MediaMetadataRetriever();


    public OneItemPerRowRecyclerViewAdapter(Context ctx, ArrayList<String> items) {
        mCtx= ctx;
        mItems = items;
        totalNumber=mItems.size();
    }

    public void updateItem(String filePath){
        mItems.add(filePath);
        totalNumber=mItems.size();
        this.notifyItemChanged(totalNumber-1);
    }

    @NonNull
    @Override
    public OneItemPerRowRecyclerViewAdapter.ViewHolderOneItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.oneitem_perow_layout,viewGroup,false);
        OneItemPerRowRecyclerViewAdapter.ViewHolderOneItem viewholder = new OneItemPerRowRecyclerViewAdapter.ViewHolderOneItem(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOneItem viewHolderOneItem, int pos){
        Log.d("BVH", "onBindViewHolder called");
        try{
            mmr.setDataSource(mItems.get(pos));
            String songName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String singer = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

            if(songName!=null)
                viewHolderOneItem.songname.setText(songName);
            if(singer!=null)
                viewHolderOneItem.singer.setText(singer);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount(){
        return mItems.size();
    }


    public class ViewHolderOneItem extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView songIcon;
        TextView songname;
        TextView singer;
        RelativeLayout parentLayout;

        public ViewHolderOneItem(@NonNull View itemView) {
            super(itemView);
            songIcon= itemView.findViewById(R.id.song_icon);
            songname= itemView.findViewById(R.id.song_name);
            singer= itemView.findViewById(R.id.singer);
            parentLayout = itemView.findViewById(R.id.oneitemonerow_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("clicksong","hitIt");
            Intent callExternalMusicAppIntent = new Intent();
            callExternalMusicAppIntent.setAction(Intent.ACTION_VIEW);
            String path = mItems.get(getAdapterPosition());

            callExternalMusicAppIntent.setDataAndType(Uri.parse(mItems.get(getAdapterPosition())),"audio/*");
            if(callExternalMusicAppIntent.resolveActivity(mCtx.getPackageManager())!=null)
                mCtx.startActivity(callExternalMusicAppIntent);
            else{
                Toast.makeText(mCtx,R.string.no_app_works,Toast.LENGTH_SHORT).show();
            }
        }
    }

}
