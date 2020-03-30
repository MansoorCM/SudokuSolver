package com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sudokusolver.MainActivity;
import com.example.sudokusolver.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }
    public void sudokuActivity(View view)
    {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
