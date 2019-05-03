package com.armusat_taubah;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.*;
import android.text.InputFilter;
import android.text.TextUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ActPenanda extends AppCompatActivity implements
        OnItemSelectedListener {
    //Deklarasi variable atribut pada xml
    Spinner s1, s2, s3;
    Button simpan, hapus;
    EditText tgl1, ayat1;
    TextView date, baca;
    String[] surat, hari, bulan;
    private Toolbar xToolbar;

    private SharedPreferences pref;
    private final String KEY_HARI = "key_hari";
    private final String KEY_TGL = "key_tgl";
    private final String KEY_BLN = "key_bln";
    private final String KEY_SURAT = "key_surat";
    private final String KEY_AYAT = "key_ayat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penanda);

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

        pref = getSharedPreferences("mypreferences", MODE_PRIVATE);

        tampildata();

        s1 = (Spinner) findViewById(R.id.spinhari);
        hari = getResources().getStringArray(R.array.hari);

        s2 = (Spinner) findViewById(R.id.spinbulan);
        bulan = getResources().getStringArray(R.array.bulan);

        s3 = (Spinner) findViewById(R.id.spinsurat);
        surat = getResources().getStringArray(R.array.surat);

        tgl1 = (EditText) findViewById(R.id.tgl1);
        tgl1.setFilters(new InputFilter[]{new InputFilterMinMax("1", "31")});


        ayat1 = (EditText) findViewById(R.id.ayat1);
        ayat1.setFilters(new InputFilter[]{new InputFilterMinMax("1", "286")});


        ArrayAdapter<String> adapter1 = new
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hari);
        ArrayAdapter<String> adapter2 = new
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bulan);
        ArrayAdapter<String> adapter3 = new
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, surat);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setOnItemSelectedListener(this);
        s1.setPrompt("Pilih Hari");
        s1.setAdapter(adapter1);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setPrompt("Pilih Bulan");
        s2.setAdapter(adapter2);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s3.setPrompt("Pilih Surat");
        s3.setAdapter(adapter3);

        simpan = (Button) findViewById(R.id.simpan);
        simpan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String hari = (String) s1.getSelectedItem();
                String bulan = (String) s2.getSelectedItem();
                String surat = (String) s3.getSelectedItem();
                String tanggal = tgl1.getText().toString();
                String ayat = ayat1.getText().toString();

                if (TextUtils.isEmpty(tanggal)) {
                    tgl1.setError("isi tanggal");
                    return;
                }
                if (TextUtils.isEmpty(ayat)) {
                    ayat1.setError("isi ayat");
                    return;
                } else {
                    simpandate(hari,tanggal,bulan,surat,ayat);
                    tampildata();
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
                }
            }
        });
        hapus = (Button)findViewById(R.id.hapus);
        hapus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                date.setText("");
                baca.setText("");
            }
        });

        LinearLayout layout = (LinearLayout) findViewById(R.id.line2);
        layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
    }

    private void simpandate(String hari, String tanggal, String bulan, String surat, String ayat) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_HARI, hari+", ");
        editor.putString(KEY_TGL, tanggal+" ");
        editor.putString(KEY_BLN, bulan+" 2016");
        editor.putString(KEY_SURAT, surat+" ayat ");
        editor.putString(KEY_AYAT, ayat);
        editor.commit();
    }

    private void tampildata() {
        String simpanHari = ambilHari();
        String simpanTgl = ambilTgl();
        String simpanBln = ambilBln();
        String simpanSurat= ambilSurat();
        String simpanAyat= ambilAyat();

       EditText tanggal = (EditText)findViewById(R.id.tgl1);
        tanggal.setText("");
        EditText ayat = (EditText)findViewById(R.id.ayat1);
        ayat.setText("");
        date = (TextView) findViewById(R.id.textView9);
        date.setText(simpanHari+simpanTgl+simpanBln);
        baca = (TextView) findViewById(R.id.textView11);
        baca.setText(simpanSurat+simpanAyat);
    }

    private String ambilHari() {
        return pref.getString(KEY_HARI, "");
    }

    private String ambilTgl(){
        return pref.getString(KEY_TGL,"");
    }
    private String ambilBln(){
        return pref.getString(KEY_BLN,"");
    }
    private String ambilSurat(){
        return pref.getString(KEY_SURAT,"");
    }
    private String ambilAyat(){
        return pref.getString(KEY_AYAT,"");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub

    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

