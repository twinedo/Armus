package com.armusat_taubah;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;


/**
 * Created by TWIN on 6/23/2016.
 */
public class ImageAdapter extends ArrayAdapter<Object> {
    Context context;
    public ImageAdapter (Context c) {
        super(c,0);
        this.context=c;
    }
    public int getCount() {
        return 4;}
    // membuat ImageView untuk setiap item yang dipanggil oleh adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View row= convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_item, parent, false);

            TextView textViewTitle = (TextView) row.findViewById(R.id.textView);
            ImageView imageViewIte = (ImageView) row
                    .findViewById(R.id.imageView1);
            switch (position) {
                case 0:
                    textViewTitle.setText(R.string.title1);
                    imageViewIte.setImageResource(R.mipmap.catatan);
                    break;
                case 1:
                    textViewTitle.setText(R.string.title2);
                    imageViewIte.setImageResource(R.mipmap.jadwal);
                    break;
                case 2:
                    textViewTitle.setText(R.string.title3);
                    imageViewIte.setImageResource(R.mipmap.penanda);
                    break;
                case 3:
                    textViewTitle.setText(R.string.title4);
                    imageViewIte.setImageResource(R.mipmap.tentang);
                    break;
            }
        }return row;
    }
}
