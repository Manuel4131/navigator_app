package com.recover.recover;

import android.widget.TextView;

public class SongData {
    private String songname;
    private String singer;
    private TextView albumPic;

    // setter
    public void setSongname(String songname) {
        this.songname = songname;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setAlbumPic(TextView albumPic) {
        this.albumPic = albumPic;
    }
    // getter
    public String getSongname() {
        return songname;
    }

    public String getSinger() {
        return singer;
    }

    public TextView getAlbumPic(){
        return albumPic;
    }



}
