package com.android.firebaseapp.firebaseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonCreate, buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCreate = (Button) findViewById(R.id.buttonCreateId);
        buttonList = (Button) findViewById(R.id.buttonListId);

        buttonCreate.setOnClickListener(this);
        buttonList.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonCreateId){
            startActivity(new Intent(getApplicationContext(), NewUserActivity.class));
        }
        if(view.getId() == R.id.buttonListId){
            startActivity(new Intent(getApplicationContext(), DetailUserActivity.class));
        }
    }
}
