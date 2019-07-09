package com.recover.recover;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mImages = new ArrayList<String>();
    private Context mCtx;
    private String Tag = new String("RecyclyerViewAdapter");
    private int dataSize;
    private int totalRowNumber ; // 4 pictures in one row
    private int itemNumberEachRow=4;

    public RecyclerViewAdapter(Context ctx, ArrayList<String> Images){
        Log.d("RecyclerViewAdapter", "Constructor");
        mImages = Images;
        mCtx = ctx;
        dataSize = mImages.size();
        totalRowNumber = dataSize/itemNumberEachRow + ((dataSize%itemNumberEachRow == 0)?0:1);
    }

    public void updateItem(String newItem){
        mImages.add(newItem);
        dataSize = mImages.size();
        totalRowNumber = dataSize/itemNumberEachRow + ((dataSize%itemNumberEachRow == 0)?0:1);

        int row = (mImages.size()-1 ) /4;
        Log.d("compare",String.valueOf(mImages.size())+" , "+String.valueOf(row));
        this.notifyItemChanged(row);    // row is row line number

        Log.d("updateItem mImages size",String.valueOf(mImages.size()));

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_layout,viewGroup,false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    /**
     * Called. Every time when each viewholder is created.
     * @param viewHolder
     * @param pos
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {
        Log.d(Tag, "onBindViewHolder called");
        String msg = "onBVH is called,pos";
        for(int i=0;i<itemNumberEachRow;i++){
            if(pos * itemNumberEachRow + i < dataSize) {
                Glide.with(mCtx)
                        .asBitmap()
                        .load(mImages.get(pos * itemNumberEachRow + i))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into(viewHolder.selectImageView(i));
                viewHolder.bindImageEvent(i,pos * itemNumberEachRow + i );
            }
        }


    }

    /**
     * The item count will decide how many times to call onBindViewHolder()
     * @return
     */
    @Override
    public int getItemCount() {
//        Log.d("totalRowNumber",String.valueOf(totalRowNumber));
        return totalRowNumber;
    }

    /**
     * Hold each listitem object in the memory.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ArrayList<ImageView> ImageViews= new ArrayList<ImageView>();
        ImageView image1;
        ImageView image2;
        ImageView image3;
        ImageView image4;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.image1);
            ImageViews.add(image1);
            image2 = itemView.findViewById(R.id.image2);
            ImageViews.add(image2);
            image3 = itemView.findViewById(R.id.image3);
            ImageViews.add(image3);
            image4 = itemView.findViewById(R.id.image4);
            ImageViews.add(image4);

            parentLayout = itemView.findViewById(R.id.listitem_parent);
            itemView.setOnClickListener(this);
        }

        public void bindImageEvent(int imageNumber,final int pos){
            selectImageView(imageNumber).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("image number", String.valueOf(pos));
                    Intent detailViewIntent=new Intent(mCtx,DetailPictureActivity.class);
                    detailViewIntent.putExtra("path", mImages.get(pos));
                    mCtx.startActivity(detailViewIntent);
                }
            });
        }

        /**
         * Return corresponded ImageView depending on pos.
         * Like: pos is 2 then ch ImageView2.
         * @param pos
         * @return
         */
        public ImageView selectImageView(int pos){
            return ImageViews.get(pos);
        }

        @Override
        public void onClick(View v) {
            // no event yet
        }// onClick
    }// class ViewHolder
}
