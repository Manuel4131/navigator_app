package com.recover.recover;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailPictureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_picture);
        ImageView detailImgView = findViewById(R.id.detail_img);
        Bundle b = getIntent().getExtras();
        if(b!=null){
            String imgPath = b.getString("path");
            detailImgView.setImageBitmap(BitmapFactory.decodeFile(imgPath));
        }
    }


}
