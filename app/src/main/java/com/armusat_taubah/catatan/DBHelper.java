package com.armusat_taubah.catatan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by TWIN on 7/24/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    //versi database
    private static final int version = 1;

    //nama database
    private static final String DB_NAME = "DBcatatan";
    //nama tabel
    private static final String TABLE_NAME = "catatan";
    //nama kolom
    private static final String KEY_ID = "id";
    private static final String KEY_JUDUL = "judulCtt";
    private static final String KEY_ISI = "isiCtt";
    private static final String KEY_DATE = "date";
    //sqlquery untuk buat tabel di database
    private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_JUDUL+" TEXT NOT NULL," +
            " "+KEY_ISI+" TEXT NOT NULL, "+KEY_DATE+" TEXT);";

    public DBHelper(Context context){
        super(context, DB_NAME, null, version);
        this.context = context;
    }

    //memanggil fungsi CREATE_TABLE
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    //saat aplikasi diperbarui, maka hapus table lama dan buat yang baru
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2){
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);

        onCreate(db);
    }

    //fungsi menambah catatan ke database
    public void cttTambah(String judul, String isi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("judulCtt", judul);
        cv.put("isiCtt", isi);
        cv.put("date", getDateTime());

        //tambah catatan ke database
        db.insert(TABLE_NAME, null, cv);

        //lalu tutup koneksi ke database
        db.close();
    }

    //Mengurutkan semua catatan
    public Cursor getCatatan(SQLiteDatabase db){
        Cursor c = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_JUDUL,KEY_DATE}, null, null, null, null, "id DESC");
        c.moveToFirst();
        return c;
    }

    public Cursor getCatatanShow(SQLiteDatabase db, int id){
        Cursor c = db.query(TABLE_NAME, new String[] {KEY_JUDUL, KEY_ISI, KEY_DATE}, KEY_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        c.moveToFirst();
        return c;
    }

    public void hapusCtt(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public boolean editCtt (int id, String judul, String isi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("judulCtt", judul);
        cv.put("isiCtt", isi);
        cv.put("date", new Date().toString());

        return db.update(TABLE_NAME, cv, KEY_ID +"="+id,null)>0;
    }

    //public void updateNote

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm", Locale.getDefault()
        );
        Date date = new Date();
        return dateFormat.format(date);
    }
}
