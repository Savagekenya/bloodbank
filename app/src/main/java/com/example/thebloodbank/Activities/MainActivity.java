package com.example.thebloodbank.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.thebloodbank.Activities.Adapters.RequestAdapters;
import com.example.thebloodbank.Activities.DataModels.RequestDataModel;
import com.example.thebloodbank.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<RequestDataModel > requestDataModels;
    private RequestAdapters requestAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestDataModels= new ArrayList<>();
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.search_button){
                    //open search
                    startActivity(new Intent(MainActivity.this,SearchActivity.class));

                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        requestAdapters = new RequestAdapters(requestDataModels,this);

    }

    private void populateHomepage(){
        RequestDataModel requestDataModel = new RequestDataModel( "","");
        requestDataModels.add (requestDataModel);
        requestAdapters.notifyDataSetChanged();
        recyclerView.setAdapter(requestAdapters);
        populateHomepage();
    }
}