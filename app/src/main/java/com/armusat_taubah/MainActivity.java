package com.armusat_taubah;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.armusat_taubah.catatan.ActCatatan;

public class MainActivity extends AppCompatActivity implements OnItemClickListener{

    private Toolbar xToolbar;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(xToolbar);

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent myIntent = null;
                if(position == 0){
                    myIntent = new Intent(v.getContext(), ActCatatan.class);
                }
                if(position == 1){
                    myIntent = new Intent(v.getContext(), ActJadwal.class);
                }
                if(position == 2){
                    myIntent = new Intent(v.getContext(), ActPenanda.class);
                }
                if(position == 3){
                    myIntent = new Intent(v.getContext(), ActTentang.class);
                }
                startActivity(myIntent);
            }});

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            finish();
            System.exit(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onBackPressed(){

        return;

    }
}

