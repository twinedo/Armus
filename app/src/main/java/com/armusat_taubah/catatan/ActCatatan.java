package com.armusat_taubah.catatan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.armusat_taubah.R;
import java.util.ArrayList;

public class ActCatatan extends AppCompatActivity implements OnItemClickListener {

    private static final String TAG = "catatan" ;
    private Toolbar xToolbar;

    //listview dan tombol pada layout activity_catatan
    private ListView daftarCtt;
    private Button cttTambah;

    //adapter untuk list
    private ArrayAdapter<String> adapter;
    private Cursor catatan;
    //database
    private DBHelper dbHelper;

    //array untuk judul catatan
    private ArrayList<String> titles;
    private ArrayList<Item> items;

    //posisi variabel saat di klik di list
    private int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan);

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

        daftarCtt = (ListView) findViewById(R.id.DaftarCtt);
        cttTambah = (Button) findViewById(R.id.cttTambah);

        dbHelper = new DBHelper(getApplicationContext());

        //set untuk judul pada listview
        setCatatan();

        //set saat longclik pada listview
        this.registerForContextMenu(daftarCtt);

        //fungsi onClickListener untuk tombol "Tambah  Catatan"
        cttTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),
                        ActCatatanTodo.class));
            }
            });
    }

    //deklarasi fungsi setCatatan
    public void setCatatan(){
        titles = new ArrayList<>();
        items = new ArrayList<>();

        //memanggil catatan yang ada di Database
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        //cek dbHelper lebih detail
        catatan = dbHelper.getCatatan(db);

        db.close();

        //tutup database karena disini tidak digunakan lagi

        if (catatan.moveToFirst()){
            do{
                items.add(new Item(catatan.getShort(0), catatan.getString(1)));
            } while (catatan.moveToNext());
        }

        for(Item i:items){
            titles.add(i.getJudul());
        }

        //buat adapter baru untuk list
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        daftarCtt.setAdapter(adapter);
        //set listview agar bisa di klik
        daftarCtt.setOnItemClickListener(this);
    }

    //refresh list
    @Override
    protected void onResume(){
        super.onResume();
        setCatatan();
    }

    //method untuk longclicked pada list
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        //menuInfo untuk menampilkan pilihan saat longclicked listview
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        position = info.position;
        //judul pilihan
        menu.setHeaderTitle("Silahkan Pilih");
        //memunculkan menu
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_ctt, menu);
    }

    //method saat memilih menu longclick
    @Override
    public boolean onContextItemSelected(MenuItem item){
        //memanggil TextView untuk item yang dipilih
        TextView tv=(TextView) daftarCtt.getChildAt(position);
        //menampilkan text yang dipilih
        String judul = tv.getText().toString();

        //buat kondisi pilihan
        switch(item.getItemId()){
            case R.id.editCtt:
                Intent i = new Intent(this, ActCatatanTodo.class);
                i.putExtra("id", items.get(position).getId());
                Log.d(TAG, judul);
                i.putExtra("isEdit", true);
                startActivity(i);
                break;

            case R.id.hapusCtt:
                //hapus catatan yang dipilih
                dbHelper.hapusCtt(items.get(position).getId());
                //refresh listview
                setCatatan();
                break;
        }return false;
    }

    //Fungsi klik pada judul catatan
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
        TextView tv = (TextView) arg1;
        String judul = tv.getText().toString();
        Intent mIntent = new Intent(this, ActCatatanShow.class);
        mIntent.putExtra("title", judul);
        mIntent.putExtra("id", items.get(arg2).getId());
        startActivity(mIntent);
    }

    public class Item{
        private int id;
        private String judul;
        public Item(int id, String judul){
            this.id = id;
            this.judul = judul;
        }

        public int getId(){
            return id;
        }

        public String getJudul(){
            return judul;
        }
    }
}
