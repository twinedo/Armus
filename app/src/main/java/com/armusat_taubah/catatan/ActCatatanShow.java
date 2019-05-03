package com.armusat_taubah.catatan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.armusat_taubah.R;

/**
 * Created by TWIN on 7/24/2016.
 */
public class ActCatatanShow extends AppCompatActivity {


    private Toolbar xToolbar;
    private TextView judulCtt;
    private TextView waktuCtt;
    private TextView isiCtt;

    //dbHelper
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    //default value untuk variabel judul dan isi
    private String judul = "Judul";
    private int id = 0;
    private String isi = "Isi";
    private String waktu = "waktu";

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_catatan_show);

            //fungsi Toolbar pada layout activity_catatan
            xToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(xToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            xToolbar.setNavigationIcon(R.drawable.ic_action_back);
            xToolbar.setNavigationOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            }));

            //inisialisasi
            dbHelper = new DBHelper(getApplicationContext());
            judulCtt = (TextView)findViewById(R.id.JudulCtt);
            isiCtt = (TextView)findViewById(R.id.IsiCtt);
            waktuCtt = (TextView)findViewById(R.id.WaktuCtt);

            Intent intent = getIntent();
            id = intent.getIntExtra("id",0);

            db=dbHelper.getReadableDatabase();
            //cursor memanggil judul, isi, waktu dari DBHelper
            Cursor cursor=dbHelper.getCatatanShow(db, id);
            db.close();

            //memanggil judul, isi, waktu dari cursor
            judul = cursor.getString(0).toString();
            isi = cursor.getString(1).toString();
            waktu = cursor.getString(2).toString();

            //mencetak judul, isi, dan waktu
            judulCtt.setText(judul);
            isiCtt.setText(isi);
            waktuCtt.setText(waktu);

    }
}
