package com.armusat_taubah.catatan;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.armusat_taubah.R;

public class ActCatatanTodo extends AppCompatActivity {

    private static final String TAG = "catatan";

    private Toolbar xToolbar;
    private EditText deskJudul;
    private EditText deskIsi;
    private Button cttSimpan;

    //variable memilih untuk membuat baru atau perbarui catatan
    private boolean isEdit;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    //variabel akan menampilkan judul saat edit
    private String editJudul;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan_todo);
        //fungsi Toolbar
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

        deskJudul = (EditText) findViewById(R.id.deskJudul);
        deskIsi = (EditText) findViewById(R.id.deskIsi);
        cttSimpan = (Button) findViewById(R.id.cttSimpan);

        dbHelper = new DBHelper(getApplicationContext());

        //mengatur tampilan buat atau edit
        Intent intent = getIntent();
        //jika user ingin edit, maka form judul akan ditimpa dengan data sebelumnya
        editJudul = intent.getStringExtra("judul");
        id = intent.getIntExtra("id",0);

        //membuat value isEdit menjadi true, selain itu salah
        isEdit = intent.getBooleanExtra("isEdit", false);

        //cek apakah user ingin mengedit data
        if(isEdit){
            Log.d(TAG, "isEdit");
            db= dbHelper.getReadableDatabase();
            Cursor c = dbHelper.getCatatanShow(db, id);
            db.close();
            //menampilkan judul dan isi
            deskJudul.setText(c.getString(0));
            deskIsi.setText(c.getString(1));

            //lalu mengubah text tombol tadinya "simpan", menjadi "perbarui data"
            cttSimpan.setText(getResources().getString(R.string.ctt_perbarui));
        }

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative1);
        layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });


        //set tombol cttSimpan
        cttSimpan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String judul = deskJudul.getText().toString();
                String isi = deskIsi.getText().toString();

            if (judul.equals("") || isi.equals("")) {
                Toast.makeText(getApplicationContext(), "Data tidak boleh kosong!!", Toast.LENGTH_LONG).show();
                return;
            }

            //menambahkan catatan ke db
            if (!isEdit){
                //jika buat baru maka kita hanya menambah data baru
                dbHelper = new DBHelper(getApplicationContext());
                dbHelper.cttTambah(judul, isi);
                finish();
            }else{
                //edit/perbarui data
                dbHelper = new DBHelper(getApplicationContext());
                dbHelper.editCtt(id, judul, isi);
                finish();
            }
            }
        });
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @Override
    protected void onPause(){
        super.onPause();
        dbHelper.close();
    }
}