package com.recover.recover;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.TextView;


public class ItemsAdapter extends BaseAdapter {
    private final String[] items = {"Music", "Movie", "Document", "Setting"};
    private final Context ctx;

    public ItemsAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int p){
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView cell_ctx = new TextView(ctx);
        cell_ctx.setText(items[position]);
        return cell_ctx;
    }
}
